package http.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhangjiajing on 2016/8/17.
 * 网络访问是耗时操作 在线程中处理
 */
public class HttpThread extends Thread {
    private String url;//传递url参数
    private WebView webView;//创建webview
    private Handler handler;//在主线程更新UI 需要创建handler
    private ImageView imageView;//传递的imageview

    //创建构造方法，对参数进行初始化 参数：url，webview,handler
    public HttpThread(String url,WebView webView,Handler handler){
        //对参数进行初始化
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    //创建构造方法，对参数进行初始化 参数：url，webview,handler
    public HttpThread(String url,ImageView imageView,Handler handler){
        //对参数进行初始化
        this.url = url;
        this.imageView = imageView;
        this.handler = handler;
    }

    //run方法处理网络耗时
    @Override
    public void run() {
        try {
            //与网络进行交互，创建url 将外界传入参数传入
            URL httpUrl = new URL(url);
            //对url进行http访问 得到HttpUrlConnection对象 通过URL.openConnection方法
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //httpURLConnection可对网络进行操作
            //首先，设置请求超时的时间
            httpURLConnection.setReadTimeout(5000);
            //设置网络请求方式 一般使用get
            httpURLConnection.setRequestMethod("GET");

            /**
             * 使用ImageView展示图片
             */
            //得到输入流
            httpURLConnection.setDoInput(true);
            InputStream inputStream =httpURLConnection.getInputStream();
            //进行读取 把图片下载到文件 设置文件输出流
            FileOutputStream fileOutputStream = null;
            File downloadFile = null;
            //判断SD卡是否存在 SD卡是否挂载的参数
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //如果SD卡挂载创建文件
                //设置下载文件的名称为创建时间
                String fileName = String.valueOf(System.currentTimeMillis());
                //设置parent目录就是SD卡的目录
                File parent =Environment.getExternalStorageDirectory();
                //downloadFile，指定目录为SD卡的目录
                downloadFile = new File(parent,fileName);
                //向目录中写文件
                fileOutputStream = new FileOutputStream(downloadFile);
            }

            //写文件之前 创建缓冲区
            byte[] bytes = new byte[2*1024];
            //指定长度
            int length;

            //写数据
            if (fileOutputStream!=null){
                //读流 length不等于-1的情况下就表明一直有数据
                while ((length = inputStream.read(bytes))!=-1 ){
                    //从0开始到length写入
                    fileOutputStream.write(bytes,0,length);
                }
            }

            //创建Bitmap 得到图片
            final Bitmap bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath());

            //更新UI
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //设置图片
                    imageView.setImageBitmap(bitmap);
                }
            });

            /**
             * 使用WebView展示URL
             */
//            //创建StringBuffer进行缓冲
//            final StringBuffer stringBuffer = new StringBuffer();
//            //创建包装类BufferedReader InputStreamReader将字符流转化为字节流
//            //传入httpURLConnection.getInputStream读入流
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(httpURLConnection.getInputStream()));
//            //创建String类型的变量
//            String str;
//            //得到返回的信息 读出流的所有数据 readline读一行
//            while ((str=bufferedReader.readLine())!=null){
//                //向StringBuffer填充数据
//                stringBuffer.append(str);
//            }
//            //填充完毕之后，使用Handler
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    //拿到webview 获取数据 数据，类型，
//                    webView.loadData(stringBuffer.toString(),"text/html;charset=utf-8",null);
//                }
//            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
