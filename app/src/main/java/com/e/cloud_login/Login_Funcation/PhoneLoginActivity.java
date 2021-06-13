package com.e.cloud_login.Login_Funcation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.cloud_login.Main_Funcation.HomeActivity;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.EditWrite;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.FileUtil;
import com.e.cloud_login.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneLoginActivity extends Activity {
    TextView tv_title,tv_1,tv_2,tv_agreement;
    ImageView img_phone,img_token;
    EditText et_phone,et_token;
    Button btn_token,btn_login;
    private LinearLayout linearLayout;
    private Integer TYPE_LOGIN = 4;
    private TimeCount time ;
    AccountLogin accountLogin =  new AccountLogin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        InitWidget();
    }
    public void Login(String phone){
        //先登入，如果不存在就自动组册并再次登入
        Integer result = accountLogin.getcode(phone,TYPE_LOGIN);
        switch (result){
            case 0:{
                Toast.makeText(PhoneLoginActivity.this,"验证码重复",Toast.LENGTH_SHORT).show();
                break;
            }
            case 1:{
                Toast.makeText(PhoneLoginActivity.this,"验证码已过期",Toast.LENGTH_SHORT).show();
                break;
            }
            case 2:{
                Toast.makeText(PhoneLoginActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
                break;
            } default:{
                Toast.makeText(PhoneLoginActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    }
    void InitWidget(){
        Onclick onclick = new Onclick();
        linearLayout = findViewById(R.id.phonelogin_login);
        tv_title=findViewById(R.id.phonelogin_title);
        tv_1 = findViewById(R.id.phonelogin_tv_message1);
        tv_2 = findViewById(R.id.phonelogin_tv_message2);
        tv_agreement= findViewById(R.id.phonelogin_tv_agreement);
        img_phone = findViewById(R.id.phonelogin_img_phone);
        img_token = findViewById(R.id.phonelogin_img_token);
        et_phone = findViewById(R.id.phonelogin_et_phone);
        et_token = findViewById(R.id.phonelogin_et_token);
        EditWrite.setEditTextInhibitInputSpace(et_phone);//禁止输入空格和特殊符号
        EditWrite.setEditTextInhibitInputSpeChat(et_phone);
        EditWrite.setEditTextInhibitInputSpeChat(et_token);
        EditWrite.setEditTextInhibitInputSpace(et_token);
        btn_token = findViewById(R.id.phonelogin_btn_token);
        btn_login = findViewById(R.id.phonelogin_btn_login);
        btn_token.setOnClickListener(onclick);
        btn_login.setOnClickListener(onclick);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.file_from_bottom);
        linearLayout.setAnimation(animation);
    }
    public class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.phonelogin_btn_token:{
                    String phone = et_phone.getText().toString();
                    if(FileUtil.isMobileNO(phone)){
                        time = new TimeCount(60000, 1000);
                        time.start();
                        Login(phone);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"手机号格式错误",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case R.id.phonelogin_btn_login:{
                    String code = et_token.getText().toString();
                    String phone = et_phone.getText().toString();
                    if(code==""){
                        Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                    }else{
                        String token = accountLogin.phoneLogin(phone,code);
                        if(token != null){
                            Toast.makeText(getApplicationContext(),"登入成功",Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor userinfo = getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                            userinfo.putString("token",token);
                            userinfo.putString("phone",phone);
                            userinfo.commit();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }
                    }
                }
            }
        }
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_token.setBackgroundResource(R.drawable.shape_btn_white);
            btn_token.setTextColor(Color.parseColor("#000000"));
            btn_token.setClickable(false);
            btn_token.setText("重新发送"+"("+millisUntilFinished / 1000 +") ");
        }

        @Override
        public void onFinish() {
            btn_token.setText("重新获取");
            btn_token.setTextColor(Color.WHITE);
            btn_token.setTextSize(1);
            btn_token.setClickable(true);
            btn_token.setBackgroundResource(R.drawable.shape_btn_black);
        }
   }


}
