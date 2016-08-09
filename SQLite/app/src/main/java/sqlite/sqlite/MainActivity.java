package sqlite.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

//每个程序都有自己的数据库 默认情况下是各自互相不干扰

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 数据库操作方法一
         */
        //创建数据库 并且打开
        SQLiteDatabase sqLiteDatabase1 = openOrCreateDatabase("zjj1.db",MODE_PRIVATE,null);
        //创建表 一个主键，三个普通的字段
        sqLiteDatabase1.execSQL("create table if not exists zjj1tb(_id integer primary key autoincrement," +
                                                        "name text not null," +
                                                        "age integer not null," +
                                                        "sex text not null)");
        //在表中插入数据
        sqLiteDatabase1.execSQL("insert into zjj1tb(name,sex,age) values('zhangjiajing','女','22')");
        sqLiteDatabase1.execSQL("insert into zjj1tb(name,sex,age) values('Jessica','女','27')");
        sqLiteDatabase1.execSQL("insert into zjj1tb(name,sex,age) values('郑秀妍','女','26')");

        //在表中查询数据 使用Cursor
        Cursor cursor1 = sqLiteDatabase1.rawQuery("select*from zjj1tb", null);
        //判断是否为空
        if (cursor1!=null){
            while (cursor1.moveToNext()){
                Log.d("test1", "_id" + cursor1.getInt(cursor1.getColumnIndex("_id")));
                Log.d("test1", "name"+cursor1.getInt(cursor1.getColumnIndex("name")));
                Log.d("test1", "age"+cursor1.getInt(cursor1.getColumnIndex("age")));
                Log.d("test1", "sex"+cursor1.getInt(cursor1.getColumnIndex("sex")));
                Log.d("test1", "1111111111111111111111111111111");
            }
            //游标释放
            cursor1.close();
        }
        //数据库释放
        sqLiteDatabase1.close();

        /**
         * 数据库操作方法二
         */
        //创建数据库 并且打开
        SQLiteDatabase sqLiteDatabase2 = openOrCreateDatabase("zjj2.db",MODE_PRIVATE,null);
        //创建表 一个主键，三个普通的字段
        sqLiteDatabase2.execSQL("create table if not exists zjj2tb(_id integer primary key autoincrement," +
                                                        "name text not null," +
                                                        "age integer not null," +
                                                        "sex text not null)");
        //创建ContentValues对象并实例化
        ContentValues values = new ContentValues();
        values.put("name","张佳婧");
        values.put("sex","女");
        values.put("age",23);
        //在表中插入数据
        long rowId = //可用可不用
                sqLiteDatabase2.insert("zjj2tb",null,values);

        //清除values数据后可再次赋值
        values.clear();
        values.put("name","卡皇");
        values.put("sex", "女");
        values.put("age", 27);
        sqLiteDatabase2.insert("zjj2tb", null, values);

        values.clear();
        values.put("name","毛毛");
        values.put("sex", "女");
        values.put("age", 26);
        sqLiteDatabase2.insert("zjj2tb", null, values);

        values.clear();
        values.put("name","卡哥");
        values.put("sex", "男");
        values.put("age", 21);
        sqLiteDatabase2.insert("zjj2tb", null, values);

        values.clear();
        values.put("name","杰西卡");
        values.put("sex", "男");
        values.put("age", 24);
        sqLiteDatabase2.insert("zjj2tb", null, values);

        //将id大于3的人的性别改为女
        values.clear();
        values.put("sex", "女");
        sqLiteDatabase2.update("zjj2tb",values,"_id>?",new String[]{"3"});

        //删除name中带有卡字的人
        sqLiteDatabase2.delete("zjj2tb","name like?",new String[]{"%卡%"});

        //在表中查询数据 使用Cursor
        Cursor cursor2 = sqLiteDatabase2.query("zjj2tb", null, "_id>?", new String[]{"0"}, null, null, "age");
        //判断是否为空
        if (cursor2!=null){
            //把所有列取出来 查询出所有的字段
            String[] columns2 = cursor2.getColumnNames();
            //如果下一个有数据
            while (cursor2.moveToNext()){
                //取数据
                for (String columnName:columns2){
                    Log.d("test2",cursor2.getString(cursor2.getColumnIndex(columnName)));
                }
            }
            cursor2.close();
        }
        sqLiteDatabase2.close();

        /**
         * 数据库操作方法三
         */
        DBOpenHelper helper = new DBOpenHelper(this,"zjj3tb");
        //获取一个只读的数据库 只能查询 不能写入 不能更新
//        helper.getReadableDatabase();
        //获取可读写的数据库
        SQLiteDatabase sqLiteDatabase3 = helper.getWritableDatabase();
        Cursor cursor3 = sqLiteDatabase3.rawQuery("select*from zjj3tb", null);
        if (cursor3!=null){
            //把所有列取出来 查询出所有的字段
            String[] columns3 = cursor3.getColumnNames();
            //如果下一个有数据
            while (cursor3.moveToNext()){
                //取数据
                for (String columnName:columns3){
                    Log.d("test3",cursor3.getString(cursor3.getColumnIndex(columnName)));
                }
            }
            cursor3.close();
        }
        sqLiteDatabase3.close();
    }
}
