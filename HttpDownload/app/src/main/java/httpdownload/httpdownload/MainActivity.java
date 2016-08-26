package httpdownload.httpdownload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button btnDownLoad;
    private TextView tvDownLoad;

    private int count = 0;

    //更新UI操作 创建Handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int result = msg.what;
            count +=result;
            //如果count为3，表明三个线程下载任务已完成
            if (count==3){
                //更新文本信息
                tvDownLoad.setText("下载完毕");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownLoad = (Button) findViewById(R.id.btnDownLoad);
        tvDownLoad = (TextView) findViewById(R.id.tvDownLoad);

        btnDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启新线程 不允许在主线程中访问网络
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //创建DownLoad对象
                        Download download = new Download(handler);
                        download.downLoadFile("http://g.hiphotos.baidu.com/baike/c0%3Dbaike220%2C5%2C5%2C220%2C73/sign=8c2e212aecc4b7452099bf44ae957572/b999a9014c086e069bee984805087bf40ad1cb6b.jpg");
                    }
                }.start();
            }
        });
    }
}
