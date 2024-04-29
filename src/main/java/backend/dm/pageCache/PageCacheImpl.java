package backend.dm.pageCache;
import backend.dm.cache.AbstractCache;
import backend.dm.page.Page;
import backend.dm.page.PageImpl;
import utils.Panic;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import utils.Error;

public class PageCacheImpl extends AbstractCache<Page> implements PageCache{

    private static final int MEM_MIN_LIM = 10;
    public static final String DB_SUFFIX = ".db";

}
