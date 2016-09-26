package zhuoxin.com.news.acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;


import java.util.Set;

import zhuoxin.com.news.R;
import zhuoxin.com.news.callback.FlowLayoutCallback;
import zhuoxin.com.news.util.FlowLayout;
import zhuoxin.com.news.util.SharedPreUtil;

public class AddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_add_tag;//listView控件
    private FlowLayout flowLayout;//工具包
    private SharedPreUtil sharedPreUtil;
    Set<String> stringSet;
    private String[] str = {"房产", "军事", "娱乐", "游戏", "互联网", "财经",  "教育", "体育", "汽车", "图片"};

    private boolean needUpdate = false;// 代表是否需要刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        lv_add_tag = (ListView) findViewById(R.id.lv_add_tag);
        flowLayout = (FlowLayout) findViewById(R.id.frameLayout_add_tag);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("needUpdate", needUpdate);
                setResult(101, intent);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
        lv_add_tag.setAdapter(adapter);
        sharedPreUtil = new SharedPreUtil(this);

        stringSet = sharedPreUtil.getNewFromFragment();
        // 给FlowLayout 设置上数据
        flowLayout.getSetData(stringSet);

        flowLayout.setCallback(new FlowLayoutCallback() {
            @Override
            public void afterOnChildClick() {

                needUpdate = true;
            }
        });

        lv_add_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (stringSet.add(str[position])) {//添加成功
                    flowLayout.getSetData(stringSet);
                    needUpdate = true;
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在界面销毁的时候 保存数据到SharedPref
        sharedPreUtil.seteNewToFragment(stringSet);
    }
}
