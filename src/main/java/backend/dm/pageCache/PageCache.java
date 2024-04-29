package backend.dm.pageCache;
import backend.dm.page.Page;
import utils.Panic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import utils.Error;

public interface PageCache {

    public static final int PAGE_SIZE = 1 << 13; // 8k

    int newPage(byte[] initData);

    Page getPage(int pgno) throws  Exception;

    void close();

    void release(Page page);

    void truncateByBgno(int maxPgno);

    int getPageNumber();

    void flushPage(Page pg);

    public static PageCacheImpl create(String path, long memory)
    {
        File f = new File(path + PageCacheImpl.DB_SUFFIX)
    }


}
