package zhuoxin.com.news.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class AnimPagerAdapter extends PagerAdapter {
    public List<View> views;
    public Context context;

    //初始化拿到数据
    public  AnimPagerAdapter(List<View> views) {

        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);

        return views.get(position);
    }


}
