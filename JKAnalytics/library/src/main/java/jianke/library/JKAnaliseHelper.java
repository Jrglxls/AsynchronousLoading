package jianke.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangjiajing on 2016/8/12.
 * 创建数据库
 */
public class JKAnaliseHelper extends SQLiteOpenHelper {

    public JKAnaliseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists JKAnaliseTable(id integer PRIMARY KEY AUTOINCREMENT,Appkey text,UserId text,UserFlag text,pageId text,Referrer text,Timestamp text,EventId text,Duration text,Extras text,Param text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
