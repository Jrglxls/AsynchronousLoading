package http.http;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhangjiajing on 2016/8/18.
 * 使用HttpThreadRegist 和 HttpClientThread传递数据
 */
public class RegistActivity extends Activity {
    //初始化
    private EditText etName,etAge;
    private Button btnRegist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = null;//设置url

                /**
                 * 使用HttpThreadRegist传递数据
                 */
                new HttpThreadRegist(url,etName.getText().toString(),etAge.getText().toString()).start();
                /**
                 * 使用HttpClientThread传递数据 get方式
                 */
                url = url+"?name="+etName.getText().toString()+"&age="+ etAge.getText().toString();
                new HttpClientThread(url);
                /**
                 * 使用HttpClientThread传递参数 post方式
                 */
                new HttpClientThread(url,etName.getText().toString(),etAge.getText().toString());
            }
        });
    }
}
