package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.e.cloud_login.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;

public class HandWrite extends View {
    Paint paint = null;//定义画笔
    Bitmap origBit = null;//存放原始图像
    Bitmap new_1Bit = null;//存放从原始图象复制的位图图象
    Bitmap new_2Bit = null;//存放处理后的图象
    float startX=0,startY=0;//画线的七点坐标
    float clickX=0,clickY=0;//画线的终点坐标
    boolean isMove = true;//设置是否画线的标记
    boolean isClear = false;//设置是否清除涂鸦的标记
    int color = Color.BLUE;//设置画笔的颜色
    float strokeWidth = 2.0f;//设置画笔的宽度
    static int mScreenWidth ;
    static int  mScreenHeight;
    Matrix mMatrix = new Matrix();
    int photo;
    /**
     * 构造方法
     */

    public HandWrite(Context context, AttributeSet attrs) {
        super(context,attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;

        //从资源中获取原始图像
        origBit = BitmapFactory.decodeResource(getResources(),R.drawable.background).copy(Bitmap.Config.ARGB_8888,true);
        origBit = Bitmap.createScaledBitmap(origBit, mScreenWidth, mScreenHeight, true);
        //建立原始图像的位图
        new_1Bit = Bitmap.createBitmap(origBit);
    }
    /**

     * 清除函数
     */
    public void clear(){
        isClear = true;
        new_2Bit = Bitmap.createBitmap(origBit);
        invalidate();
    }
    /**
     * 保存函数
     */
    public void save(){
        //保存图片
        File appDir = new File(Environment.getExternalStorageState(),"Boohee");
        if(!appDir.exists()){
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis()+".jpg";
        File file = new File(appDir,fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            new_1Bit.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //其次把文件插件到系统图库
        Context context = getContext();
        try{
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(),fileName,null);
            Log.d("TAG",file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
    }
    /**
     * 设置笔的宽度
     * @param strokeWidth
     */
    public void setSyle(float strokeWidth){
        this.strokeWidth = strokeWidth;
    }

    /**
     * 画图的时候会调用onDraw函数
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //将画好的图片返回到画布上
        canvas.drawBitmap(HandWriting(new_1Bit),0,0,null);
        //画布是100%匹配view的
    }

    /**
     * 绘制方法
     * @param newBit
     * @return
     */
    private Bitmap HandWriting(Bitmap newBit){//记录绘制图形
        Canvas canvas = null;//定义画布
        if (isClear){//创建绘制新图形的画布
            canvas = new Canvas(new_2Bit);
        }
        else{
            canvas = new Canvas(newBit);//创建绘制原图形的画布
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        if(isMove){
            canvas.drawLine(startX,startY,clickX,clickY,paint);//在画布上画线条,调用onDraw
        }
        startX = clickX;
        startY = clickY;
        if(isClear){
            return new_2Bit;// 返回新绘制的图像
        }
        return newBit;//若清屏,则返回原图像
    }

    /**
     * 触摸的方法
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event){
        clickX = event.getX();//获取触摸坐标位置
        clickY = event.getY();
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            isMove = false;
            invalidate();//刷新
            return true;
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){//记录在屏幕上滑动的轨迹
            isMove = true;
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }
}