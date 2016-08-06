package com.zhangjiajing.zjjasynctask;

/**
 * Created by zhangjiajing on 2016/8/5.
 * AsyncTask 学习
 */
public class AsyncTask extends android.os.AsyncTask<Void,Void,Void> {

    //必须实现 第二调用
    @Override
    protected Void doInBackground(Void... params) {
        publishProgress();//调用onProgressUpdate方法
        return null;
    }

    //最先调用
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //最后调用
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    //doInBackground中调用publicProgress()后执行
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
