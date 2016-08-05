package com.zjjLruCache.zjjLruCache.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhangjiajing on 2016/7/28.
 * 图片缓存
 */
public class ImageLoader {
    private ImageView mImageView;
    private String mURL;
    //创建LruCache key url   value bitmap进行缓存
    private LruCache<String,Bitmap> mLruCache;

    //创建构造方法 初始化LruCache
    public ImageLoader(){
        //获取当前可用内存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设置内存为可用大小的1/4
        int cacheSize = maxMemory/4;
        //LruCache初始化
        mLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                //将bitmap的实际大小保存进去
                return value.getByteCount();
            }
        };
    }

    //将内容保存到LruCache set方法
    //增加到缓存 先判断是否存在
    public void addBitmapToCache(String url,Bitmap bitmap){
        if (getBitmapFromCache(url) == null){
            mLruCache.put(url,bitmap);
        }
    }

    //使用LruCache的内容 get方法 通过url返回指定的bitmap
    //从缓存中获取数据
    public Bitmap getBitmapFromCache(String url){
        return mLruCache.get(url);
    }



    //android单线程 使用handler处理消息的传递
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mURL))
            mImageView.setImageBitmap((Bitmap) msg.obj);
        }
    };
    
    //多线程加载图片
    public void showImageByThread(ImageView imageView, final String url){
        mImageView = imageView;
        mURL = url;
        //多线程需要使用thread
        new Thread(){
            @Override
            public void run() {
                super.run();
                //直接获取图片
                Bitmap bitmap = getBitmapFromURL(url);
                Message message = Message.obtain();//提高使用效率
                message.obj = bitmap;//obj设置为bitmap
                handler.sendMessage(message);
            }
        }.start();//start thread
    }

    //从url获取bitmap
    public Bitmap getBitmapFromURL(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);//通过urlString 转化为url
            //创建connection对象 创建inputstream
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //buffer将connection包装一下
            is = new BufferedInputStream(httpURLConnection.getInputStream());
            //inputstream通过Android API转化为bitmap
            bitmap = BitmapFactory.decodeStream(is);
            //释放所连接的资源
            httpURLConnection.disconnect();
//            Thread.sleep(1000);//睡眠 模拟网络不好
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            //将inputStream释放
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 使用AsyncTask设置图片
     * @param imageView
     * @param url
     */

    public void showImageByAsyncTask(ImageView imageView,String url){
        //从缓存中去出对应的图片
        Bitmap bitmap = getBitmapFromCache(url);
        //如果缓存没有，则去下载
        if (bitmap == null){
            new NewsAsyncTask(imageView,url).execute(url);
        }else {
            //如果缓存有，直接使用
            mImageView.setImageBitmap(bitmap);
        }

        //未使用缓存
//        new NewsAsyncTask(imageView,url).execute(url);
    }

    private class NewsAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private ImageView mImageView;
        private String mUrl;

        public NewsAsyncTask(ImageView imageView,String url){
            mImageView = imageView;
            mUrl = url;

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //使用LruCache缓存
            String url = params[0];
            //从网络获取图片
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap != null){
                //将不在缓存的图片加入缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
            //未使用缓存
//            return getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
