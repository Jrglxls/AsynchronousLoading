package http.http;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private WebView wvWebView;
    private Handler handler;
    private ImageView ivImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wvWebView = (WebView) findViewById(R.id.wvWebiew);
        handler = new Handler();
        ivImageView = (ImageView) findViewById(R.id.ivImageView);

//        /**
//         * 使用WebView展示URL
//         */
//        //创建HttpThread对象 并调用start方法
//        new HttpThread("http://www.baidu.com",wvWebView,handler).start();

        /**
         * 使用ImageView展示图片
         */
        new HttpThread("http://g.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=925ea2e391dda144ce0464e0d3debbc7/0ff41bd5ad6eddc4cf53b20b3fdbb6fd5366338d.jpg",
                ivImageView,handler).start();
    }
}
