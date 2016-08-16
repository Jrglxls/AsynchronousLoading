package systemservice.systemservice;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 方法一：通过id获取layout
         */
//        setContentView(R.layout.activity_main);
        /**
         * 方法二：使用布局填充器
         */
        //布局填充器
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //通过layoutInflater进行布局的绑定 返回view对象
        View view = layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(view);
    }

    public void doClick(View view) {
        switch (view.getId()){
            case R.id.btnNetwork:
                /**
                 * 网络连接
                 */
                if (isNetWorkConnected(this)){
                    Toast.makeText(this,"网络已连接",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"网络未连接",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnWifi:
                /**
                 * wifi管理
                 */
                //获取WiFi连接，返回WiFiMnager
                WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
                //判断WiFi可控制
                if (wifiManager.isWifiEnabled()){
                    //设置WiFi关闭
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(this,"wifi已关闭",Toast.LENGTH_SHORT).show();
                }else {
                    //设置WiFi打开
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(this,"WiFi已连接",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnVoice:
                /**
                 * 得到当前音量
                 */
                //音量管理器获取音量服务
                AudioManager audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
                //系统最大音量
                int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                //系统当前音量
                int current = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                Toast.makeText(this,"系统的最大音量为："+max+"系统的当前音量为："+current,Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPackageName:
                /**
                 * 获取当前应用运行状态
                 */
                //ActivityManager对象获取当前activity服务
                ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
                //获取当前应用name
                String packageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
                Toast.makeText(this,"当前运行的包名："+packageName,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 网络连接
     * @param context
     * @return
     */
    public boolean isNetWorkConnected(Context context){
        //如果上下文对象存在
        if (context!=null){
            //ConnectivityManager对象获取网络连接
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            //获取网络信息
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            //如果网络信息不为空
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
