package com.contentprovider.contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 查询联系人
         */
        //获取ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        //查询 查询的uri地址，查询的条件ID和NAME,查询条件,,排列 返回游标对象
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        //如果游标不等于空
        if (cursor!=null){
            //遍历查询每一个
            while (cursor.moveToNext()){
                //取出相关信息id和name id为类型 _ID可以写成ContactsContract.Contacts._ID
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("zjj","_id"+id);
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.d("zjj","display_name"+name);
                /**
                 * 根据联系人id查出联系人号码
                 */
                //查询所有联系人的号码 查询id和name后二次查询 uri，查询number和type，根据id查询，，排序
                Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);
                //如果有游标不等于空
                if (cursor1!=null){
                    //遍历查询每一个
                    while (cursor1.moveToNext()){
                        //取出电话号码类型
                        int type = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        //如果为家庭号码
                            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME){
                                //取得number值
                                Log.d("zjj","家庭电话"+cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                //如果为手机号码
                            }else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE){
                                Log.d("zjj","手机"+cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            }
                    }
                    //使用完关闭游标
                    cursor1.close();
                }
                /**
                 * 根据联系人id查出联系人号码
                 */
                //查询所有联系人的号码 查询id和name后二次查询 Uri，查询data和type，根据id查询，，排序
                Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.TYPE},
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+id,null,null);
                //如果cursor2不等于空
                if (cursor2!=null){
                    //遍历查询每一个
                    while (cursor2.moveToNext()){
                        //取出邮箱类型
                        int type = cursor2.getInt(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        //判断是否为工作邮箱
                        if (type == ContactsContract.CommonDataKinds.Email.TYPE_WORK){
                            //取出邮箱地址
                            Log.d("zjj","工作邮箱"+cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }else {
                            Log.d("zjj","其他邮箱"+cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }
                    }
                    //使用后关闭游标
                    cursor2.close();
                }
            }
            //使用后关闭游标
            cursor.close();
        }

        /**
         * 插入联系人
         */
        //获取ContentResolver对象
        ContentResolver contentResolver1 = getContentResolver();
        //获取ContentValues对象
        ContentValues contentValues = new ContentValues();
        //得到uri对象
        Uri uri = contentResolver1.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        //解析url 得到long类型的参数
        Long raw_contact_id = ContentUris.parseId(uri);

        contentValues.clear();//清空
        //指定id
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, raw_contact_id);
        //display_name 插入人名
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,"卡宝");
        //指定type
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        //执行插入语句
        contentResolver1.insert(ContactsContract.Data.CONTENT_URI,contentValues);

        contentValues.clear();//清空
        //指定id
        contentValues.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, raw_contact_id);
        //插入电话信息
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "13409965689");
        //指定type
        contentValues.put(ContactsContract.CommonDataKinds.Phone.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        //执行插入语句
        contentResolver.insert(ContactsContract.Data.CONTENT_URI,contentValues);
    }
}
