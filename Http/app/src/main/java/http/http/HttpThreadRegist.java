package http.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by zhangjiajing on 2016/8/18.
 */
public class HttpThreadRegist extends Thread{
    //指定url参数
    String url;
    String name;
    String age;

    public HttpThreadRegist(String url,String name,String age){
        this.url = url;
        this.name = name;
        this.age = age;

    }

    /**
     * get方式传递数据
     */
    public void doGet(){
        //重新构造url
        url = url+"?name="+name+"&age="+age;
        try {
            //创建url对象 得到url
            URL httpUrl = new URL(url);
            //得到HttpConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求方式
            httpURLConnection.setRequestMethod("GET");
            //设置请求超时时间
            httpURLConnection.setReadTimeout(5000);
            //创建BufferedReader new InputStreamReader得到HttpConnection输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            //创建str
            String str;
            //创建StringBuffer
            StringBuffer stringBuffer = new StringBuffer();
            //读取返回的信息
                //读取每行 当行不为空
            while ((str=bufferedReader.readLine())!=null){
                //使用StringBuffer进行填充数据
                stringBuffer.append(str);
            }

            Log.d("zjj",stringBuffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post方式传递参数
     */
    private void doPost(){
        try {
            //创建url对象 得到url
            URL httpUrl = new URL(url);
            //得到HttpConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求方式
            httpURLConnection.setRequestMethod("Post");
            //设置请求超时时间
            httpURLConnection.setReadTimeout(5000);
            //得到输出流对象
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //构造字节 提交实体数据
            String content = "name"+name+"age"+age;
            //向服务器提交数据
            outputStream.write(content.getBytes());
            //创建BufferedReader new InputStreamReader得到HttpConnection输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            //创建str
            String str;
            //创建StringBuffer
            StringBuffer stringBuffer = new StringBuffer();
            //读取返回的信息
                //读取每行 当行不为空
            while ((str=bufferedReader.readLine())!=null){
                stringBuffer.append(str);
            }

            Log.d("zjj",stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        doGet();

        doPost();

    }
}
