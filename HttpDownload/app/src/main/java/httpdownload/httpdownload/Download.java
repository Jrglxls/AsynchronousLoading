package httpdownload.httpdownload;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zhangjiajing on 2016/8/26.
 *
 */
public class Download {
    private Handler handler;

    //构造方法
    public Download(Handler handler){
        this.handler = handler;
    }

    //在本地通过线程池去下载数据

    //创建线程池对象 固定长度3
    private Executor threadPool = Executors.newFixedThreadPool(3);

    //用到线程池 需要定义一个runnable对象
    static class DownLoadRunnable implements Runnable{
        //指定成员变量
        private String url;
        private String fileName;
        private long start;
        private long end;
        private Handler handler;

        //指定构造方法传递数据
        public DownLoadRunnable(String url,String fileName,long start,long end,Handler handler){
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                //获取传递的url
                URL httpUrl = new URL(url);
                //获取HttpConnection对象
                HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
                //设置请求超时时间
                httpURLConnection.setReadTimeout(5000);
                //指定请求数据的长度
                httpURLConnection.setRequestProperty("Range", "byte=" + start + "-" + end);
                //设置请求方法
                httpURLConnection.setRequestMethod("GET");
                //创建RandomAccessFile对象向本地文件写入数据 方式是rwd可读可写可执行
                RandomAccessFile randomAccessFile = new RandomAccessFile(new File(fileName),"rwd");
                //在指定区域进行读写 从start开始
                randomAccessFile.seek(start);
                //开始读取数据
                //创建InputStream获取输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //创建缓冲区
                byte[] bytes = new byte[1024*4];
                int length = 0;

                //读取数据
                //不等于-1
                while ((length = inputStream.read(bytes))!=-1){
                    //从0到length长度读取
                    randomAccessFile.write(bytes,0,length);
                }
                //读取之后 关掉
                randomAccessFile.close();
                inputStream.close();

                //下载完成之后 向主线程发送消息
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void downLoadFile(String url){
        try {
            //获取传递的url
            URL httpUrl = new URL(url);
            //获取HttpConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求超时时间
            httpURLConnection.setReadTimeout(5000);
            //设置请求方法
            httpURLConnection.setRequestMethod("GET");
            //创建count获取下载总体长度
            int count = httpURLConnection.getContentLength();
            //在本地通过三个线程去下载 每个线程去下载总体长度的三分之一
            int block = count/3;
            //指定文件名字
            String fileName = getFileName(url);
            //指定文件下载地址 SD卡
            //拿到SD卡的file
            File parent = Environment.getExternalStorageDirectory();
            //在SD卡创建文件
            File fileDownLoad = new File(parent,fileName);

            //向线程池提交任务
            //开启三个线程
            /**
             * 算法 每个线程下载长度
             */
            for(int i=0;i<3;i++){
                //算出每个线程起始位置终止位置 取数据的区间
                long start = i*block;
                long end = (i+1)*block-1;
                if (i==2){
                    end = count;
                }
                //创建DownLoadRunnable对象
                DownLoadRunnable downLoadRunnable = new DownLoadRunnable(url,fileDownLoad.getAbsolutePath(),start,end,handler);
                //通过threadPool线程池去提交任务
                threadPool.execute(downLoadRunnable);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取出url后缀名 作为文件名
     */
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
}
