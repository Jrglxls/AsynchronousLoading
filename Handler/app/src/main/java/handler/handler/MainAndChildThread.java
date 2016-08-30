package handler.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhangjiajing on 2016/8/30.
 * 问题 不能remove掉消息
 */
public class MainAndChildThread extends Activity implements View.OnClickListener{
    private Button btnMainThread,btnChildThread;

    //定义主线程的handler并初始化
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //向子线程发送消息
            Message message = new Message();
            childHandler.sendMessageDelayed(message, 1000);
            System.out.println("main handler");
        }
    };

    //定义子线程的handler
    private Handler childHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_child_thread);
        btnMainThread = (Button) findViewById(R.id.btnMainThread);
        btnChildThread = (Button) findViewById(R.id.btnChildThread);
        btnMainThread.setOnClickListener(this);
        btnChildThread.setOnClickListener(this);

        //创建子线程
        HandlerThread handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        //初始化子线程handler 使用子线程的looper
        childHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //向主线程发送message
                Message message = new Message();
                mainHandler.sendMessageDelayed(message, 1000);
                System.out.println("child handler");

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMainThread:
                mainHandler.sendEmptyMessage(1);
                break;
            case R.id.btnChildThread:
                mainHandler.removeMessages(1);
                System.out.println("removeMessages");
                break;
        }
    }
}
