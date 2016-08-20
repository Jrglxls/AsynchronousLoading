package json.json;

import java.util.List;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class PersonInfo {
    private String name;
    private int age;
    private String url;
    private List<SchoolInfo> schoolInfo;

    public List<SchoolInfo> getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(List<SchoolInfo> schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
