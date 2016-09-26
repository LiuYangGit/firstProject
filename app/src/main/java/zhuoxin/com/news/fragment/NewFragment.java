package zhuoxin.com.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.acitivity.AddActivity;
import zhuoxin.com.news.adapter.NewPagerAdapter;
import zhuoxin.com.news.util.SharedPreUtil;

/**
 * Created by Administrator on 2016/9/1.
 */
public class NewFragment extends Fragment {
    TabLayout tableLayout;
    ViewPager viewPager;
    NewPagerAdapter newPagerAdapter;
    List<Fragment> fragmentList;
    List<String> stringList;
    private SharedPreUtil sharedPreUtil;
    private ImageView iv_new_icon;//右边添加标签按钮

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreUtil = new SharedPreUtil(getActivity());
        View view = inflater.inflate(R.layout.activity_new_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableLayout = (TabLayout) view.findViewById(R.id.new_tabLayout);

        viewPager = (ViewPager) view.findViewById(R.id.vp_new);


        //添加标签按钮
        iv_new_icon = (ImageView) view.findViewById(R.id.iv_new_icon);
        iv_new_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
              //  Toast.makeText(getActivity(), "+++++++点击", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, 100);
            }
        });
        ///22222222222222222222222222222222222222222222222222222222
        initviewFragment();
        tableLayout.setupWithViewPager(viewPager);
    }

    private void initviewFragment() {
        fragmentList = new ArrayList<>();
        if (stringList != null)
            stringList.clear();
        fragmentList.clear();
        stringList = sharedPreUtil.getNewFragmentList();

        for (int i = 0; i < stringList.size(); i++) {
            Log.i("NewFragment", stringList.get(i));
            //将页卡消息传递到下一个fragment来
            BaiDuNewFragment temp = new BaiDuNewFragment();
            Bundle tab = new Bundle();
            tab.putString("newName", stringList.get(i));
            temp.setArguments(tab);
            fragmentList.add(temp);//百度
        }
        newPagerAdapter = new NewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, stringList);
        viewPager.setAdapter(newPagerAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101 && data.getBooleanExtra("needUpdate", false)) {

//
            initviewFragment();
            tableLayout.setupWithViewPager(viewPager);
        }

    }
}
