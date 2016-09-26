package zhuoxin.com.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class NewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    List<String> stringList;

    public NewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> stringList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
