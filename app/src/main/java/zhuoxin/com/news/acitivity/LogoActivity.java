package zhuoxin.com.news.acitivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import zhuoxin.com.news.R;
import zhuoxin.com.news.base.BaseActivity;
import zhuoxin.com.news.util.SharedPreUtil;

/**
 * Created by Administrator on 2016/8/31.
 */
public class LogoActivity extends BaseActivity implements Animation.AnimationListener {
    private ImageView imageView;
    private SharedPreUtil sharedPreUtil;


    @Override  //加载布局
    public void setActivity() {
        sharedPreUtil = new SharedPreUtil(this);
        String versionName = sharedPreUtil.getLeadDataFromShared();
        if (versionName.equals(null) || !versionName.equals(AppVersionName())) {
            // TODO 跳转LeadActivity
            StartActivity(LeadActivity.class);

            finish();
        }
        setContentView(R.layout.activity_logo);
    }

    @Override  //初始化数据
    public void initView() {
        imageView = (ImageView) findViewById(R.id.iv_loge_icon);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        imageView.startAnimation(animation);
        animation.setAnimationListener(this);

    }

    @Override   //后面的逻辑
    public void afterView() {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new SharedPreUtil(LogoActivity.this).saveLeadDataToShared(null);
        StartActivity(FragmentActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
