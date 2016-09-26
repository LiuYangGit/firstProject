package zhuoxin.com.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.news.R;

import zhuoxin.com.news.acitivity.CollectActivity;
import zhuoxin.com.news.acitivity.WebViewActivity;
import zhuoxin.com.news.adapter.SearchAdapter;
import zhuoxin.com.news.sql.CollectData;
import zhuoxin.com.news.sql.SQLiteDate;


/**
 * Created by Administrator on 2016/9/1.
 */
public class SearchFragment extends Fragment {
    private EditText editText;
    private TextView textView;
    private ListView listView;
    List<SQLiteDate> list;
    SearchAdapter searchAdapter;
    CollectData collectData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String text = (String) msg.obj;
                    list = collectData.Find(text);
                    if (list != null) {
                        searchAdapter = new SearchAdapter(getActivity(), list);
                        listView.setAdapter(searchAdapter);
                        searchAdapter.setList(list);
                        searchAdapter.notifyDataSetChanged();
                    }

                    if (list.size() > 0) {

//                        textView.setText(list.toString());
                    } else {
                        textView.setText("没有找到");
                    }
                    break;
                case 1:
                    textView.setText("");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        collectData = new CollectData(getActivity());
        return inflater.inflate(R.layout.activity_search_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        editText = (EditText) view.findViewById(R.id.et_search);
        textView = (TextView) view.findViewById(R.id.tv_search_fragment);
        listView = (ListView) view.findViewById(R.id.search_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                String url = list.get(position).getUrl();
                Log.i("2222", url.toString());
                bundle.putString("title",list.get(position).getTitle());
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //TODO
        editText.addTextChangedListener(new TextWatcher() {
            //文本使用前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //文本使用中
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Message message = new Message();
                if (s.length() > 0) {
                    String str = s.toString();//转String
                    message.obj = str;//定义发送体
                    message.what = 0;//定义标示
                    handler.sendMessage(message);//发送消息队列
                } else {
                    message.what = 1;//定义标示
                    handler.sendMessage(message);
                    System.out.println("没有数据");
                }
            }

            //文本使用后
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

}
