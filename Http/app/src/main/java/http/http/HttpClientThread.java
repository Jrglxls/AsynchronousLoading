package http.http;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;

/**
 * Created by zhangjiajing on 2016/8/19.
 * HttpClientThread传递数据
 */
public class HttpClientThread extends Thread{
    private String url;

    //get方式 构造方法
    public HttpClientThread(String url){
        this.url = url;
    }

    private String name;
    private String age;

    //post方式 构造方法
    public HttpClientThread(String url,String name,String age){
        this.url = url;
        this.name = name;
        this.url = url;
    }

    private void doHttpClientGet(){
        //创建HttpGet对象 用来发送Get请求
        HttpGet httpGet =new HttpGet(url);
        //创建HttpClient对象，所有的参数都是通过其发送
        HttpClient httpClient = new DefaultHttpClient();
        //创建HttpResponse对象
        HttpResponse httpResponse;
        try {
            //通过execute发送请求 得到响应的HttpResponse对象
            httpResponse = httpClient.execute(httpGet);
            //判断服务器返回的结果码有没有问题 结果码=类型
            if (httpResponse.getStatusLine().getStatusCode() == SC_OK){
                //取出服务器返回的数据
                String content = EntityUtils.toString(httpResponse.getEntity());
                Log.d("zjj",content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost(){
        //创建HttpPost对象用来发送Post请求
        HttpPost httpPost =new HttpPost(url);
        //创建HttpClient对象，所有的参数都是通过其发送
        HttpClient httpClient = new DefaultHttpClient();
        //创建HttpResponse对象
        HttpResponse httpResponse;
        //发送实体数据 通过NameValuePair去存储数据
        //创建ArrayList 存储的泛型为NameValuePaire
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        //添加数据
        list.add(new BasicNameValuePair("name",name));
        list.add(new BasicNameValuePair("age",age));
        try {
            //Entity设置要发送的数据
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            //通过execute发送请求 得到响应的HttpResponse对象
            httpResponse = httpClient.execute(httpPost);
            //判断服务器返回的结果码有没有问题
            if (httpResponse.getStatusLine().getStatusCode() == SC_OK){
                //拿到服务器返回的数据
                String content = EntityUtils.toString(httpResponse.getEntity());
                Log.d("zjj",content);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        //访问get方法
        doHttpClientGet();
        //访问post访问
        doHttpClientPost();
    }
}
