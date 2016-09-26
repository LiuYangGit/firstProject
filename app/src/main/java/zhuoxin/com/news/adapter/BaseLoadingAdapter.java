package zhuoxin.com.news.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import zhuoxin.com.news.R;

/**
 * Created by Administrator on 2016/9/17.
 */
public abstract class BaseLoadingAdapter<T> extends RecyclerView.Adapter {
    private List<T> myDatas;
    private Context context;
    protected LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private LoadingViewHolder loadingViewHolder;
    private boolean firstLoading = true;
    private boolean isLoading;
    //标识
    private static final int HOLDER_TYPE_NORMAL = 0;
    private static final int HOLDER_TYPE_LOADING = 1;
    private static final int HOLDER_TYPE_HEADER = 2;

    public interface LoadingLinener {
        void Loading();
    }

    private LoadingLinener loadingLinener;

    public void setLoadingListener(LoadingLinener loadingLinener) {
        this.loadingLinener = loadingLinener;
    }

    public LoadingLinener getLoadingLinener() {
        return loadingLinener;
    }

    public void setLoadingLinener(LoadingLinener loadingLinener) {
        this.loadingLinener = loadingLinener;
    }

    public BaseLoadingAdapter(Context context, List<T> myDatas, RecyclerView recyclerView) {
        super();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.myDatas = myDatas;
        this.recyclerView = recyclerView;
        //显示我们的加载
        notFilyLoding();
    }

    //一开始就显示我们的加载
    //外部第一次时我们给的sizi为0 所以执行不到就显示不出来。需要我们手动执行
    private void notFilyLoding() {
        if (myDatas.size() == 0) {
            myDatas.add(null);
            notifyItemInserted(myDatas.size() - 1);
            myDatas.remove(myDatas.size() - 1);
            notifyItemRemoved(myDatas.size() - 1);
        }
    }


    //数据添加成功
    public void setLodingComplete() {
        if (myDatas.size() > 0 && myDatas.get(myDatas.size() - 1) == null) {
            isLoading = false;
        }
    }

    //加载失败
    public void setLodingErrey() {
        if (loadingViewHolder != null) {
            //加载失败呈现的内容
            isLoading = false;
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("加载失败");
            //点击事件
            loadingViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loadingLinener != null) {//不为空就做加载 重新加载的操作
                        isLoading = true;
                        loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                        loadingViewHolder.textView.setText("正在加载请稍候");
                        loadingLinener.Loading();
                    }
                }
            });
        }
    }

    //没有更多的数据
    public void setLoadingNoMore() {
        isLoading = false;
        if (loadingViewHolder != null) {
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("没有更多信息");
        }
    }

    //判定是否滑到底
    private void setOnScrollListener() {
        if (this.recyclerView == null) {
            Log.i("11111", "没设置上");
            return;
        }
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //滑动状态更改
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            //滑动过程当中
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判定是否滑到最后一项
                if (!ViewCompat.canScrollHorizontally(recyclerView, 1)) {
                    if (!isLoading && firstLoading) {//不是第一次  也不是加载当中
                        notFilyLoding();
                        isLoading = true;
                        if (loadingViewHolder!=null){
                            loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    //获取每一项实现的风格
    //规定定义的标识
    @Override
    public int getItemViewType(int position) {
        T t = myDatas.get(position);
        if (t == null) {//没有数据还在加载过程当中
            return HOLDER_TYPE_LOADING;
        } else if (position == 0) {
            //头部信息
            return HOLDER_TYPE_HEADER;
        } else {
            return HOLDER_TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        //根据标识创建出不同的viewHolder
        switch (viewType) {
            case HOLDER_TYPE_HEADER:
                viewHolder = onCreateHeaderViewHolder(parent);
                break;
            case HOLDER_TYPE_LOADING:
                viewHolder = onCreateLoadingViewHolder(parent);
                break;
            case HOLDER_TYPE_NORMAL:
                viewHolder = onCreateNormalViewHolder(parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        //根据标识绑定上不同的数据
        switch (viewType) {
            case HOLDER_TYPE_HEADER:
                onCreateBindHeaderViewHolder(holder, position);
                break;
            case HOLDER_TYPE_LOADING:
                onCreateBindLoadingViewHolder(holder);
                break;
            case HOLDER_TYPE_NORMAL:
                onCreateBindNormalViewHolder(holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return myDatas.size();
    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ProgressBar progressBar;
        public RelativeLayout relativeLayout;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_recycle_loading_tv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_recycle_loading_pg);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_recycle_loading_layout);
        }
    }

    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent) {
        View itemView = layoutInflater.inflate(R.layout.item_news_recycle, parent, false);
        LoadingViewHolder loadingViewHolder = new LoadingViewHolder(itemView);
        return loadingViewHolder;
    }

    public void onCreateBindLoadingViewHolder(RecyclerView.ViewHolder viewHolder) {
        //TODO
    }

    //交给子类实现的两个方法，   父类至维护到LoadingViewHolder
    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup);

    public abstract void onCreateBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position);


    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup viewGroup);

    public abstract void onCreateBindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    //添加数据源
    public void AddAll(List<T> allDatas) {
        if (this.myDatas != null) {
            this.myDatas.clear();
        }
        this.myDatas.addAll(allDatas);
        notifyDataSetChanged();
    }

    //清除数据源
    public void ClearAll() {
        if (this.myDatas != null)
            this.myDatas.clear();
        notifyDataSetChanged();
    }

}
