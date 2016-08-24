package jianke.library;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangjiajing on 2016/8/12.
 *
 */
public class JKAnalytics {
    private Long startTime,endTime;
    private String mAppKey,mUserId,mUserFlag,mReferrer,mParam;
    JKAnalyticsInfo jkAnalyticsInfo= new JKAnalyticsInfo();
    private JKAnaliseDBOperate jkAnaliseDBOperate;
    private Context context;
    private Handler handler;

    /**
     * 单例
     */
     private static JKAnalytics instance = null;

     public synchronized static JKAnalytics getInstance() {
     if (instance == null) {
         instance = new JKAnalytics();
        }
     return instance;
     }

    /**
     * 设置appKey，设置上传时间间隔
     * @param appKey
     * @param context
     */
    public void startWithAppkey(String appKey,Context context){
        this.context = context;
        mAppKey = appKey;
        jkAnaliseDBOperate = new JKAnaliseDBOperate(context);

        //设置上传时间间隔
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendRequest();
            }
        }, 20000);
    }

    /**
     * 设置UserId 设置UserFlag
     * @param UserId
     * @param UserFlag
     */
    public void getUserIdAndUserFlag(String UserId,String UserFlag){
        mUserId = UserId;
        mUserFlag = UserFlag;
    }

    /**
     * 两个时间戳相减的到时间差
     * 设置duration
     */
    //获取进入页面的时间
    public void beginLogPageView(){
        startTime = new Date().getTime();
    }
    //获取退出页面的时间
    public void endLogPageView(){
        endTime = new Date().getTime();
    }

    /**
     * 设置param
     */
    public void paramsInfo(String param){
        mParam = param;
    }

    /**
     * event事件
     * 设置Extras 设置eventId 设置pageId
     */
    public void event(String eventId, String pageId, Context context){
        //获取参数赋值
        jkAnalyticsInfo.setAppkey(mAppKey);
        jkAnalyticsInfo.setUserId(mUserId);
        jkAnalyticsInfo.setUserFlag(mUserFlag);
        jkAnalyticsInfo.setPageId(pageId);
        jkAnalyticsInfo.setReferrer(mReferrer);
        jkAnalyticsInfo.setTimestamp(String.valueOf(System.currentTimeMillis()));
        jkAnalyticsInfo.setEventId(eventId);
        jkAnalyticsInfo.setDuration(String.valueOf(endTime - startTime));
        Map<String,String> maps = JKSystemParamsHelper.getDefaultParams(context);
        Log.d("zjj",maps.toString());
        jkAnalyticsInfo.setExtras(new Gson().toJson(maps));
        jkAnalyticsInfo.setParam(mParam);

        //将数据存入数据库
        jkAnaliseDBOperate.insert(jkAnalyticsInfo);

        //设置上一个页面 Referrer
        mReferrer = pageId;
    }

    /**
     * 发送请求
     */
    public void sendRequest(){
        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        List<JKAnalyticsInfo> jkAnaliseInfoList = jkAnaliseDBOperate.getALLInfos();
        if (jkAnaliseInfoList!=null){
            Log.d("zjj","jkAnaliseInfoList.size()="+jkAnaliseInfoList.size());

            for (int i = 0;i<jkAnaliseInfoList.size();i++){
                Gson gson = new Gson();
                try {
                    jsonObject = new JSONObject(gson.toJson(jkAnaliseInfoList.get(i)));
                    jsonArray.put(jsonObject);
                    Log.d("zjj","jsonObject"+jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Map<String, String> params = new HashMap<String, String>();
            if (jsonObject != null) {
                params.put("body", jsonArray.toString());
            }
            Log.d("zjj", "params" + params);
            //上传数据
//            new HttpPostSendMessage(params,"utf-8",context,handler).start();
        }
    }

}
