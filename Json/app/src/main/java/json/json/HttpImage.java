package json.json;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class HttpImage extends Thread{
    private ImageView imageView;
    private String url;
    private Handler handler;

    public HttpImage(ImageView imageView,String url,Handler handler){
        this.imageView = imageView;
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            //创建URL对象
            URL httpUrl = new URL(url);
            //创建HttpURLConnection对象 使用URL对象的openConnection()方法
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置超时时间为5秒
            httpURLConnection.setReadTimeout(5000);
            //设置请求但是
            httpURLConnection.setRequestMethod("GET");
            //获取输入流InputStream对象
            InputStream inputStream = httpURLConnection.getInputStream();
            //设置Bitmap对象 传入InputStream输入流
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //在handler中更新UI
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
