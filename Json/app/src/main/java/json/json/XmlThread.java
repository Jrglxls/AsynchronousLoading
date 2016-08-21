package json.json;

import android.os.Handler;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jrglxls on 2016/8/21.
 */
public class XmlThread extends Thread{
    private String url;
    private TextView textView;
    private Handler handler;

    public XmlThread(String url,TextView textView,Handler handler){
        this.url = url;
        this.textView = textView;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            //创建url对象 传入外来的url
            URL httpUrl = new URL(url);
            //创建HttpURLConnect对象 url.openConnection()方法
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置超时时间
            httpURLConnection.setReadTimeout(5000);
            //设置请求方式 GET
            httpURLConnection.setRequestMethod("GET");
            //创建InputStream对象得到服务器返回的输入流
            InputStream inputStream = httpURLConnection.getInputStream();

            //对xml文件进行解析 使用android原生的pull解析
            //得到XmlPullParserFactory对象
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            //通过工厂对象得到XmlPullParser对象
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

            //使用XmlPullParser对象对服务器返回的xml对象进行解析 将输入流传入
            xmlPullParser.setInput(inputStream,"UTF-8");

            //解析数据 pull解析基于事件驱动
            //获取事件类型对象 evenType
            int evenType = xmlPullParser.getEventType();
            //通过集合去存储服务器解析的数据
            final List<XmlInfo> list = new ArrayList<XmlInfo>();
            //获取XmlInfo对象并初始化
            XmlInfo xmlInfo = null;
            //根据Type类型进行处理
            //当evenType不等于END_DOCUMENT 表示有数据
            while(evenType!=XmlPullParser.END_DOCUMENT){
                //获取标签名字
                String data = xmlPullParser.getName();
                //根据evenType类型进行处理
                switch (evenType){
                    case XmlPullParser.START_TAG:
                        //设置数据
                        if ("girl".equals(data)){
                            //创建xmlInfo对象
                            xmlInfo = new XmlInfo();
                        }
                        if ("name".equals(data)){
                            xmlInfo.setName(xmlPullParser.nextText());
                        }
                        if ("age".equals(data)){
                            xmlInfo.setAge(Integer.parseInt(xmlPullParser.nextText()));
                        }
                        if ("school".equals(data)){
                            xmlInfo.setSchool(xmlPullParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //添加数据至list
                        if ("girl".equals(data)&&xmlInfo!=null){
                            list.add(xmlInfo);
                        }
                        break;
                }
                //手动添加事件处理
                evenType = xmlPullParser.next();
            }
            //更新ui
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(list.toString());
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
