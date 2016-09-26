package zhuoxin.com.news.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/20.
 */
//数据库的创建
public class CollectSQLite extends SQLiteOpenHelper {

    // String name, 文件名
    //int version  版本号
    public CollectSQLite(Context context) {
        super(context, "Myname", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table AAA(title text,url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
