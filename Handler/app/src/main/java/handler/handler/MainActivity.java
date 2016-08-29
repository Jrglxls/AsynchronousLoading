package handler.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
    private TextView tvTextView;
    private ImageView ivImageView;
    private Button btnButton;
    //创建Handler对象
    //方法一 方法二
    private Handler handler = new Handler(){
        //接受发送的消息
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接收argue参数
//            tvTextView.setText(""+msg.arg1);
            //接收obj对象
            tvTextView.setText(msg.obj.toString());
        }
    };
    //方法三
    private Handler handler1 = new Handler(new Handler.Callback() {
        //拦截消息 设置为true 则以下接收不到
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(getApplicationContext(),"one",Toast.LENGTH_SHORT).show();
            return true;
        }
    }){
        @Override
        public void handleMessage(Message message) {
            Toast.makeText(getApplicationContext(),"two",Toast.LENGTH_SHORT).show();
        }
    };

    //创建图片数组
    private int images[] = {R.drawable.picture1,
                            R.drawable.picture2,
                            R.drawable.picture3};
    //创建索引 指定当前图片位置
    private int index;
    //创建MyRunnable对象
    private MyRunnable myRunnable = new MyRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTextView = (TextView) findViewById(R.id.tvTextView);
        ivImageView = (ImageView) findViewById(R.id.ivImageView);
        btnButton = (Button) findViewById(R.id.btnButton);

        //新建Thread
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    //发送massage
                    //创建message对象
                    //方法一
//                    Message message = new Message();
                    //方法二
                    Message message = handler.obtainMessage();

                    //指定arg参数 为int类型
                    message.arg1 = 1;

                    //创建person对象
                    Person person = new Person();
                    person.age = 23;
                    person.name = "zjj";
                    //指定object对象
                    message.obj = person;
                    //将message发送到handler
                    //方法一
//                    handler.sendMessage(message);
                    //方法二
                    message.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * 更新UI方法二 类似轮播图
         */
        //隔段时间更新图片
        handler.postDelayed(myRunnable,1000);

        /**
         * 更新UI方法一
         */
//        //创建Thread 并调用start方法
//        new Thread(){
//            //创建run方法
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    //当前线程休眠时间
//                    Thread.sleep(1000);
//                    //使用handler.post中的Runnable方法更新UI
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvTextView.setText("update thread");
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }

    public void doClick(View view) {
        //在消息队列中把Runnable对象移除
        handler.removeCallbacks(myRunnable);
        //传递空消息
        handler1.sendEmptyMessage(1);
    }

    /**
     * 传递object对象实体数据
     */
    class Person{
        public int age;
        public String name;

        @Override
        public String toString() {
            return "name="+name+" age="+age;
        }
    }

    /**
     * 创建Runnable
     */
    class MyRunnable implements Runnable{

        @Override
        public void run() {
            //对当前索引进行自增
            index++;
            //index值进行循环
            index = index%3;
            //根据当前索引取出当前图片
            ivImageView.setImageResource(images[index]);
            //隔段时间更新图片
            handler.postDelayed(myRunnable,1000);
        }
    }
}
