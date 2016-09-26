package zhuoxin.com.news.acitivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
;


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import zhuoxin.com.news.R;
import zhuoxin.com.news.base.BaseActivity;
import zhuoxin.com.news.fragment.HotFragment;
import zhuoxin.com.news.fragment.NewFragment;
import zhuoxin.com.news.fragment.SearchFragment;


/**
 * Created by Administrator on 2016/9/1.
 */
public class FragmentActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton rb_new, rb_hot, rb_search;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment[] fragments = new Fragment[3];
    private NavigationView navigation_cehua; //侧滑
    private DrawerLayout drawerLayout;//整体放里面
    private Toolbar toolbar;


    //加载布局
    //   setActivity();
    //初始化
    //  initView();
    //后面执行的逻辑
    //   afterView();
    @Override

    public void setActivity() {
        setContentView(R.layout.activity_fragment_new);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initView() {
        rb_new = (RadioButton) findViewById(R.id.rb_new);
        rb_hot = (RadioButton) findViewById(R.id.rb_hot);
        rb_search = (RadioButton) findViewById(R.id.rb_search);
        rb_new.setOnClickListener(this);
        rb_hot.setOnClickListener(this);
        rb_search.setOnClickListener(this);
        choiceFragment(0);
        toolbarView();
        drawerView();
    }

    private void showShared() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    // 侧滑及相关的
    private void drawerView() {
        navigation_cehua = (NavigationView) findViewById(R.id.navigation_cehua);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //呈现Toolbar左上角按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 对Drawer监听 这个监听别人已经实现了 我们直接先使用 需要注意的是我们需要关联到我们的ToolBar
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //同步状态
        actionBarDrawerToggle.syncState();
        // 对我们的DrawerLayout设置上监听 关于滑动的动画 ActionBarDrawerToggle在做实现  我们不需要去管理
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        // 隐藏的View 呈现出来以后 我们是可能点击的 在这边想有相应的点击处理操作 那么直接想到监听者

        navigation_cehua.getHeaderView(0).findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
          //登录界面
            @Override
            public void onClick(View v) {
                View view = FragmentActivity.this.getLayoutInflater().inflate(R.layout.item_regiser, null);
                final EditText et_name, et_password;
                et_name = (EditText) findViewById(R.id.regiser_name);
                et_password = (EditText) findViewById(R.id.regiser_password);
                AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivity.this);
                builder.setView(view).setTitle("登录").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FragmentActivity.this, "确定啊", Toast.LENGTH_SHORT).show();
                  //      Toast.makeText(FragmentActivity.this, et_name.getText() + "" + et_password.getText() + "", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();


                Toast.makeText(FragmentActivity.this, "222222", Toast.LENGTH_SHORT).show();
            }
        });
        navigation_cehua.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_collect:
                        //  Toast.makeText(FragmentActivity.this, "收藏", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(FragmentActivity.this, CollectActivity.class);
                        Bundle bundle = new Bundle();

                        FragmentActivity.this.startActivity(intent);
                        break;
                    case R.id.drawer_about:
                        Toast.makeText(FragmentActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawer_setting:
                        Toast.makeText(FragmentActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
//                    case R.id.navigation_cehua:
//                        Toast.makeText(FragmentActivity.this, "登录", Toast.LENGTH_SHORT).show();
//                        break;
                }
                return true;

            }
        });
    }

    //创建菜单
    //对右上角的菜单有一个状态监听
    // true  代表需要创建  反之false不需要创建
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //解析布局文件  getMenuInflater这个方法
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        //  return super.onCreateOptionsMenu(menu);
        return true;
    }


    //Toolbar及相关的
    private void toolbarView() {
        //  action_share

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("新闻大师");
        //  toolbar.setNavigationIcon(R.drawable.btn_return);  //左上角图片
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);//核心方法   toolbar和Activitity关联起来

//菜单的监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_1:
                        Toast.makeText(FragmentActivity.this, "分享", Toast.LENGTH_SHORT).show();
                        showShared();
                        break;
                    case R.id.action_2:
                        showShared();
                        break;
                    case R.id.action_3:
                        showShared();
                        break;
                }

                return true;
            }
        });
        //下面设置返回按钮的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    // 隐藏掉所有的Fragment
    private void hideAllFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null)
                fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.commit();

    }

    // 切换Fragment
    private void choiceFragment(int index) {
        // 隐藏掉所有的Fragment
        hideAllFragment();
        //开启事物
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new NewFragment();

                    fragmentTransaction.add(R.id.frameLayout_temp, fragments[index]);
                    break;
                case 1:
                    fragments[index] = new HotFragment();

                    fragmentTransaction.add(R.id.frameLayout_temp, fragments[index]);
                    break;
                case 2:
                    fragments[index] = new SearchFragment();
                    fragmentTransaction.add(R.id.frameLayout_temp, fragments[index]);
                    break;
            }
        } else {
            fragmentTransaction.show(fragments[index]);
        }
        fragmentTransaction.commit();
    }


    @Override
    public void afterView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_new:

                choiceFragment(0);

                break;
            case R.id.rb_hot:

                choiceFragment(1);
                break;
            case R.id.rb_search:

                choiceFragment(2);
                break;
        }

    }


}
