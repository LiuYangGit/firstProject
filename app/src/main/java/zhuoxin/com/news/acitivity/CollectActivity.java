package zhuoxin.com.news.acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.adapter.CollectAdapter;
import zhuoxin.com.news.base.BaseActivity;
import zhuoxin.com.news.sql.CollectData;
import zhuoxin.com.news.sql.SQLiteDate;

//收藏类
public class CollectActivity extends BaseActivity {
    Toolbar toolbar;
    ListView listView;
    CollectData collectData;
    CollectAdapter adapter;
    List<SQLiteDate> list;

    @Override
    public void setActivity() {
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.collect_tb);
        listView = (ListView) findViewById(R.id.collect_lv);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        collectData = new CollectData(CollectActivity.this);
        list = collectData.SearchAll();
        adapter = new CollectAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onClick", "执行");
                Intent intent = new Intent(CollectActivity.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                String url = list.get(position).getUrl();
                String title = list.get(position).getTitle();
                bundle.putString("url", url);
                bundle.putString("title", title);
                intent.putExtras(bundle);
                CollectActivity.this.startActivity(intent);
            }
        });
        adapter.setIcon(new CollectAdapter.Icon() {
            @Override
            public void iconDelete(View view, int position) {
                String title = list.get(position).getTitle();
                collectData.Delete(title);
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void afterView() {

    }
}
