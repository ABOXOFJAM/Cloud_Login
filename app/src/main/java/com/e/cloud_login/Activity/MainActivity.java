package com.e.cloud_login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.cloud_login.AccountLogin;
import com.e.cloud_login.R;

public class MainActivity extends AppCompatActivity {
    EditText met_username,met_password;
    Button mbtn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        met_username = findViewById(R.id.et_username);
        met_password = findViewById(R.id.et_password);
        mbtn_start =findViewById(R.id.btn_start);
        mbtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn_start();
            }
        });
    }
    public void login_btn_start(){
        String uname =met_username.getText().toString();
        String pword =met_password.getText().toString();
        Log.i("-----uame-----",uname);
        Log.i("-----pword-----",pword);
        AccountLogin alogin = new AccountLogin();
        try{
        alogin.accountLogin(uname,pword);
        Log.i(alogin.token,alogin.token+"————测试—————");
        if(alogin.status!=null&&alogin.token!=null&&alogin.user!=null){
            Toast.makeText(MainActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
            System.out.println("访问成功");
        }
        else    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        ;
    }
