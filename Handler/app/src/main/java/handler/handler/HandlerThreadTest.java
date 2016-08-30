package handler.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by zhangjiajing on 2016/8/30.
 * 解决多线程引发的空指针问题
 */
public class HandlerThreadTest extends Activity{
    //创建HandlerThread对象
    private HandlerThread handlerThread;
    //创建Handler对象
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //java设置布局
        TextView textView = new TextView(this);
        textView.setText("handler thread");
        setContentView(textView);

        //命名
        handlerThread = new HandlerThread("handler thread");
        handlerThread.start();
        //指定looper
        handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("currentThread" + Thread.currentThread());
            }
        };
        handler.sendEmptyMessage(1);
    }
}
