package backend.tm;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import utils.*;
import utils.Error;


public interface TransactionManager {

    // 开启事务
    long begin();

    // 提交事务
    void commit(long xid);

    //撤销事务
    void abort(long xid);

    // 获取事务状态
    boolean isActive(long xid);

    // 获取事务是否提交状态
    boolean isCommitted(long xid);

    // 获取事务是否撤销状态
    boolean isAborted(long xid);

    // 关闭事务管理TM
    void close();


    // 创建一个 xid 文件并创建 TM
    public static TransactionManagerImpl create(String path){
        File f = new File(path + TransactionManagerImpl.XID_SUFFIX);
        try {
            if(!f.createNewFile()){
                Panic.panic(Error.FileExistsException);
            }
        } catch (IOException e) {
            Panic.panic(e);
        }
        if(!f.canRead() || !f.canWrite()){
            Panic.panic(Error.FileCannotRWException);
        }

        FileChannel fc = null;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(f, "rw");
            fc = raf.getChannel();
        } catch (FileNotFoundException e) {
            Panic.panic(e);
        }

        // 写空XID的文件头
        ByteBuffer buf = ByteBuffer.wrap(new byte[TransactionManagerImpl.LEN_XID_HEADER_LENGTH]);
        try {
            //从零创建 XID 文件时需要写一个空的 XID 文件头，即设置 xidCounter 为 0，
            // 否则后续在校验时会不合法：
            fc.position(0);
            fc.write(buf);
        } catch (IOException e) {
            Panic.panic(e);
        }

        return new TransactionManagerImpl(raf, fc);


    }

    // 从一个已有的 xid 文件创建 TM
    public static TransactionManagerImpl open(String path)
    {
        File f = new File(path + TransactionManagerImpl.XID_SUFFIX);
        if(!f.exists()){
            Panic.panic(Error.FileNotExistsException);
        }
        if(!f.canRead() || !f.canWrite()){
            Panic.panic(Error.FileCannotRWException);
        }

        FileChannel fc = null;
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(f, "rw");//用来访问那些保存数据记录的文件
            fc = raf.getChannel();//返回与这个文件有关的唯一FileChannel对象
        } catch (FileNotFoundException e) {
            Panic.panic(e);
        }
        return new TransactionManagerImpl(raf, fc);
    }
}
