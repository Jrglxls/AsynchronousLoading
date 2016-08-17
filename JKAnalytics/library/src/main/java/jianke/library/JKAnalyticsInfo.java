package jianke.library;

/**
 * Created by zhangjiajing on 2016/8/12.
 */
public class JKAnalyticsInfo {
    /**
     * event事件获取的参数
     */
    private String Appkey;//用户的唯一标示 OK
    private String UserId;//用户的唯一标示 CommonDatas.getUserId() OK
    private String UserFlag;//用户是否登录 CommonDatas.getLogin() OK
    private String pageId;//当前页面的标示 OK
    private String Referrer;//上一个页面的标示
    private String Timestamp;//时间戳 System.currentTimeMillis() + "" OK
    private String eventId;//事件id OK
    private String duration;//页面停留时间    ok
    private String extras;//常用的参数       ok
    private String params;//一些传递的参数

    public String getAppkey() {
        return Appkey;
    }

    public void setAppkey(String appkey) {
        Appkey = appkey;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserFlag() {
        return UserFlag;
    }

    public void setUserFlag(String userFlag) {
        UserFlag = userFlag;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getReferrer() {
        return Referrer;
    }

    public void setReferrer(String referrer) {
        Referrer = referrer;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
