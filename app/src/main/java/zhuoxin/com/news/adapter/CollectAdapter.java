package zhuoxin.com.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.sql.SQLiteDate;


/**
 * Created by Administrator on 2016/9/21.
 */
public class CollectAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<SQLiteDate> dates;

    public CollectAdapter(Context context, List<SQLiteDate> dates) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dates = dates;
    }

    public interface Icon {
        void iconDelete(View view, int position);
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon icon;

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public SQLiteDate getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collect_text, null, false);
            holder = new MyViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_collect_tv);
            holder.imageView = (ImageView) convertView.findViewById(R.id._item_collect_icon);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.textView.setText(dates.get(position).getTitle());
        holder.imageView.setImageResource(R.drawable.icon_sdclean);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon.iconDelete(v, position);
            }
        });
        return convertView;
    }


    class MyViewHolder {
        public TextView textView;
        public ImageView imageView;
        public String url;
        public String title;
    }
}
