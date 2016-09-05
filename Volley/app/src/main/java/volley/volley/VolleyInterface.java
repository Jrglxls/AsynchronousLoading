package volley.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by zhangjiajing on 2016/9/5.
 *
 */
public abstract class VolleyInterface {
    public Context context;
    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;

    //创建构造方法
    public VolleyInterface(Context context,Response.Listener<String> listener,Response.ErrorListener errorListener){
        this.context = context;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public Response.Listener<String> listener(){
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onSuccess(s);
            }
        };
        return listener;
    }

    public abstract void onSuccess(String result);


    public Response.ErrorListener errorListener(){
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onError(volleyError);
            }
        };
        return errorListener;
    }

    public abstract void onError(VolleyError volleyError);
}
