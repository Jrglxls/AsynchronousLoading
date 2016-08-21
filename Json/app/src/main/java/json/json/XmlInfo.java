package json.json;

/**
 * Created by Jrglxls on 2016/8/21.
 */
public class XmlInfo {
    private String name;
    private int age;
    private String school;

    @Override
    public String toString() {
        return "[name="+name+"age+"+age+"school="+school+"]";
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
