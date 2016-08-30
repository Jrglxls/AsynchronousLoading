package handler.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;


/**
 * Created by zhangjiajing on 2016/8/30.
 *
 */
public class UpdateUI extends Activity {
    private TextView tvUpdateUI;

    //使用handler更新UI
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //方法二
            tvUpdateUI.setText("ok");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_ui);
        tvUpdateUI = (TextView) findViewById(R.id.tvUpdateUI);

        //在子线程更新UI
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //使当前线程休眠
                    Thread.sleep(2000);
                    // TODO: 2016/8/30 执行handler方法
//                    handler1();//方法一
                    handler2();//方法二 最常用
//                    updateUI();//方法三
//                    viewUI();//方法四
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //方法一 在子线程使用handler.post Runnable方法中更新UI
    private void handler1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tvUpdateUI.setText("ok");
            }
        });
    }

    /**
     * 最常见
     */
    //方法二 直接在子线程中sendEmptyMessage发送消息
    //在handler.handleMessage中更新UI
    private void handler2(){
        handler.sendEmptyMessage(1);
    }

    //方法三 runOnUiThread中Runnable方法
    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvUpdateUI.setText("ok");
            }
        });
    }

    //方法四 通过控件.post Runnable
    private void viewUI(){
        tvUpdateUI.post(new Runnable() {
            @Override
            public void run() {
                tvUpdateUI.setText("ok");
            }
        });
    }
}
