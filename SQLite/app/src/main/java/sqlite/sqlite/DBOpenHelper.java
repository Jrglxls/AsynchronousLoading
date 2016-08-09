package sqlite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作方法三
 */

public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //首次创建数据库的时候调用 一般可以用做建库，建表的操作
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表 一个主键，三个普通的字段
        db.execSQL("create table if not exists zjj3tb(_id integer primary key autoincrement," +
                "name text not null," +
                "age integer not null," +
                "sex text not null)");
        //在表中插入数据
        db.execSQL("insert into zjj3tb(name,sex,age) values('Jessica。Jung','女','27')");
    }

    //当数据库的版本发生变化的时候会自动执行
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
