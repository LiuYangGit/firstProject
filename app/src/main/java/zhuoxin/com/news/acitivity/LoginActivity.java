package zhuoxin.com.news.acitivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import zhuoxin.com.news.R;

/**
 * Created by Administrator on 2016/9/25.
 */
public class LoginActivity extends AppCompatActivity {
    EditText name;
    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.item_regiser);
        name = (EditText) findViewById(R.id.regiser_name);
        password = (EditText) findViewById(R.id.regiser_password);

    }

//    public void login(View v) {
//        View view = getLayoutInflater().inflate(R.layout.item_regiser, null, false);
//
//
//        AlertDialog.Builder buider = new AlertDialog.Builder(this);
//        buider.setView(view).setTitle("登录").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(LoginActivity.this, name.getText() + "" + password.getText() + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//        buider.create().show();
//    }
}
