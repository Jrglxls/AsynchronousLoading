package files.files;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText etContent;
    private Button btnWrite;
    private TextView tvContentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 文件基础知识
         */
        //创建test文件
        @SuppressLint("SdCardPath") File file = new File("/mnt/sdcard/test");
        //判断文件是否存在
        if (!file.exists()){
            try {
                //文件不存在则创建
                file.createNewFile();
                Log.d("zjj", "file" + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "文件已经存在", Toast.LENGTH_SHORT).show();
        }
        //删除文件
        file.delete();

        //当前应用程序默认的数据存储目录
        File file1 = this.getFilesDir();
        Log.d("zjj","file1"+file1.toString());

        //当前应用程序默认的缓存文件的存放位置
        //把一些不是非常重要的文件放在此处使用
        //如果手机内存不足的时候，系统会自动去删除APP的cache目录的数据
        File file2 = this.getCacheDir();
        Log.d("zjj", "file2"+file2.toString());

        //指定权限去创建文件夹
        File file3 = this.getDir("zjj", MODE_PRIVATE);
        Log.d("zjj","file3"+file3.toString());

        //可以得到外部的存储位置，该位置的数据跟内置的使用是一样的
        //如果app卸载了，这里面的数据也会自动清除掉
        File file4 = this.getExternalCacheDir();
        Log.d("zjj","file4"+file4.toString());
        //如果开发者不遵循这样的规则，不把数据放入
        //  data/data/<包名>
        //  /mnt/sdcard/Android/data/<包名>
        //卸载之后数据将不会自动清除掉，将会造成所谓的数据垃圾

        /**
         * 小例子
         */
        etContent = (EditText) findViewById(R.id.etContent);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        tvContentValue = (TextView) findViewById(R.id.tvContentValue);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //把EditText写入文件
            writeFiles(etContent.getText().toString());
            //把文件读出
            tvContentValue.setText(readFiles());
            }
        });

    }

    //保存文件内容
    public void  writeFiles(String content){
        try {
            //使用FileOutputStream创建对象
            FileOutputStream fileOutputStream = openFileOutput("Jessica.txt",MODE_WORLD_READABLE+MODE_WORLD_WRITEABLE);
            //写文件
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件内容
    public String readFiles(){
        //定义字符串 作为写出去的数据
        String content = null;
        try {
            //使用FileInputStream指定读取的路径
            FileInputStream fileInputStream = openFileInput("Jessica.txt");
            //每次读取一个字节，同时转成array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //读1024个字节
            byte[] buffer = new byte[1024];
            //定义length长度
            int length = 0;
            //如果还有值
            while ((length = fileInputStream.read(buffer)) != -1){
                //把数据写出去 每次写一个buffer 从0开始写出去
                byteArrayOutputStream.write(buffer,0,length);
            }
            //赋值content
            content = byteArrayOutputStream.toString();
            //关闭
            fileInputStream.close();
            byteArrayOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
