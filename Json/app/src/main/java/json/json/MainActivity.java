package json.json;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置result
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setResult(1);

        //resultInfo.personData设置person数组
        List<PersonInfo> list = new ArrayList<PersonInfo>();
        resultInfo.setPersonData(list);

        /**
         * 数据一
         */
        //person设置name age url
        PersonInfo person1 = new PersonInfo();
        person1.setName("zjj");
        person1.setAge(22);
        person1.setUrl("http://baidu.com");

        //SchoolInfo设置Schoolname
        List<SchoolInfo> schoolInfos1 = new ArrayList<SchoolInfo>();
        SchoolInfo schoolInfo1 = new SchoolInfo();
        schoolInfo1.setSchool_name("湖北大学");
        SchoolInfo schoolInfo2 = new SchoolInfo();
        schoolInfo2.setSchool_name("武汉大学");
        schoolInfos1.add(schoolInfo1);
        schoolInfos1.add(schoolInfo2);
        person1.setSchoolInfo(schoolInfos1);
        list.add(person1);

        /**
         * 数据二
         */
        //person设置name age url
        PersonInfo person2 = new PersonInfo();
        person2.setName("jcc");
        person2.setAge(27);
        person2.setUrl("http://Jrglxls.com");

        //SchoolInfo设置Schoolname
        List<SchoolInfo> schoolInfos2 = new ArrayList<SchoolInfo>();
        SchoolInfo schoolInfo3 = new SchoolInfo();
        schoolInfo3.setSchool_name("北大");
        SchoolInfo schoolInfo4 = new SchoolInfo();
        schoolInfo4.setSchool_name("汉大");
        schoolInfos2.add(schoolInfo3);
        schoolInfos2.add(schoolInfo4);
        person2.setSchoolInfo(schoolInfos2);
        list.add(person2);

        //实体数据创建完毕，需要调用json字符串
        Gson gson = new Gson();
        System.out.println(gson.toJson(resultInfo));
        Log.d("zjj", gson.toJson(resultInfo));
    }
}
