package zhuoxin.com.news.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zhuoxin.com.news.bean.NewsInfo;
import zhuoxin.com.news.sql.CollectSQLite;

/**
 * Created by Administrator on 2016/9/20.
 */
public class CollectData {
    SQLiteDatabase db;
    Context context;


    public CollectData(Context context) {
        this.context = context;
        CollectSQLite collectSQLite = new CollectSQLite(context);
        db = collectSQLite.getReadableDatabase();
    }


    //添加操作
    public void AddMsg(SQLiteDate sqLiteDate) {
        if (!search(sqLiteDate.getTitle())) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", sqLiteDate.getTitle());
            contentValues.put("url", sqLiteDate.getUrl());
            db.insert("AAA", null, contentValues);
        }

    }

    //查询有没有同一个数据
    public boolean search(String title) {
        Cursor cursor = db.rawQuery("select * from AAA where title = ?", new String[]{title});
        Log.i("ly","cursor:"+cursor.getCount());
        if (cursor.moveToNext())
            return true;
        else {
            return false;
        }


    }

    //模糊查询操作
    public List<SQLiteDate> Find(String findTitle) {
        List<SQLiteDate> list = new ArrayList<>();
        SQLiteDate sqLiteDate = null;
        Cursor cursor = db.rawQuery("select * from AAA where title like ?", new String[]{'%' + findTitle + '%'});
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            sqLiteDate = new SQLiteDate(title, url);
            list.add(sqLiteDate);

        }
        return list;
    }


    //查询所有
    public List<SQLiteDate> SearchAll() {
        List<SQLiteDate> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from AAA", null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            SQLiteDate sqLiteDate = new SQLiteDate(title, url);
            list.add(sqLiteDate);
        }
        return list;
    }

    //指定删除
    public void Delete(String title) {
        CollectSQLite collectSQLite = new CollectSQLite(context);
        db = collectSQLite.getWritableDatabase();
        db.delete("AAA", "title=?", new String[]{title});
        db.close();
    }
}
