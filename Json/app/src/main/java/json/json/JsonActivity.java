package json.json;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class JsonActivity extends Activity {
    private ListView lvJsonList;
    private JsonAdapter jsonAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        lvJsonList = (ListView) findViewById(R.id.lvJsonList);
        jsonAdapter = new JsonAdapter(this);
    }
}
