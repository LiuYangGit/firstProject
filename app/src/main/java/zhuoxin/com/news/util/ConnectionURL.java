package zhuoxin.com.news.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/9/17.
 */ //请求URL的工具类
public class ConnectionURL {
    //百度API地址
    public static final String BAIDU_URL = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static final String BAIDU_KEY = "609771ed11b10eac40e84c0f3cbc1836";

    //拼接URL方法
    public static String findNewsByName(String newName, int page) {
        return BAIDU_URL + "?channelName=" + changeToUTF(newName+"焦点") + "&page=" + page;
    }

    //转换
    private static String changeToUTF(String newName) {
        try {
            newName = URLEncoder.encode(newName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newName;
    }


}
