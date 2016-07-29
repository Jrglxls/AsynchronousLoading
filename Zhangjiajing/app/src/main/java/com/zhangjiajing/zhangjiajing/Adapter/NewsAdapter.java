package com.zhangjiajing.zhangjiajing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangjiajing.zhangjiajing.Activity.ImageLoader;
import com.zhangjiajing.zhangjiajing.Activity.NewsBean;
import com.zhangjiajing.zhangjiajing.R;

import java.util.List;

/**
 * Created by zhangjiajing on 2016/7/28.
 */
public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> newsBean;//需要映射的数组
    private LayoutInflater layoutInflater;//添加layout布局
    private ImageLoader mImageLoader;

    //构造方法，专递参数
    public NewsAdapter(Context context,List<NewsBean> data){
        newsBean = data;
        layoutInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
    }

    @Override
    public int getCount() {
        return newsBean.size();
    }

    @Override
    public Object getItem(int position) {
        return newsBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.listview_item,null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = newsBean.get(position).getNewsIconUrl();
        viewHolder.ivIcon.setTag(url);//增加身份标识，将url作为身份验证码
        //使用多线程加载图片 传递控件和url 在方法中设置图片
//        mImageLoader.showImageByThread(viewHolder.ivIcon, url);
        //使用AsyncTask加载图片
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);
        viewHolder.tvTitle.setText(newsBean.get(position).getNewsTitle());
        viewHolder.tvContent.setText(newsBean.get(position).getNewsContent());
        return convertView;
    }

    class ViewHolder{
        private TextView tvTitle,tvContent;
        private ImageView ivIcon;
    }
}
