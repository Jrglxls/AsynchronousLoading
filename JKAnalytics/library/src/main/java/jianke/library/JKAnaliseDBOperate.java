package jianke.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jrglxls on 2016/8/17.
 */
public class JKAnaliseDBOperate {

    private Context context;
    private JKAnaliseDatabase jkAnaliseDatabase;
    private SQLiteDatabase db;

    public JKAnaliseDBOperate(Context context) {
        this.context = context;
        jkAnaliseDatabase = new JKAnaliseDatabase(context,"JKAnalise",null,1);
        db = jkAnaliseDatabase.getWritableDatabase();
    }

    public void insert(JKAnalyticsInfo jkAnalyticsInfo) {
        db.execSQL("insert into JKAnaliseTable values (NULL,?,?,?,?,?,?,?,?,?) ",
                new Object[]{jkAnalyticsInfo.getUserId(),
                             jkAnalyticsInfo.getUserFlag(),
                             jkAnalyticsInfo.getPageId(),
                             jkAnalyticsInfo.getReferrer(),
                             jkAnalyticsInfo.getTimestamp(),
                             jkAnalyticsInfo.getEventId(),
                             jkAnalyticsInfo.getDuration(),
                             jkAnalyticsInfo.getExtras(),
                             jkAnalyticsInfo.getParams()});
    }

    public void delete(int maxId){

//        int result = db.delete("JKAnalise","id <= ?",new String[]{maxId+""});
    db.execSQL("delete from JKAnaliseTable where id <= ?",new Object[]{maxId});
    }

    public int getMaxId(){
      Cursor c = db.rawQuery("select max id from JKAnaliseTable",null) ;
        int maxId = 0;
        while (c.moveToNext()){
             maxId = c.getInt(c.getColumnIndex("id"));
        }
        c.close();
        return maxId;
    }


    public List<JKAnalyticsInfo> getALLInfos(){
        int maxId = getMaxId();
        List<JKAnalyticsInfo> jkAnalyticsInfoList = new ArrayList<JKAnalyticsInfo>();
        Cursor c = db.rawQuery("select * from JKAnaliseTable where id <= ?",new String[]{maxId+""});
        while (c.moveToNext()){
            JKAnalyticsInfo jkAnalyticsInfo = new JKAnalyticsInfo();
//            jkAnalyticsInfo.setAppkey(c.getString(c.getColumnIndex("appKey")));

            jkAnalyticsInfo.setUserId(c.getString(c.getColumnIndex("UserId")));
            jkAnalyticsInfo.setUserFlag(c.getString(c.getColumnIndex("UserFlag")));
            jkAnalyticsInfo.setPageId(c.getString(c.getColumnIndex("PageId")));
            jkAnalyticsInfo.setReferrer(c.getString(c.getColumnIndex("Referrer")));
            jkAnalyticsInfo.setTimestamp(c.getString(c.getColumnIndex("Timestamp")));
            jkAnalyticsInfo.setEventId(c.getString(c.getColumnIndex("EventId")));
            jkAnalyticsInfo.setDuration(c.getString(c.getColumnIndex("Duration")));
            jkAnalyticsInfo.setExtras(c.getString(c.getColumnIndex("Extras")));
            jkAnalyticsInfo.setParams(c.getString(c.getColumnIndex("Params")));

            jkAnalyticsInfoList.add(jkAnalyticsInfo);
        }
        c.close();

        return jkAnalyticsInfoList;
    }

    public void close(){
        db.close();
    }


}
