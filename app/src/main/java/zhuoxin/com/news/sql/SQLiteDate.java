package zhuoxin.com.news.sql;

/**
 * Created by Administrator on 2016/9/21.
 */
public class SQLiteDate {
    String title;
    String url;

    public SQLiteDate(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SQLiteDate{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
