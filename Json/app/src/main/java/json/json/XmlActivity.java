package json.json;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by Jrglxls on 2016/8/21.
 *
 */
public class XmlActivity extends Activity {
    private TextView tvXml;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml);
        tvXml = (TextView) findViewById(R.id.tvXml);
        handler = new Handler();

        String url = "something";
        XmlThread xmlThread = new XmlThread(url,tvXml,handler);
        xmlThread.start();
    }
}
