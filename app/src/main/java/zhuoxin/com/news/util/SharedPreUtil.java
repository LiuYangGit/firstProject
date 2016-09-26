package zhuoxin.com.news.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/8/31.
 */
//SharedPre 工具类
public class SharedPreUtil {
    private Context context;
    private String lead_sharedP = "lead_data";
    private String tab_data = "tab_data";

    public SharedPreUtil(Context context) {
        this.context = context;
    }

    /**
     * 保存引导信息
     */
    public void saveLeadDataToShared(String versionName) {
        //获取到SharedPreferences的对象
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(lead_sharedP, Context.MODE_PRIVATE);
        //写的操作 获取到Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("versionName", versionName);
        editor.commit();//提交

    }

    /**
     * return versionName
     */
    public String getLeadDataFromShared() {
        return context.getSharedPreferences(lead_sharedP, 0).getString("versionName", "");
    }

    public List<String> getNewFragmentList() {
        Set<String> tabs = getNewFromFragment();
        List<String> taList = new ArrayList<>();
        for (String tab : tabs) {
            taList.add(tab);
        }
        return taList;
    }


    public Set<String> getNewFromFragment() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(tab_data, 0);
        Set<String> data = sharedPreferences.getStringSet("add", null);
        if (data == null) {
            data = new TreeSet<>();
            data.add("军事");
            data.add("娱乐");
            data.add("游戏");
            data.add("科技");
            data.add("体育");
            data.add("女人");
        }
        return data;
    }


    public void seteNewToFragment(Set<String> set) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(tab_data, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putStringSet("add", set);
        editor.commit();
    }

}
