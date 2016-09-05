package volley.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volley_Get();
        volley_Post();
    }

    //volley与activity进行关联
    @Override
    protected void onStop() {
        super.onStop();
        //通过给定的tag值，将指点的队列全部关闭
        RequestApplication.getHttpQueues().cancelAll("zjjpost");
    }

    private void volley_Post() {
        String url = "something";
        /**
         * 使用StringRequest
         */
        //创建StringRequest请求对象 参数：方法 url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //请求成功返回
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                //输出json格式 解析数据
            }
        }, new Response.ErrorListener() {
            //请求失败返回
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this,volleyError.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            //使用post方式传递参数 getParams()方法
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("sonething","something");
                return hashMap;
            }
        };
        //设置请求对象tag标签 可以通过tag标签在请求队列中进行寻找
        stringRequest.setTag("zjjpost");
        //将request添加到全局队列里
        RequestApplication.getHttpQueues().add(stringRequest);

        /**
         * 使用JSONObjectRequest
         */
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sonething","something");
        //将map转化为object对象
        JSONObject jsonObject = new JSONObject(hashMap);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
            // TODO: 2016/9/5
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            // TODO: 2016/9/5
            }
        });
        //设置请求对象tag标签 可以通过tag标签在请求队列中进行寻找
        jsonObjectRequest.setTag("zjjpost");
        //将request添加到全局队列里
        RequestApplication.getHttpQueues().add(stringRequest);
    }

    //使用get请求
    private void volley_Get() {
        String url = "something";
        /**
         * 使用StringRequest
         */
        //创建StringRequest请求对象 参数：方法 url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //请求成功返回
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                //输出json格式 解析数据
            }
        }, new Response.ErrorListener() {
            //请求失败返回
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this,volleyError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //设置请求对象tag标签 可以通过tag标签在请求队列中进行寻找
        stringRequest.setTag("zjjget");
        //将request添加到全局队列里
        RequestApplication.getHttpQueues().add(stringRequest);

        /**
         * 使用JSONObjectRequest
         */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
                //输出json格式 解析数据
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this,volleyError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //设置请求对象tag标签 可以通过tag标签在请求队列中进行寻找
        jsonObjectRequest.setTag("zjjget");
        //将request添加到全局队列里
        RequestApplication.getHttpQueues().add(stringRequest);

        /**
         * 使用JSONArrayRequest
         */
        //类似JSONObjectRequest
    }

    //使用二次封装
    private void twoPackage(){
        String url = "something";
        VolleyRequest.RequestGet(this, url, "zjjget", new VolleyInterface(this,VolleyInterface.listener,VolleyInterface.errorListener) {
            @Override
            public void onSuccess(String result) {
            // TODO: 2016/9/5
            }

            @Override
            public void onError(VolleyError volleyError) {
            // TODO: 2016/9/5
            }
        });
    }
}
