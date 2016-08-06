package com.zhangjiajing.zjjasynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zhangjiajing on 2016/8/5.
 */
public class ImageTest extends Activity{
    private ImageView imageView;
    private ProgressBar progressBar;
    private static String imageUrl = "http://imgsrc.baidu.com/forum/wh%3D1024%2C768/sign=0985b6228094a4c20a76ef2a3ec62ff9/5d3641e736d12f2e6635f94d48c2d562843568c1.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //使用AsyncTask 设置传递进去的参数
        new AsyncTask().execute(imageUrl);
    }

    //创建AsyncTask 完成图片的一步处理
    class AsyncTask extends android.os.AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * 获取传递进来的参数url 将参数转化为bitmap
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            //获取到传进来的参数 即URL
            String asyncTaskUrl = params[0];//数组的第0位
            //定义bitmap 所要获取的
            Bitmap asyncTaskBitmap = null;
            /**
             * 网络操作所必须的参数 将url转化为bitmap
             */
            //定义网络连接对象
            URLConnection connection;
            //用于获取数据的输入流
            InputStream inputStream;

            try {
                //将url调用openConnection方法来获取connection对象
                connection = new URL(asyncTaskUrl).openConnection();//获取网络连接对象
                //通过connection调用getInputStream()方法获取输入流
                inputStream = connection.getInputStream();
                //使用BufferedInputStream将inputStream包装
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                //人为延迟
                Thread.sleep(3000);
                //通过BitmapFactory.decodeStream()方法将输入流解析成bitmap
                asyncTaskBitmap = BitmapFactory.decodeStream(bufferedInputStream);
                //解析完成后 关闭输入流inputStream
                inputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //将bitmap作为返回值
            return asyncTaskBitmap;
        }

        /**
         * 操作UI
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
        }
    }
}
