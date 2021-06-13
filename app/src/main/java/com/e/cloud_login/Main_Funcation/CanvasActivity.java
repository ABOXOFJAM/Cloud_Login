package com.e.cloud_login.Main_Funcation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.cloud_login.Data.FilelistBean;
import com.e.cloud_login.Fragment.HomeFragment;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.FileUtil;
import com.e.cloud_login.Main_Funcation.Pan_Funcation.HandWrite;
import com.e.cloud_login.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLongPressListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class CanvasActivity extends AppCompatActivity {
    HandWrite handWrite;
    Button btn_finish;
    ImageButton btn_more,btn_out;
    private int GET_IMAGE = 0x02;
    private long endTime = 0;
    PDFView pdfView;
    LinearLayout linearLayout;
    Bitmap bitmap ;
    byte[] bytes ;//Litepal中pdf的保存形式
    Boolean save;//定义是否保存首图片
    Bitmap mBitmap;
    Boolean finish = false,start = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hide_tool();
        super.onCreate(savedInstanceState);
        LitePal.initialize(getApplicationContext());
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_canvas);
        hide_tool();
        InitData();
    }
    /**
     * 隐藏标题栏
     */
    public void hide_tool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 实现透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void InitData() {
        MyOnClick myOnClick = new MyOnClick();
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        save = intent.getBooleanExtra("MineFragment",false);
        linearLayout = findViewById(R.id.canvas_toolbar);
        pdfView = findViewById(R.id.canvas_pdf);
        handWrite = findViewById(R.id.canvas_handwrite);
        File file = new File(path);
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(false)//双击这里禁用，在tap的时候添加监听设置呼出页面
                .defaultPage(0)//打开时候的默认页面
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                        if (start = true) {
                            Paint paint = new Paint();
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setAntiAlias(true);
                            paint.setColor(Color.BLUE);
                            paint.setStrokeWidth(2.0f);
                            //绘制Cap为BUTT的点
                            if (mBitmap != null) {
                                canvas.drawBitmap(mBitmap, 0, 0, paint);
                            }
                            mBitmap = null;
                            start = false;
                        }
                    }
                })
                .onLongPress(new OnLongPressListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {
                        handWrite.setVisibility(View.VISIBLE);
                        btn_finish.setVisibility(View.VISIBLE);
                    }
                })
                .enableAntialiasing(true)
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)//模式，以适应视图中的页面
                .fitEachPage(false)//使每个页面适合视图，否则较小页面相对于最大页面缩放
                .pageSnap(true)//翻页对齐
               // .onPageChange()//当翻页时候
                .load();

        btn_finish = findViewById(R.id.canvas_btn_ok);
        btn_out = findViewById(R.id.canvas_btn_out);
        btn_more = findViewById(R.id.canvas_btn_more);
        btn_finish.setOnClickListener(myOnClick);
        btn_out.setOnClickListener(myOnClick);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();//当前多少毫秒
            if (currentTime - endTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
                endTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
        }
        return true;
    }
    public class MyOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.canvas_btn_ok:{
                    start = true;
                    mBitmap = handWrite.getmBitmap();
                    pdfView.invalidate();
                    handWrite.setVisibility(View.GONE);
                    btn_finish.setVisibility(View.GONE);
                    break;
                }
                case R.id.canvas_btn_out:{
                    /**
                     * 设置第一张图片为主视图
                     */
                    if(!save) {
                        handWrite.setVisibility(View.GONE);
                        bitmap = FileUtil.shotActivityNoStatusBar(CanvasActivity.this, linearLayout);
                        bytes = FileUtil.BitmapToBytes(bitmap);
                        List<FilelistBean> filelistBeanList = LitePal.where("img_url = ?", getIntent().getStringExtra("path")).find(FilelistBean.class);
                        int position = getIntent().getIntExtra("position", filelistBeanList.size());
                        FilelistBean filelistBean = filelistBeanList.get(position);
                        filelistBean.img = bytes;
                        filelistBean.save();
                    }
                    finish();//数据库插入的pdf过大
                    break;
                }
                case R.id.canvas_btn_more:{
                    break;
                }
            }
        }
    }
}
