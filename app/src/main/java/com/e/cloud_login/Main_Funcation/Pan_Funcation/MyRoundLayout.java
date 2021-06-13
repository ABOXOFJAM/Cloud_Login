package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class MyRoundLayout extends FrameLayout {
    private float topLeftRadius;
    private float topRightRadius;

    public MyRoundLayout(@NonNull Context context) {
        super(context);
    }
    /**
     * 设置左上角圆角弧度
     *
     * @param topLeftRadius
     */
    public void setDrawTopLeft(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        invalidate();
    }

    /**
     * 设置右上角圆角弧度
     *
     * @param topRightRadius
     */
    public void setDrawTopRight(float topRightRadius) {
        this.topRightRadius = topRightRadius;
        invalidate();
    }

}
