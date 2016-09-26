package zhuoxin.com.news.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by Administrator on 2016/9/17.
 */
public class MyApplication extends Application {
    private RequestQueue requestQueue;
    private Gson gson;

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {

        ApiStoreSDK.init(this, "2bf845320bc3e1b4844af875faaa2125");
        super.onCreate();
        myApplication = this;
        //请求队列的实例化
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //gson对象
        gson = new Gson();
        //初始化ImageLoader配置信息
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(400, 800)//最大的图片宽度和高度
                .threadPoolSize(5) //关于线程池的
                .threadPriority(Thread.NORM_PRIORITY - 1)  //线程优先级
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)      //缓存的大小
                .memoryCacheSizePercentage(12)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//默认
                .imageDownloader(new BaseImageDownloader(this))//默认
                .imageDecoder(new BaseImageDecoder(true))//默认
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())//默认
                .writeDebugLogs()//打印日志信息
                .build();
        //将配置信息给我们ImageLoader对象
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    //应用停止
    @Override
    public void onTerminate() {
        super.onTerminate();
        requestQueue.cancelAll(this);//停止所有的请求
    }
}
