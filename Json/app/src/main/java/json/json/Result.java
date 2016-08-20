package json.json;

import java.util.List;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class Result {
    private int result;
    private List<PersonInfo> personData;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<PersonInfo> getPersonData() {
        return personData;
    }

    public void setPersonData(List<PersonInfo> personData) {
        this.personData = personData;
    }
}
