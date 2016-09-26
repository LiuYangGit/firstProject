package zhuoxin.com.news.acitivity;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.adapter.AnimPagerAdapter;
import zhuoxin.com.news.base.BaseActivity;
import zhuoxin.com.news.util.SharedPreUtil;

/**
 * Created by Administrator on 2016/8/31.
 */
public class LeadActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    Button button;
    private AnimPagerAdapter adapter;
    List<View> viewList;
    int[] data = {R.drawable.lead_1, R.drawable.lead_2, R.drawable.lead_3};//图片资源


    @Override  //加载布局
    public void setActivity() {
        setContentView(R.layout.activity_anims);
        viewList = new ArrayList<>();
        View pager = null;
        for (int i = 0; i < 3; i++) {
            pager = LayoutInflater.from(this).inflate(R.layout.activity_anim_three, null);//解析
            pager.findViewById(R.id.iv_anim_two).setBackgroundResource(data[i]);
            if (i == 2) {
                button = (Button) pager.findViewById(R.id.bttn_anim_start);
                button.setOnClickListener(this);

            }
            viewList.add(pager);
        }

    }

    @Override  //初始化
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.anim_viewPager);
        Log.i("abc", viewPager.toString());

        adapter = new AnimPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        //    viewPager.addOnPageChangeListener(this);

    }

    @Override   //后面逻辑
    public void afterView() {

    }

    @Override
    // TODO 业务跳转 保存SharedPre
    public void onClick(View v) {
        new SharedPreUtil(this).saveLeadDataToShared(AppVersionName());
        StartActivity(LogoActivity.class);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // 根据position得不同 决定btn的可视化
        if (position == 2)
            button.setVisibility(View.VISIBLE);
        else
            button.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
