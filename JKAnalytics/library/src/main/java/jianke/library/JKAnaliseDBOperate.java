package jianke.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jrglxls on 2016/8/17.
 * 操作数据库
 */
public class JKAnaliseDBOperate {

    private Context context;
    private JKAnaliseHelper jkAnaliseHelper;
    private SQLiteDatabase db;

    public JKAnaliseDBOperate(Context context) {
        this.context = context;
        jkAnaliseHelper = new JKAnaliseHelper(context,"JKAnaliseTable",null,1);
        db = jkAnaliseHelper.getWritableDatabase();
    }

    public void insert(JKAnalyticsInfo jkAnalyticsInfo) {
        db.execSQL("insert into JKAnaliseTable values (NULL,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{jkAnalyticsInfo.getAppkey(),
                             jkAnalyticsInfo.getUserId(),
                             jkAnalyticsInfo.getUserFlag(),
                             jkAnalyticsInfo.getPageId(),
                             jkAnalyticsInfo.getReferrer(),
                             jkAnalyticsInfo.getTimestamp(),
                             jkAnalyticsInfo.getEventId(),
                             jkAnalyticsInfo.getDuration(),
                             jkAnalyticsInfo.getExtras(),
                             jkAnalyticsInfo.getParam()});
    }

    public void delete(int maxId){

//        int result = db.delete("JKAnalise","id <= ?",new String[]{maxId+""});
    db.execSQL("delete from JKAnaliseTable where id <= ?",new Object[]{maxId});
    }

    public int getMaxId(){
        int maxId = 0;
        Cursor cursor = db.rawQuery("select * from JKAnaliseTable",null) ;
            while (cursor.moveToNext()){
                maxId = cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("jrglxls", String.valueOf(maxId));
            }
//        Cursor cursor = db.rawQuery("select isnull(max(id),-1) from JKAnaliseTable",null);
//        if(cursor.moveToFirst()){
//            maxId = cursor.getInt(cursor.getColumnIndex("id"));
//            Logger.d("jrglxls", String.valueOf(maxId));
//        }
        cursor.close();
        return maxId;
    }


    public List<JKAnalyticsInfo> getALLInfos(){
        int maxId = getMaxId();
        List<JKAnalyticsInfo> jkAnalyticsInfoList = new ArrayList<JKAnalyticsInfo>();
        Cursor cursor = db.rawQuery("select * from JKAnaliseTable where id <= ?",new String[]{maxId+""});
        if (cursor!=null){
            while (cursor.moveToNext()){
                JKAnalyticsInfo jkAnalyticsInfo = new JKAnalyticsInfo();
                jkAnalyticsInfo.setAppkey(cursor.getString(cursor.getColumnIndex("Appkey")));
                jkAnalyticsInfo.setUserId(cursor.getString(cursor.getColumnIndex("UserId")));
                jkAnalyticsInfo.setUserFlag(cursor.getString(cursor.getColumnIndex("UserFlag")));
                jkAnalyticsInfo.setPageId(cursor.getString(cursor.getColumnIndex("PageId")));
                jkAnalyticsInfo.setReferrer(cursor.getString(cursor.getColumnIndex("Referrer")));
                jkAnalyticsInfo.setTimestamp(cursor.getString(cursor.getColumnIndex("Timestamp")));
                jkAnalyticsInfo.setEventId(cursor.getString(cursor.getColumnIndex("EventId")));
                jkAnalyticsInfo.setDuration(cursor.getString(cursor.getColumnIndex("Duration")));
                jkAnalyticsInfo.setExtras(cursor.getString(cursor.getColumnIndex("Extras")));
                jkAnalyticsInfo.setParam(cursor.getString(cursor.getColumnIndex("Param")));

                jkAnalyticsInfoList.add(jkAnalyticsInfo);
            }
        }
        cursor.close();
        return jkAnalyticsInfoList;
    }
}
