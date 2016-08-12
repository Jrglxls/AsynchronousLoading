package broadcastreceiver.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by zhangjiajing on 2016/8/12.
 * 有序广播
 */
public class BroadcastReceiver2 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Log.d("zjj", "普通广播2 " + msg);
//        //截断广播
//        abortBroadcast();
        Bundle bundle =getResultExtras(true);
        String test = bundle.getString("test");
        Log.d("zjj","得到的处理结果"+test);
    }
}
