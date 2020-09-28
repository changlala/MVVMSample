package com.chang.mvvmsample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chang.mvvmsample.R;
import com.chang.mvvmsample.model.entity.User;
import com.chang.mvvmsample.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainViewModel mViewModel;
    Button insertBtn;
    Button getBtn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertBtn = findViewById(R.id.insert);
        getBtn = findViewById(R.id.get);
        tv = findViewById(R.id.tv);

        //实例化viewmodel
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //设置数据变更时的callback
        mViewModel.userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //数据发生变化时将自动更新TextView
                StringBuilder sb = new StringBuilder();
                for(User u :users){
                    sb.append(" id").append(u.getId()).append(" name").append(u.getName()).append(" age").append(u.getAge()).append("\n");
                }

                tv.setText(sb.toString());
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User u = new User();
                u.setAge(111);
                u.setName("sss");
                mViewModel.inserUser(u, new MainViewModel.Callback() {
                    @Override
                    public void onInsertUserDone(final long id) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"新添加的user id "+id,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getUserList();
            }
        });
    }
}