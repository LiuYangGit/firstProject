package zhuoxin.com.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.acitivity.WebViewActivity;
import zhuoxin.com.news.sql.CollectData;
import zhuoxin.com.news.sql.SQLiteDate;


/**
 * Created by Administrator on 2016/9/22.
 */
//搜索
public class SearchAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<SQLiteDate> list;

    public void setList(List<SQLiteDate> list) {
        this.list = list;
    }

    public SearchAdapter(Context context, List<SQLiteDate> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SQLiteDate getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolders viewHolders;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_text, null, false);
            viewHolders = new ViewHolders();
            viewHolders.textView = (TextView) convertView.findViewById(R.id.item_searche_text);
            convertView.setTag(viewHolders);
        } else {
            viewHolders = (ViewHolders) convertView.getTag();
        }

        Intent intent = new Intent(context, WebViewActivity.class);
        viewHolders.textView.setText(list.get(position).getTitle());
        return convertView;
    }

    class ViewHolders {
        TextView textView;
        String url;
        String title;
    }
}
