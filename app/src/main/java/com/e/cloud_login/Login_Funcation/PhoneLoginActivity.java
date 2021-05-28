package com.e.cloud_login.Login_Funcation;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cloud_login.R;

public class PhoneLoginActivity extends Activity {
    TextView tv_title,tv_1,tv_2,tv_agreement;
    ImageView img_phone,img_token;
    EditText et_phone,et_token;
    Button btn_token,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        InitWidget();
    }

    void InitWidget(){
        tv_title=findViewById(R.id.phonelogin_title);
        tv_1 = findViewById(R.id.phonelogin_tv_message1);
        tv_2 = findViewById(R.id.phonelogin_tv_message2);
        tv_agreement= findViewById(R.id.phonelogin_tv_agreement);
        img_phone = findViewById(R.id.phonelogin_img_phone);
        img_token = findViewById(R.id.phonelogin_img_token);
        et_phone = findViewById(R.id.phonelogin_et_phone);
        et_token = findViewById(R.id.phonelogin_et_token);
        btn_token = findViewById(R.id.phonelogin_btn_token);
        btn_login = findViewById(R.id.phonelogin_btn_login);
    }
}
