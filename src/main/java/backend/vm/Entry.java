package backend.vm;

import backend.dm.dataItem.DataItem;
import backend.dm.dataItem.DataItemImpl;

// 规定一条Entry中存储的数据格式如下
// [XMIN]  [XMAX]  [DATA]
//  8个字节  8个字节
// XMIN 是创建该条记录（版本）的事务编号，
// 而 XMAX 则是删除该条记录（版本）的事务编号。
// DATA 就是这条记录持有的数据。
public class Entry {

    private static final int OF_XMIN = 0;
    private static final int OF_XMAX = OF_XMIN + 8;
    private static final int OF_DATA = OF_XMAX + 8;

    private long uid;
    private DataItem dataItem;
    private VersionManager vm;

    public static Entry newEntry(VersionManager vm, DataItem dataItem, long uid) {
        Entry entry = new Entry();
        entry.uid = uid;
        entry.dataItem = dataItem;
        entry.vm = vm;
        return entry;
    }

    public static Entry loadEntry(VersionManager vm, long uid) throws Exception
    {
        DataItem di = ((VersionManagerImpl)vm).dm.read(uid);
        return newEntry(vm, di, uid);
    }
}
