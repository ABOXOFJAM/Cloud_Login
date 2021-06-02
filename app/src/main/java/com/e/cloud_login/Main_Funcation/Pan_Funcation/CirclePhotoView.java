package com.e.cloud_login.Main_Funcation.Pan_Funcation;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.e.cloud_login.R;

public class CirclePhotoView extends View {
    private int max;//最大进度
    private int roundColor;//圈颜色
    private int roundProgressColor;//进度颜色
    private int textColor;//文字颜色
    private int backgroundColor;//背景颜色
    private float textSize;//文字大小
    private float roundWidth;//圈宽度
    private boolean textShow;//是否显示文字
    private int progress;//当前进度
    private Paint mPaintCircle;      //画圆形图像的笔
    private Paint mPaintBorder;          //画圆形边界的笔
    private BitmapShader mBitmapShader;      //图像着色器，可以用来画圆
    private Matrix mMatrix;          //图片变换处理器-用来缩放图片以适应view控件的大小
    private int mWidth;        //获得控件宽度
    private int mHeight;             //获得控件高度
    private float mRadius;             //中心园的半径
    public static final int STROKE = 0;
    public static final int FILL = 1;
    private Bitmap bitmap;
    private float mBitmapHeight;
    private float mBitmapWidth;
    private  Bitmap afterBitmap ;


    public CirclePhotoView(Context context) {
        super(context);
    }

    public CirclePhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        max = typedArray.getInteger(R.styleable.CustomProgressBar_max, 100);
        roundColor = typedArray.getColor(R.styleable.CustomProgressBar_roundColor, Color.RED);
        roundProgressColor = typedArray.getColor(R.styleable.CustomProgressBar_roundProgressColor, Color.BLUE);
        textColor = typedArray.getColor(R.styleable.CustomProgressBar_textColor, Color.GREEN);
        textSize = typedArray.getDimension(R.styleable.CustomProgressBar_textSize, 55);
        roundWidth = typedArray.getDimension(R.styleable.CustomProgressBar_roundWidth, 10);
        textShow = typedArray.getBoolean(R.styleable.CustomProgressBar_textShow, true);
        backgroundColor = typedArray.getColor(R.styleable.CustomProgressBar_backgroundColor, Color.GRAY);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        //初始化图片变换处理器
        mMatrix = new Matrix();
        //圆形头像画笔设置
        mPaintCircle = new Paint();
        mPaintCircle.setColor(roundColor);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintCircle.setStrokeWidth(roundWidth);
        mPaintCircle.setAntiAlias(true);

        //边框设置
        mPaintBorder = new Paint();
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(roundWidth);
        mPaintBorder.setColor(roundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);//设置圆形图片背景色，和整体背景保持一致为好。
        mWidth = getWidth() / 2;
        mHeight = getHeight() / 2;
        mRadius = Math.min(mWidth, mHeight) - roundWidth;
        Drawable drawable = getBackground();
        if (drawable == null) {
            super.onDraw(canvas);
        } else {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            if(bitmap==null){
                return;
            }
            mBitmapHeight = bitmap.getHeight();
            mBitmapWidth = bitmap.getWidth();
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            float scale = Math.max(bitmap.getWidth(),bitmap.getHeight()) / Math.min(bitmap.getWidth(),bitmap.getHeight());
            mMatrix.setScale(scale,scale);
            mBitmapShader.setLocalMatrix(mMatrix);
            mPaintCircle.setShader(mBitmapShader);
            canvas.drawCircle(mWidth, mHeight, mRadius, mPaintCircle);
            canvas.drawCircle(mWidth, mHeight, mRadius + roundWidth / 2, mPaintBorder);

            /* 也可以绘制圆形
            ShapeDrawable shapeDrawble = new ShapeDrawable(new OvalShape());
            shapeDrawble.getPaint().setShader(mBitmapShader);
            shapeDrawble.setBounds(0,0,getWidth(),getHeight());
            shapeDrawble.draw(canvas);
            */
        }
    }
}
