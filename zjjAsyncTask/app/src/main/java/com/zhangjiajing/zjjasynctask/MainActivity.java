package com.zhangjiajing.zjjasynctask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask asyncTask = new AsyncTask();//创建一个实例
        asyncTask.execute();//启动AsyncTask
    }

    //xml设置android:onClick="loadImage"方法 实现跳转
    public void loadImage(View view) {
        startActivity(new Intent(this, ImageTest.class));
    }

    //xml设置android:onClick="loadImage"方法 实现跳转
    public void ProgressBarTest(View view) {
        startActivity(new Intent(this,ProgressBarTest.class));
    }
}
