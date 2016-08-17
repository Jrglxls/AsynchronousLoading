package jianke.library;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiajing on 2016/8/12.
 */
public class JKAnalytics {
    private Long startTime,endTime,duration;
    private String appKey;
    private JKAnalyticsInfo jkAnalyticsInfo;
    private JKAnaliseDBOperate jkAnaliseDBOperate;
    private Context context;

    public void startWithAppkey(String appKey,Context context){
        this.context = context;

        jkAnalyticsInfo = new JKAnalyticsInfo();
        jkAnalyticsInfo.setAppkey(appKey);
        jkAnaliseDBOperate = new JKAnaliseDBOperate(context);

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
        //// TODO: 2016/8/17



        //将数据存入数据库
        jkAnaliseDBOperate.insert(jkAnalyticsInfo);
    }

    /**
     * 发送请求
     */
    public void sendRequest(){

        List<JKAnalyticsInfo> jkAnaliseInfoList = jkAnaliseDBOperate.getALLInfos();
        // TODO: 2016/8/17

    }


}
