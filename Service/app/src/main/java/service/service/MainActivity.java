package service.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent1,intent2;
    MyBindService myBindService;
    //serviceConnection对象,绑定对象
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        //当启动源跟service成功连接之后将会自动调用这个方法
        //去数据得到service连接即可
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBindService = ((MyBindService.MyBinder)iBinder).getService();
        }

        //当启动源跟Service的连接意外丢失的时候会调用这个方法
        //比如当service崩溃或被意外杀死，成功断开连接则不会
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View view) {
        switch (view.getId()){
            /**
             * Start方式
             */
            case R.id.startSeivice:
                //启动服务start
                intent1 = new Intent(this,MyStartService.class);
                startService(intent1);
                break;
            case R.id.stopService:
                //关闭服务
                intent1 = new Intent(this,MyStartService.class);
                stopService(intent1);
                break;
            /**
             * Bind方式
             */
            case R.id.BindService:
                //启动Bind服务 绑定
                intent2 = new Intent(this,MyBindService.class);
                //Bind方式 service，conn,flags
                bindService(intent2, serviceConnection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.start:
                //调用MyBindService中Play()方法
                myBindService.Play();
                break;
            case R.id.stop:
                //调用MyBindService中Stop()方法
                myBindService.Stop();
                break;
            case R.id.next:
                //调用MyBindService中Next()方法
                myBindService.Next();
                break;
            case R.id.previous:
                //调用MyBindService中Previous()方法
                myBindService.Previous();
                break;
            case R.id.UnBindService:
                //解绑定 服务
                unbindService(serviceConnection);
                break;
            /**
             * strat加bind 调用onDestory先停止服务再解绑定
             */
//            case R.id.BindService:
//                //启动Bind服务 绑定
//                startActivity(intent2);
//                intent2 = new Intent(this,MyBindService.class);
//                //Bind方式 service，conn,flags
//                bindService(intent2, serviceConnection, Service.BIND_AUTO_CREATE);
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        stopService(intent2);
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
