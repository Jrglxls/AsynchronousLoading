package json.json;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class HttpJson extends Thread{
    private String url;
    private Context context;

    private ListView listView;
    private JsonAdapter jsonAdapter;

    private Handler handler;

    //创建构造方法
    public HttpJson(String url,ListView listView,JsonAdapter jsonAdapter,Handler handler){
        this.url = url;
        this.listView = listView;
        this.jsonAdapter = jsonAdapter;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            //创建URL对象
            URL httpUrl = new URL(url);
            //创建HttpURLConnection对象 使用URL对象的openConnection()方法
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置超时时间为5秒
            httpURLConnection.setReadTimeout(5000);
            //设置请求但是
            httpURLConnection.setRequestMethod("GET");
            //网络发送请求 创建BufferedReader对象
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            //创建StringBuffer对象
            StringBuffer stringBuffer = new StringBuffer();
            //创建str
            String str;
            //循环读取服务器返回的数据
            while((str=bufferedReader.readLine())!=null){
                //通过StringBuffer去读取完毕
                stringBuffer.append(str);
            }
            //将解析的对象传进去
            final List<PersonInfo> personInfos = parseJson(stringBuffer.toString());
            //子线程向主线程发送消息
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //将数据传递进去
                    jsonAdapter.setData(personInfos);
                    listView.setAdapter(jsonAdapter);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析json
    private List<PersonInfo> parseJson(String json){
        try {
            //创建JSONObject对象
            JSONObject jsonObject = new JSONObject(json);
            //创建PersonInfo list集合
            List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
            //创建SchoolInfo集合
            List<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
            //获取结果码
            int result = jsonObject.getInt("result");
            //结果为1正确
            if (result==1){
                //解析数据
                //创建JSONArray数组 获取personData
                JSONArray personData = jsonObject.getJSONArray("personData");
                //数组创建完 进行遍历
                for (int i=0;i<personData.length();i++){
                    //创建PersonInfo实体对象
                    PersonInfo personInfo = new PersonInfo();
                    //将personInfo实体数据添加到list
                    personInfos.add(personInfo);

                    //获取每行数据
                    JSONObject person = personData.getJSONObject(i);
                    String name = person.getString("name");
                    int age = person.getInt("age");
                    String url = person.getString("url");

                    //设置实体数据
                    personInfo.setName(name);
                    personInfo.setAge(age);
                    personInfo.setUrl(url);

                    //将schoolInfo集合传递给personInfo
                    personInfo.setSchoolInfo(schoolInfos);

                    //获取 schoolData 数组
                    JSONArray schoolData = person.getJSONArray("schoolInfo");

                    for (int j=0;j<schoolData.length();j++){
                        //创建SchoolInfo对象
                        SchoolInfo schoolInfo = new SchoolInfo();

                        JSONObject school = schoolData.getJSONObject(j);
                        String schoolName = school.getString("schoolName");

                        schoolInfo.setSchool_name(schoolName);
                        //将schoolInfo添加到集合当中
                        schoolInfos.add(schoolInfo);
                    }
                }
                return personInfos;
            }else {
                Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
