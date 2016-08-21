package json.json;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangjiajing on 2016/8/20.
 */
public class JsonAdapter extends BaseAdapter{
    //数据
    private List<PersonInfo> list;
    private Context context;

    //创建Inflater对象 用于初始化布局
    private LayoutInflater layoutInflater;

    private Handler handler = new Handler();

    //创建多参构造方法
    public JsonAdapter(Context context,List<PersonInfo> list){
        this.context = context;
        this.list = list;
        //初始化
        layoutInflater = LayoutInflater.from(context);
    }

    //创建只有一个参数的构造方法
    public JsonAdapter(Context context){
        this.context = context;
        //初始化
        layoutInflater = LayoutInflater.from(context);
    }

    //创建setData方法
    public void setData(List<PersonInfo> personInfos){
        this.list = personInfos;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //创建viewHolder对象
        viewHolder viewHolder = null;
        //判断如果view为空的情况下
        if (view==null){
            //设置布局 item
            view = layoutInflater.inflate(R.layout.json_item,null);
            //将布局传入holder
            viewHolder = new viewHolder(view);
            //设置Tag为holder
            view.setTag(viewHolder);
        }else {
            //view不等于空 拿出Tag
            viewHolder = (JsonAdapter.viewHolder) view.getTag();
        }

        //拿到实体数据
        PersonInfo personInfo = list.get(i);
        //通过viewHolder设置界面信息
        viewHolder.tvName.setText(personInfo.getName());
        viewHolder.tvAge.setText(""+personInfo.getAge());

        List<SchoolInfo> schoolInfos = personInfo.getSchoolInfo();
        SchoolInfo schoolInfo1 = schoolInfos.get(0);
        SchoolInfo schoolInfo2 = schoolInfos.get(1);
        viewHolder.tvSchool1.setText(schoolInfo1.getSchool_name());
        viewHolder.tvSchool2.setText(schoolInfo2.getSchool_name());

        //设置图片
        new HttpImage(viewHolder.ivJsonImage,personInfo.getUrl(),handler).start();
        return null;
    }

    //创建Holder对象
    class viewHolder{
        private TextView tvName,tvAge,tvSchool1,tvSchool2;
        private ImageView ivJsonImage;

        //创建构造方法
        public viewHolder(View view){
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAge = (TextView) view.findViewById(R.id.tvAge);
            tvSchool1 = (TextView) view.findViewById(R.id.tvSchool1);
            tvSchool2 = (TextView) view.findViewById(R.id.tvSchool2);
            ivJsonImage = (ImageView) view.findViewById(R.id.ivJsonImage);
        }
    }
}
