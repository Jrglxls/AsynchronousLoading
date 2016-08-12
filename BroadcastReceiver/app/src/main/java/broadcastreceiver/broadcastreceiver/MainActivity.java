package broadcastreceiver.broadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    BroadcastReceiver3 broadcastReceiver3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //动态注册
//        IntentFilter intentFilter = new IntentFilter("zjj_one");//action用来识别广播
//        BroadcastReceiver2 broadcastReceiver2 = new BroadcastReceiver2();//receiver name
//        registerReceiver(broadcastReceiver2,intentFilter);//注册广播
    }

    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btnSendNormal:
                //发送一条普通广播
                //准备参数
                Intent intent = new Intent();
                intent.putExtra("msg", "这是一条普通广播");
                intent.setAction("zjj_one");
                sendBroadcast(intent);
                break;
            case R.id.btnSendOrder:
                //发送一条有序广播
                Intent intent1 = new Intent();
                intent1.putExtra("msg","这是一条有序广播");
                intent1.setAction("zjj_one");
                sendOrderedBroadcast(intent1, null);
                break;
            case R.id.btnSend3:
                //发送一条有序广播
                Intent intent2 = new Intent();
                intent2.putExtra("msg", "这是一条异步广播");
                intent2.setAction("zjj_three");
                sendStickyBroadcast(intent2);
                //先发送后注册
                IntentFilter intentFilter = new IntentFilter("zjj_three");//action用来识别广播
                broadcastReceiver3 = new BroadcastReceiver3();//receiver name
                registerReceiver(broadcastReceiver3,intentFilter);//注册广播
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver3);
    }
}
