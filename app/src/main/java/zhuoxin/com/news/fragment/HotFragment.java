package zhuoxin.com.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import zhuoxin.com.news.R;
import zhuoxin.com.news.adapter.BaseLoadingAdapter;
import zhuoxin.com.news.adapter.NewsContentAdapter;
import zhuoxin.com.news.base.MyApplication;
import zhuoxin.com.news.bean.NewsInfo;
import zhuoxin.com.news.util.ConnectionURL;
import zhuoxin.com.news.util.FlowLayout;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HotFragment extends Fragment {

    private RecyclerView recyclerView;
    private View contentView;
    private RequestQueue requestQueue;
    private Gson gson;
    private List<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentListBeans;
    private BaseLoadingAdapter<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final int ADD_DATA_FLAG = 0;//上拉加载
    private final int UPADTE_DATA_FLAG = 1;//下拉刷新
    private int allPage;//所有页数
    private int nowPage = 1; //当前页数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.activity_hot_fragment, container, false);
        // 获取传入的参数
        requestQueue = MyApplication.getInstance().getRequestQueue();
        gson = MyApplication.getInstance().getGson();
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.hot_rv);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hot_srl);
        //下拉监听
        //下拉刷新的色彩配置。最多可以设置四种颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.cardview_dark_background, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//下拉刷新回调  重新加载数据
                getBaiduNewInfo(UPADTE_DATA_FLAG, 1);
            }
        });

        contentListBeans = new ArrayList<>();
        //adapter = new BaseLoadingAdapter<>(getActivity(), contentListBeans, recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsContentAdapter(getActivity(), contentListBeans, recyclerView);
        recyclerView.setAdapter(adapter);
        getBaiduNewInfo(ADD_DATA_FLAG, nowPage);//添加一页数据
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 获取数据
     */
    private void getBaiduNewInfo(final int getDataType, int pager) {
        StringRequest stringRequest = new StringRequest(ConnectionURL.findNewsByName("互联网", nowPage), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                NewsInfo newsInfo = gson.fromJson(response, NewsInfo.class);
                contentListBeans = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
                onGetDataSuccess(getDataType);

                Log.i("volley_response", newsInfo.toString());
//                adapter = new NewsContentAdapter(getActivity(), contentListBeans, recyclerView);
//                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("volley_err ", volleyError.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", ConnectionURL.BAIDU_KEY);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void onGetDataSuccess(int getDataType) {
        if (contentListBeans == null || contentListBeans.size() == 0)
            return;
        Iterator<NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean> iterator = contentListBeans.iterator();
        NewsInfo.ShowapiResBodyBean.PagebeanBean.ContentlistBean temp = null;
        // 遍历数据源 并且把所有的没有图片地址的信息删除掉
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp == null || temp.getImageurls().size() == 0 || temp.getImageurls().get(0).getUrl() == null)
                iterator.remove();
        }
//        for (ContentlistBean bean : contentListBeans) {
//            if (bean.getImageurls().size() == 0)
//                Log.i("ImageUrlTest", newName + "不含图片");
//            else {
//                Log.i("ImageUrlTest", newName + "含有");
//            }
//        }
        switch (getDataType) {
            case ADD_DATA_FLAG:
                if (contentListBeans != null) {
                    adapter.AddAll(contentListBeans);
                    nowPage++;//当前页数需要刷新
                }
                break;
            case UPADTE_DATA_FLAG:
                if (contentListBeans != null) {
                    adapter.ClearAll();
                    adapter.AddAll(contentListBeans);
                }
                swipeRefreshLayout.setRefreshing(false);
                nowPage = 2;
                break;
        }

    }


//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_hot_fragment, container, false);
//    }
}
