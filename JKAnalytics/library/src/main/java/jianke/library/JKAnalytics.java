package jianke.library;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiajing on 2016/8/12.
 *
 */
public class JKAnalytics {
    private Long startTime,endTime,duration;
    private String mAppKey,mUserId,mUserFlag,mReferrer,mParam;
    JKAnalyticsInfo jkAnalyticsInfo= new JKAnalyticsInfo();
    private JKAnaliseDBOperate jkAnaliseDBOperate;
    private Context context;
    private Handler handler;

    /**
     * 单例对象实例
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
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                sendRequest();
//            }
//        },4000);
        sendRequest();
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
        duration = endTime-startTime;
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
        jkAnalyticsInfo.setDuration(duration.toString());
        Map<String,String> maps = JKSystemParamsHelper.getDefaultParams(context);
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
//        int maxId = jkAnaliseDBOperate.getMaxId();

        List<JKAnalyticsInfo> jkAnaliseInfoList = jkAnaliseDBOperate.getALLInfos();
        Log.d("jrglxls", jkAnaliseInfoList.toString());
        if (jkAnaliseInfoList!=null){
            Map<String, String> params = new HashMap<String, String>();
            params.put("body", jkAnaliseInfoList.toString());
//            String result = HttpPost.submitPostData(params,"utf-8");
//            new HttpPostSendMessage(params,"utf-8",context,handler).start();

//            if (result.equals("success")){
//                jkAnaliseDBOperate.delete(maxId);
//            }else if (result.equals("failure")){
//
//            }
        }
    }
}
