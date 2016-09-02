package volley.volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhangjiajing on 2016/9/1.
 *
 */
public class RequestApplication extends Application {
    //创建全局的请求队列
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化请求队列 上下文可用APP的全局上下文getApplicationContext()
        requestQueue = Volley.newRequestQueue(this);
    }

    //获取请求队列 返回RequestQueue
    public static RequestQueue getHttpQueues(){
        //直接返回请求好的全局队列
        return requestQueue;
    }
}
