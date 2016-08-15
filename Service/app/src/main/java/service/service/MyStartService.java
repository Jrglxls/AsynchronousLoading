package service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangjiajing on 2016/8/15.
 * start方式
 */
public class MyStartService extends Service {

    //只会调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zjj","start-onCreate");
    }

    //启动会调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zjj","start-onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //停掉 仅一次
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zjj", "start-onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("zjj", "start-onBind");
        return null;
    }
}
