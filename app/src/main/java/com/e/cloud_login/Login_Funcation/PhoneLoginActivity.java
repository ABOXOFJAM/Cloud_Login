package com.e.cloud_login.Login_Funcation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cloud_login.Main_Funcation.HomeActivity;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.Edit;
import com.e.cloud_login.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneLoginActivity extends Activity {
    TextView tv_title,tv_1,tv_2,tv_agreement;
    ImageView img_phone,img_token;
    EditText et_phone,et_token;
    Button btn_token,btn_login;
    private Integer TYPE_LOGIN = 4;
    AccountLogin accountLogin =  new AccountLogin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        InitWidget();
    }
    public void Login(){
        String phone = et_phone.getText().toString();
        //先登入，如果不存在就自动组册并再次登入
        Integer result = accountLogin.getcode(phone,TYPE_LOGIN);
        if(result == 200){
            Toast.makeText(PhoneLoginActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
        }
    }
    void InitWidget(){
        Onclick onclick = new Onclick();
        tv_title=findViewById(R.id.phonelogin_title);
        tv_1 = findViewById(R.id.phonelogin_tv_message1);
        tv_2 = findViewById(R.id.phonelogin_tv_message2);
        tv_agreement= findViewById(R.id.phonelogin_tv_agreement);
        img_phone = findViewById(R.id.phonelogin_img_phone);
        img_token = findViewById(R.id.phonelogin_img_token);
        et_phone = findViewById(R.id.phonelogin_et_phone);
        et_token = findViewById(R.id.phonelogin_et_token);
        Edit.setEditTextInhibitInputSpace(et_phone);//禁止输入空格和特殊符号
        Edit.setEditTextInhibitInputSpeChat(et_phone);
        Edit.setEditTextInhibitInputSpeChat(et_token);
        Edit.setEditTextInhibitInputSpace(et_token);
        btn_token = findViewById(R.id.phonelogin_btn_token);
        btn_login = findViewById(R.id.phonelogin_btn_login);
        btn_token.setOnClickListener(onclick);
        btn_login.setOnClickListener(onclick);
    }
    public class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.phonelogin_btn_token:{
                    Login();
                    break;
                }
                case R.id.phonelogin_btn_login:{
                    String code = et_token.getText().toString();
                    String phone = et_phone.getText().toString();
                    if(code==""){
                        Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                    }else{
                        //String token = accountLogin.phoneLogin(phone,code);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                }
            }
        }
    }
}
