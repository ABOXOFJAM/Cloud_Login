package com.e.cloud_login.Login_Funcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.cloud_login.Data.JSON.RegisterJson;
import com.e.cloud_login.R;
import com.e.cloud_login.WidgetSetting;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText met_username_register,met_password_register,met_email_register,met_password_check;
    Button mbtn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        met_password_check=findViewById(R.id.et_pasword2_register);
        met_email_register=findViewById(R.id.et_email_register);
        met_password_register=findViewById(R.id.et_password_register);
        met_username_register=findViewById(R.id.et_username_register);
        mbtn_start=findViewById(R.id.btn_reigster);
        mbtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              register();
            }
        });
    }
    public void register(){
        String uname=met_username_register.getText().toString();
        String pword=met_password_register.getText().toString();
        String email=met_email_register.getText().toString();
        String pword_check =met_password_check.getText().toString();
        if(!pword_check.equals(pword)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }
        else if(!isEmail(email)){
            Toast.makeText(this,"邮箱格式错误",Toast.LENGTH_SHORT).show();
        }
        else{
             AccountLogin aregister = new AccountLogin();
             final RegisterJson[] rStatus = {null};
             Runnable runnable = new Runnable() {
                @Override
                   public void run() {
                   rStatus[0] =aregister.accountRegister(uname,new WidgetSetting().getMD5(pword),email);
            }
        };
        Thread thread = new Thread(runnable);
        try{
            thread.start();
            thread.join(4000);
        if(rStatus[0] !=null&&rStatus[0].RegisterStatus){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            intent.putExtra("uname",uname);
            intent.putExtra("pword",pword);
            setResult(RESULT_OK,intent);
            finish();
        }
        else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
          }
        }
        catch (Exception e) {
        e.printStackTrace();
          }
        }
       }
    /********判断邮箱格式********/
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
