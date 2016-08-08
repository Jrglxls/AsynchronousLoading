package com.sharedpreferences.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText etUserName,etPassword;
    private CheckBox cbSave;
    private Button btnLogin,btnCancle;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * SharedPreferences基础知识
         */
//        //方法一 定义并得到对象
////        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        //方法二 参数 文件名 文件权限 只能由当前程序读取
//        SharedPreferences sharedPreferences = getSharedPreferences("zjjPref",MODE_PRIVATE);
//        //通过Editor得到编辑器对象
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        //通过editor存值
//        editor.putString("name","zhangjiajing");
//        editor.putInt("age", 22);
//        //当前系统的时间
//        editor.putLong("time",System.currentTimeMillis());
//        editor.putBoolean("defaule", true);
//        //一定要提交，否则操作无效
//        editor.commit();
//        //移除
//        editor.remove("default");
//        editor.commit();

        /**
         * 例子
         */
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        cbSave = (CheckBox) findViewById(R.id.cbSave);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        editor =sharedPreferences.edit();
        //如果用户名已保存，从SharedPreferences读取
        String name = sharedPreferences.getString("userName","");
            //如果SharedPreferences有存储值，这设置值
        if (name.equals("")){
            cbSave.setChecked(false);
        }else {
            cbSave.setChecked(true);
            etUserName.setText(name);
        }
    }

    /**
     * 为按钮设置点击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                //获取用户名和密码
                String name = etUserName.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                //用户名和密码正确
                if ("zhangjiajing".equals(name)&&"111111".equals(pass)){
                    //保存用户名选择框被选择
                    if (cbSave.isChecked()){
                        //保存用户名并登录
                        editor.putString("userName", name);
                        editor.commit();
                    }else {
                        //选择框未被选择，移除已保存的用户名
                        editor.remove("userName");
                        editor.commit();
                    }
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancle:
                break;
            default:
                break;
        }

    }
}
