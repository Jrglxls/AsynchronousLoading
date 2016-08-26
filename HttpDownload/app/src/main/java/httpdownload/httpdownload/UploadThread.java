package httpdownload.httpdownload;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by zhangjiajing on 2016/8/26.
 *
 */
public class UploadThread extends Thread{
    private String fileName;
    private String url;

    public UploadThread(String fileName,String url){
        this.fileName = fileName;
        this.url = url;

    }

    @Override
    public void run() {
        super.run();
        //表单分割线的描述符
        String boundary = "-------------------something";
        //前缀
        String prefix = "--";
        //换行符
        String end = "\r\n";
        try {
            //获取传递的url
            URL httpUrl  = new URL(url);
            //获取HttpConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求方法
            httpURLConnection.setRequestMethod("POST");
            //设置允许向服务器写入数据
            httpURLConnection.setDoOutput(true);
            //设置允许向服务器读出数据
            httpURLConnection.setDoInput(true);
            //设置服务器上传请求属性 拼装实体数据给服务器
            httpURLConnection.setRequestProperty("Content-Type","muktipart/form-data;boundary"+boundary);
            //创建输出流对象 拿到输出流
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            //向服务器写数据
            //第一行数据
            dataOutputStream.writeBytes(prefix+boundary+end);
            //第二行数据
            dataOutputStream.writeBytes("something"+"something"+"somgthing"+"\""+end);
            //向服务器写实体数据
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            //创建缓冲区
            byte[] bytes = new byte[1024*4];
            int length = 0;

            //读取数据
            //不等于-1
            while ((length = fileInputStream.read(bytes))!=-1){
                //从0到length长度读取
                //将读到的数据写给服务器
                dataOutputStream.write(bytes,0,length);
            }
            //加上回车换行符
            dataOutputStream.writeBytes(end);
            //最后一行数据
            dataOutputStream.writeBytes(prefix + boundary + prefix + end);
            //刷新缓冲区
            dataOutputStream.flush();

            //读取服务器返回的信息
            //创建BufferedReader对象 获取服务器返回的数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            //创建StringBuffer对象 存储数据
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            //将bufferedReader每一行数据赋值给str
            while ((str = bufferedReader.readLine())!=null){
                //将读到的数据写给StringBuffer
                stringBuffer.append(str);
            }

            //使用完关闭
            dataOutputStream.close();
            bufferedReader.close();

            //打印读取的数据
            Log.d("zjj",stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
