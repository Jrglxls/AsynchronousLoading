package volley.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by zhangjiajing on 2016/9/5.
 *
 */
public class ImageActivity extends Activity{
    private String url;
    private ImageView ivImageview;
    private NetworkImageView networkImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_volley);
        url = "http://npimg.39.net/PictureLib/A/CT1500016770/CT1500016777/2012-09-24/201209241925446855.jpg";
        ivImageview = (ImageView) findViewById(R.id.ivImageview);
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
//        imageVolley();
        imageVolleyCache();
        netWorkImageView();
    }

    /**
     * 使用volley加载图片
     */
    public void imageVolley(){
        //创建请求对象
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                ivImageview.setImageBitmap(bitmap);
            }
        }, 500, 500, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ivImageview.setBackgroundResource(R.mipmap.ic_launcher);
            }
        });
        //将request添加到全局队列里
        RequestApplication.getHttpQueues().add(imageRequest);
    }

    /**
     * 使用缓存加载图片 使用LruCache
     */
    public void imageVolleyCache(){
        ImageLoader imageLoader = new ImageLoader(RequestApplication.getHttpQueues(), new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(ivImageview,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        imageLoader.get(url,imageListener);
    }

    /**
     * 使用volley自带的networkImageView
     */
    public void netWorkImageView(){
        ImageLoader imageLoader = new ImageLoader(RequestApplication.getHttpQueues(), new BitmapCache());
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl(url,imageLoader);
    }

}
