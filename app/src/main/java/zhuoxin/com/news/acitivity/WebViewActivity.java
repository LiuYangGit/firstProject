package zhuoxin.com.news.acitivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import zhuoxin.com.news.R;
import zhuoxin.com.news.base.BaseActivity;
import zhuoxin.com.news.sql.CollectData;
import zhuoxin.com.news.sql.CollectSQLite;
import zhuoxin.com.news.sql.SQLiteDate;

/**
 * Created by Administrator on 2016/9/19.
 */
public class WebViewActivity extends BaseActivity {
    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    Context context;
    String url = null;
    String title = null;
    CollectData collectData;

//    public WebViewActivity(Context context) {
//        this.context = context;
//    }

    @Override
    public void setActivity() {
        setContentView(R.layout.item_webview);
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.item_toolbar);
        webView = (WebView) findViewById(R.id.item_news_wv);
        progressBar = (ProgressBar) findViewById(R.id.item_news_pb);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.item_news_fab);
        imageView = (ImageView) findViewById(R.id.item_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onChicl123", "finish");
                finish();
            }
        });
        toolbar.setTitle("");
//        toolbar.setNavigationIcon(R.drawable.btn_homeasup_default);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("onChicl123", "finish");
//                finish();
//            }
//        });
        setSupportActionBar(toolbar);
        String url = getIntent().getExtras().getString("url");
        //设置本身浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        //设置进度条监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i("view", newProgress + "");
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.loadUrl(url);
        Log.i("url222", url);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDate sqLiteDate;
                Log.i("111111", "走到了");
                collectData = new CollectData(WebViewActivity.this);
                if (!collectData.search(getIntent().getExtras().getString("title"))) {
                    sqLiteDate = new SQLiteDate(getIntent().getExtras().getString("title"), getIntent().getExtras().getString("url"));
                    collectData.AddMsg(sqLiteDate);
                    // 问题：   TODO 没收藏  执行收藏过了。 有   也执行收藏过了。
                        Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    //
                     Toast.makeText(WebViewActivity.this, "已经收藏了", Toast.LENGTH_SHORT).show();
                    Log.i("222", "11111");
                }
//                collectData.search("title");
//                sqLiteDate = new SQLiteDate(getIntent().getExtras().getString("title"), getIntent().getExtras().getString("url"));
//                collectData.AddMsg(sqLiteDate);
                //     Log.i("111111", sqLiteDate.toString());

            }
        });


    }

    @Override
    public void afterView() {
    }


}