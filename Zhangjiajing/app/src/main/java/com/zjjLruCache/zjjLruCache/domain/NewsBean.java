package com.zjjLruCache.zjjLruCache.domain;

/**
 * Created by zhangjiajing on 2016/7/28.
 * ListView item实体类
 */
public class NewsBean {
    private String newsIconUrl;
    private String newsTitle;
    private String newsContent;

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public String setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
        return newsIconUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
        return newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String setNewsContent(String newsContent) {
        this.newsContent = newsContent;
        return newsContent;
    }

}
