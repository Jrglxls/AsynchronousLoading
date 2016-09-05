package volley.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by zhangjiajing on 2016/9/5.
 * 图片缓存 使用Lrucache
 */
public class BitmapCache implements ImageLoader.ImageCache {
    public LruCache<String,Bitmap> lruCache;
    public int max = 10*1024*1024;

    public BitmapCache(){
        lruCache = new LruCache<String , Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }

    //返回缓存的内容
    @Override
    public Bitmap getBitmap(String s) {
        return lruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        lruCache.put(s,bitmap);
    }
}
