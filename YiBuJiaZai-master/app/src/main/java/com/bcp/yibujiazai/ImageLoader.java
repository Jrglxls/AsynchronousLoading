package com.bcp.yibujiazai;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/6/7.
 */
public class ImageLoader {
    //Lru   创建Cache
    private LruCache<String, Bitmap> mCashes;

    //滑动优化
    private ListView mListView;
    private Set<NewsAsyncTask> mTask;

    public ImageLoader(ListView listView) {
        //滑动优化
        mListView = listView;
        mTask = new HashSet<>();

        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int casheSize = maxMemory / 4;
        mCashes = new LruCache<String, Bitmap>(casheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时调用
                return value.getByteCount();

            }
        };
    }

    //将内容写入缓存
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCashes.put(url, bitmap);
        }
    }

    //从缓存读取内容
    public Bitmap getBitmapFromCache(String url) {
        return mCashes.get(url);
    }

    //多线程方式
    private ImageView mImageView;
    private String mUrl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }

        }
    };


    //多线程
    public void showImageByThread(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromURL(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //AsyncTask
    public void showImageByAsyncTask(ImageView imageView, String url) {
        // get image from cache
        Bitmap bitmap = getBitmapFromCache(url);

//        //AsyncTask
//        if (bitmap == null) {
//            //not in cache --- download from url
//            new NewsAsyncTask(imageView, url).execute(url);
//        } else {
//            imageView.setImageBitmap(bitmap);
//        }

        //滑动优化
        if (bitmap == null) {
            //not in cache --- download from url
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void cancelAllTask(){
        if (mTask!=null){
            for (NewsAsyncTask task:mTask){
                task.cancel(false);
            }
        }
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView;
        private String mUrl;

//        public NewsAsyncTask(ImageView imageView, String url) {
//            mImageView = imageView;
//            mUrl = url;
//        }

        //滑动优化
        public NewsAsyncTask(String url) {
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //AsyncTask
            //return getBitmapFromURL(params[0]);

            //LruCache
            // get image from url
            Bitmap bitmap = getBitmapFromURL(params[0]);
            if (bitmap != null) {
                //将不在缓存的图片加入缓存
                addBitmapToCache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //AysncTask
//            if (mImageView.getTag().equals(mUrl)) {
//                mImageView.setImageBitmap(bitmap);
//            }

            //滑动优化
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }

    //滑动优化 用于加载从start到end的所有图片
    public void loadImages(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            // get image from cache
            Bitmap bitmap = getBitmapFromCache(url);

            if (bitmap == null) {
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
