package com.bcp.yibujiazai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private boolean mFirstIn;

    //滑动优化
    private int mStart, mEnd;
    public static String[] URLS;//当前获取到的所有图片的地址

    //Lru Cache
    private ImageLoader mImageLoader;

    public NewsAdapter(Context context, List<NewsBean> data, ListView listView) {
        mList = data;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);

        //滑动优化
        URLS=new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            URLS[i] = data.get(i).newsIconUrl;
        }
        mFirstIn=true;
        //注册滑动事件
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        //方法一：
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,mList.get(position).newsIconUrl);

        //方法二：
//        new ImageLoader().showImageByAsyncTask(viewHolder.ivIcon,url);

        //方法三：
        mImageLoader.showImageByAsyncTask(viewHolder.ivIcon, url);

        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);

        return convertView;
    }


    //通过监听ListView的滑动操作进行优化
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //是否停止滚动状态
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            mImageLoader.loadImages(mStart, mEnd);
        } else {
            //停止任务
            mImageLoader.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //判断到第一次加载界面时，预先加载一屏幕图像
        if (mFirstIn&&visibleItemCount>0){
            mImageLoader.loadImages(mStart,mEnd);
            mFirstIn=false;
        }
    }

    class ViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }
}
