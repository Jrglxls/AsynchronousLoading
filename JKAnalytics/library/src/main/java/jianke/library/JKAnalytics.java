package jianke.library;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangjiajing on 2016/8/12.
 */
public class JKAnalytics {
    private Long startTime,endTime,duration;
    private String appKey;
    private JKAnalyticsInfo jkAnalyticsInfo;

    public void startWithAppkey(String appKey){
        jkAnalyticsInfo = new JKAnalyticsInfo();
        jkAnalyticsInfo.setAppkey(appKey);
    }




    /**
     * 两个时间戳相减的到时间差
     */
    //获取进入页面的时间
    public Long beginLogPageView(){
        startTime = new Date().getTime();
        return startTime;
    }
    //获取退出页面的时间
    public Long endLogPageView(){
        endTime = new Date().getTime();
        duration = endTime-startTime;
        jkAnalyticsInfo.setDuration(duration.toString());
        return endTime;
    }

    /**
     * event事件
     */
    public void event(String eventId, String pageId, Context context){
        //获取参数赋值
//        jkAnalyticsInfo.setDuration();
        Map<String,String> maps = new JKSystemParamsHelper(context).getDefaultParams(context);
        jkAnalyticsInfo.setExtras(new Gson().toJson(maps));


        //将数据存入数据库
    }

    /**
     * 发送请求
     */
    public void sendRequest(){



    }


}
