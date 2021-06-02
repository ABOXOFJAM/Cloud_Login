package com.e.cloud_login.Main_Funcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.cloud_login.Main_Funcation.Pan_Funcation.HandWrite;
import com.e.cloud_login.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class CanvasActivity extends AppCompatActivity {
    HandWrite handWrite;
    Button btn_clear;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        InitData();
    }

    private void InitData() {
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        File file = new File(path);
        pdfView = findViewById(R.id.canvas_pdf);
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(false)//双击这里禁用，在tap的时候添加监听设置呼出页面
                .defaultPage(1)//打开时候的默认页面
                .onPageChange(new OnPageChangeListener() {//翻页监听
                    public void onPageChanged(int page, int pageCount) {
                        //这里面写当监听器监听到对应改变是的反应
                        //例如展示数据与处理数据。
                        //Toast.makeText(MainActivity.this, page + " / " + pageCount, Toast.LENGTH_SHORT).show();
                    }
                })
                //.onDraw()
               // .onDrawAll()//允许在所有页面上分别为每个页面绘制内容
                //.onTap()
                .enableAntialiasing(true)
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)//模式，以适应视图中的页面
                .fitEachPage(false)//使每个页面适合视图，否则较小页面相对于最大页面缩放
                .pageSnap(false)
               // .onPageChange()//当翻页时候
                .load();

        handWrite = findViewById(R.id.canvas_handwrite);
        btn_clear = findViewById(R.id.canvas_btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWrite.clear();
            }
        });
    }

}
