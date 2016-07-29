package com.zhangjiajing.zhangjiajing.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhangjiajing.zhangjiajing.Adapter.NewsAdapter;
import com.zhangjiajing.zhangjiajing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private ImageView ivIcon;
    private ListView lvListView;
    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        lvListView = (ListView)findViewById(R.id.lvListView);
        new NewsAsyncTask().execute(URL);
    }

    /**
     * 实现网络的异步访问
     */
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this,newsBeans);
            lvListView.setAdapter(newsAdapter);
        }
    }

    /**
     * 将url对应的json格式数据转化为我们所封装的newsBean
     * @param url
     * @return
     */
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            //new URL(url).openStream()与
            // url.openConnection().getInputStream()相同
            //可根据URL直接联网获取网络数据
            //返回值类型为InputStream
            JSONObject jsonObject;
            NewsBean newsBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean = new NewsBean();
                    String newsIconUrl = newsBean.setNewsIconUrl(jsonObject.getString("picSmall"));
                    String newsTitle = newsBean.setNewsTitle(jsonObject.getString("name"));
                    String newsContent = newsBean.setNewsContent(jsonObject.getString("description"));
                    newsBeanList.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Jrglxls",jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    /**
     * 通过inputStream 解析网页返回的数据
     * @param is
     * @return
     */
    //读取网络信息
    private String readStream(InputStream is){//字节流
        InputStreamReader isr;
        String result = "";
        String line = "";
        try {
            isr = new InputStreamReader(is,"utf-8");//字节流转换为字符流
            BufferedReader br = new BufferedReader(isr);//字符流以Buffer的形式读取出来
            while ((line = br.readLine()) != null){
                result += line;//最后拼接到result
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onClick(View v) {

    }
}
