package broadcastreceiver.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zhangjiajing on 2016/8/12.
 * 异步广播
 */
public class BroadcastReceiver3 extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("zjj","收到了异步广播");
    }
}
