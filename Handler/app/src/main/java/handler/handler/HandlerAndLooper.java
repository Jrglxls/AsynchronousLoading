package handler.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by zhangjiajing on 2016/8/29.
 * ActivityThread 默认整个应用程序进行创建，创建Activity并回调各种方法
 * main 默认创建一个线程 所有应用程序更新UI线程通过main线程
 * Looper 默认创建Looper
 * messageQueue 默认创建messageQueue
 * 将创建的Handler与默认的进行关联
 * threadlocal 在线程当中保存变量信息 set get
 */

public class HandlerAndLooper extends Activity {
    //创建Handler对象 主线程中
    //不可执行耗时操作，会进入卡死状态
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            System.out.println("UI Thread" + Thread.currentThread());
//        }
//    };

    //创建Handler对象
    private Handler handler;

    //创建MyThread对象
    private MyThread myThread ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //java设置布局
        TextView textView = new TextView(this);
        textView.setText("hello handler");
        setContentView(textView);

        myThread = new MyThread();
        myThread.start();
//        try {
//            //当前线程休眠
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //发送消息
//        myThread.handler.sendEmptyMessage(1);//子线程
//        handler.sendEmptyMessage(1);//主线程

        //默认轮询新建looper对象
        handler = new Handler(myThread.looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println(msg);
            }
        };
        handler.sendEmptyMessage(1);



    }

    /**
     * 自定义线程
     */
    class MyThread extends Thread{
        //子线程中
        public Handler handler;

        //定义looper对象
        public Looper looper;

        //线程实现run方法
        @Override
        public void run() {
            super.run();
            //创建Looper
            Looper.prepare();

            //指定looper对象
            looper = Looper.myLooper();

            //创建Handler对象 默认情况下根据当前线程拿到Looper
            //子线程拿到Looper
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //打印当前线程id
                    System.out.println("currentThreadThread"+Thread.currentThread());
                }
            };
            //循环处理消息
            Looper.loop();
        }
    }

}
