package zhuoxin.com.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import zhuoxin.com.news.R;
import zhuoxin.com.news.acitivity.WebViewActivity;
import zhuoxin.com.news.bean.NewsInfo;
import zhuoxin.com.news.bean.NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsContentAdapter extends BaseLoadingAdapter<ContentlistBean> {
    private Context context;
    private List<ContentlistBean> myDatas;
    private DisplayImageOptions options;
    ///


    public NewsContentAdapter(Context context, List myDatas, RecyclerView recyclerView) {
        super(context, myDatas, recyclerView);
        this.myDatas = myDatas;
        this.context = context;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)
                .showImageOnFail(R.drawable.img_news_lodinglose)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View itemView = super.layoutInflater.inflate(R.layout.item_news_recycle_header, viewGroup, false);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(itemView);
        return headerViewHolder;
    }

    @Override
    public void onCreateBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
        headerViewHolder.item_news_tv_head_text.setText(myDatas.get(position).getTitle());
        //
        headerViewHolder.url = myDatas.get(position).getLink();
        headerViewHolder.title = myDatas.get(position).getTitle();
        List<ContentlistBean.ImageEntity> entityList = myDatas.get(position).getImageurls();
        if (entityList.size() != 0)
            ImageLoader.getInstance().displayImage(entityList.get(0).getUrl(), headerViewHolder.item_news_iv_head_img, options);
    }

    //
    @Override
    public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup viewGroup) {
        View normalView = super.layoutInflater.inflate(R.layout.item_news_recycle_normal, viewGroup, false);
        NormalViewHolder normalViewHolder = new NormalViewHolder(normalView);
        return normalViewHolder;
    }

    @Override
    public void onCreateBindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
        ContentlistBean contentlistBean = myDatas.get(position);
        normalViewHolder.item_recycle_content.setText(contentlistBean.getDesc());
        normalViewHolder.item_recycle_time.setText(contentlistBean.getPubDate());
        normalViewHolder.item_recycle_title.setText(contentlistBean.getTitle());
        normalViewHolder.url = contentlistBean.getLink();//地址
        normalViewHolder.title = contentlistBean.getTitle();

        List<ContentlistBean.ImageEntity> entityList = myDatas.get(position).getImageurls();
        if (entityList.size() != 0)
            ImageLoader.getInstance().displayImage(entityList.get(0).getUrl(), normalViewHolder.item_recycle_img, options);

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView item_news_tv_head_text;
        public ImageView item_news_iv_head_img;
        public String url;
        public String title;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            item_news_tv_head_text = (TextView) itemView.findViewById(R.id.item_news_tv_head_text);
            item_news_iv_head_img = (ImageView) itemView.findViewById(R.id.item_news_iv_head_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_recycle_img;
        public TextView item_recycle_title;
        public TextView item_recycle_time;
        public TextView item_recycle_content;

        public String url;
        public String title;

        public NormalViewHolder(View itemView) {
            super(itemView);
            item_recycle_img = (ImageView) itemView.findViewById(R.id.item_recycle_img);
            item_recycle_time = (TextView) itemView.findViewById(R.id.item_recycle_time);
            item_recycle_title = (TextView) itemView.findViewById(R.id.item_recycle_title);
            item_recycle_content = (TextView) itemView.findViewById(R.id.item_recycle_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putString("title", title);
                    in.putExtras(bundle);
                    Log.i("3222", bundle.toString());
                    context.startActivity(in);
                    Log.i("222", "跳转" + in);
                }
            });

        }
    }
}
