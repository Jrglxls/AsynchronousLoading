package httpdownload.httpdownload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

/**
 * Created by zhangjiajing on 2016/8/26.
 *
 */
public class Upload extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);
    }

    public void doclick(View view) {
        //获取SD卡路径
        File file = Environment.getExternalStorageDirectory();
        File fileAbs = new File(file,"something");
        String url = "something";
        //获取绝对路径
        String fileName = fileAbs.getAbsolutePath();

        UploadThread uploadThread = new UploadThread(fileName,url);
        uploadThread.start();
    }
}
