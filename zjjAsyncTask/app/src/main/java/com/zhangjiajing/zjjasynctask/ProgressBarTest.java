package com.zhangjiajing.zjjasynctask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Created by zhangjiajing on 2016/8/6.
 */
public class ProgressBarTest extends Activity{
    private ProgressBar mProgressBar;
    private AsyncTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_test);
        //创建AsyncTask
        mTask = new AsyncTask();
        mTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //使AsyncTask与Activity生命周期保持一致
        if (mTask!=null && mTask.getStatus() == android.os.AsyncTask.Status.RUNNING){
            //cancle方法只是将对应的AsyncTask标记为cancle状态，并不是直接停止线程
            mTask.cancel(true);
        }
    }

    class AsyncTask extends android.os.AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //模拟进度更新
            for (int i= 0;i<100;i++){
                //判断AsyncTask是否为cancle状态 是停止循环 否继续循环
                if (isCancelled()){
                    break;
                }
                //使用publicProgress 将进度传递给progressBar
                i+=3;
                publishProgress(i);
                try {
                    //延缓更新进度
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //接受publicProgress()方法传递的参数 valus为数组
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //判断AsyncTask是否为cancle状态 是退出更新ProgressBar 否继续
            if (isCancelled()){
                return;
            }
            //获取进度更新值
            mProgressBar.setProgress(values[0]);
        }
    }
}
