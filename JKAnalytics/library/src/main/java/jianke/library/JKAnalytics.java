package jianke.library;

/**
 * Created by zhangjiajing on 2016/8/12.
 */
public class JKAnalytics {
    JKAnalyticsInfo jkAnalyticsInfo;
    private String startTime,endTime;


    public void startWithAppkey(String appKey){

    }

    /**
     * 两个时间戳相减的到时间差
     */
    //获取进入页面的时间
    public String beginLogPageView(){
        startTime = "sometime";
        return startTime;
    }
    //获取退出页面的时间
    public String endLogPageView(){
        endTime = "sometime";
        return endTime;
    }

    /**
     * event事件
     */
    public void event(String eventId,String pageId){
        //获取参数赋值
//        jkAnalyticsInfo.setDuration();

        //将数据存入数据库
    }

    /**
     * 发送请求
     */
    public void sendRequest(){

    }
}
