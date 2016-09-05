package volley.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by zhangjiajing on 2016/9/5.
 * Volley的二次封装
 */
public class VolleyRequest {
    public static StringRequest stringRequest;
    public static Context context;

    public static void RequestGet(Context context,String url,String tag,VolleyInterface volleyInterface){
        //取消tag标签的队列，防止重复请求
        RequestApplication.getHttpQueues().cancelAll(tag);
        //将stringRequest进行实例化 创建请求对象
        stringRequest = new StringRequest(Request.Method.GET,url,volleyInterface.listener(),volleyInterface.errorListener());
        //设置请求标签
        stringRequest.setTag(tag);
        //将请求添加到队列里
        RequestApplication.getHttpQueues().add(stringRequest);
        //开始
        RequestApplication.getHttpQueues().start();
    }

    public static void RequestPost(Context context,String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface){
        //取消tag标签的队列，防止重复请求
        RequestApplication.getHttpQueues().cancelAll(tag);
        //将stringRequest进行实例化 创建请求对象
        stringRequest = new StringRequest(url,volleyInterface.listener(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        //设置请求标签
        stringRequest.setTag(tag);
        //将请求添加到队列里
        RequestApplication.getHttpQueues().add(stringRequest);
        //开始
        RequestApplication.getHttpQueues().start();

    }
}
