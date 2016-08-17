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

        //创建HttpThread对象 并调用start方法
        new HttpThread("http://www.baidu.com",wvWebView,handler).start();
    }
}
