package service.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangjiajing on 2016/8/15.
 * bind方式绑定
 */
public class MyBindService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("zjj","Bind-onCreate");
    }

    public class MyBinder extends Binder{
        public MyBindService getService(){
            return MyBindService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("zjj","Bind-onBind");
        return new MyBinder();
    }

    public void Play(){
        Log.d("zjj","播放");
    }

    public void Stop(){
        Log.d("zjj","暂停");
    }

    public void Next(){
        Log.d("zjj","下一首");
    }

    public void Previous(){
        Log.d("zjj","上一首");
    }

    //只能解绑定一次 必须解绑定
    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d("zjj", "Bind-unbindService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zjj", "Bind-onDestroy");
    }
}
