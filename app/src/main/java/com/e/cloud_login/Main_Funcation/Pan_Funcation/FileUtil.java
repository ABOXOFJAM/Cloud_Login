package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * 将文件转为byte[]
 */
public class FileUtil {
    /**
     * 将文件转换成byte数组
     * @param file 目标文件
     * @return
     */
    public static byte[] fileToByte(File file) {
        byte[] bytes = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            bytes = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    /**
     * byte数组转File
     * @param byteArray 字节数组
     * @param targetPath 目标路径
     */
    public static void byteArrayToFile(byte[] byteArray, String targetPath,String name) throws IOException {
        InputStream in = new ByteArrayInputStream(byteArray);
        File Father_file = new File(targetPath,"DOWNLOAD_PDF");
        String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
        if (!Father_file.exists()) {
            Father_file.mkdir();
        }
        File file = new File(targetPath+"/DOWNLOAD_PDF/"+name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * 判断文件是否存在
     * */
    public static boolean fileIsExists(String strFile) {
        try {
            File f=new File(strFile);
            if(!f.exists()) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 截取view作为图片
     * @param
     * @return
     */
    public static Bitmap convertViewToBitmap(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }
    /**
     * 根据指定的Activity截图（去除状态栏）
     *
     * @param activity 要截图的Activity
     * @return Bitmap
     */
    public static Bitmap shotActivityNoStatusBar(Activity activity,View temp) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        // 获取屏幕长和高
        int view_height = temp.getHeight();
        int widths = displayMetrics.widthPixels;
        int heights = displayMetrics.heightPixels-view_height;

//        Display display = activity.getWindowManager().getDefaultDisplay();
//        // 获取屏幕宽和高
//        int widths = display.getWidth();
//        int heights = display.getHeight();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights+view_height, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 根据目录删除所有的文件
     *
     * @param filePath
     */
    public static void deleteFileByPath(String filePath) {
        File rootfile = new File(filePath);
        if (rootfile != null && rootfile.exists()) {
            if (rootfile.isDirectory()) {
                File[] files = rootfile.listFiles();
                if (files != null) {
                    for (File childFile : files) {
                        deleteFileByPath(childFile.getAbsolutePath());
                    }
                }
                rootfile.delete();
            } else if (rootfile.isFile()) {
                rootfile.delete();
            }
        }
    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * 获取某个文件或者目录的大小
     *
     * @param f
     * @return
     */
    public static long getFileSize(File f) {
        long size = 0;
        if (f.exists()) {
            if (f.isDirectory()) {
                File flist[] = f.listFiles();
                if (flist != null) {
                    for (int j = 0; j < flist.length; j++) {

                        if (flist[j].isDirectory()) {
                            size = size + getFileSize(flist[j]);
                        } else {
                            size = size + flist[j].length();
                        }
                    }

                }
            } else {
                size = f.length();
            }
        }
        return size;
    }

    //获取文件列表

    public static List<String> getFileList(File f) {
        List<String> list = new ArrayList<>();
        if (f.exists()) {
            if (f.isDirectory()) {
                File flist[] = f.listFiles();
                if (flist != null) {
                    for (int j = 0; j < flist.length; j++) {
                        if (flist[j].isDirectory()) {
                            list.addAll(getFileList(flist[j]));
                        } else {
                            list.add(flist[j].getAbsolutePath());
                        }
                    }

                }
            } else {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }

    /*
     *Bitmap转byte数组
     */
    public static byte[] BitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }
    /*
     *byte数组转Bitmap
     */
    public static Bitmap BytesToBitmap(byte[] bis) {
        return BitmapFactory.decodeByteArray(bis, 0, bis.length);
    }

    /**
     * 检查手机号格式
     * @param s
     * @return
     */
    public static boolean isMobileNO(String s) {
        Pattern p = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 压缩图片（质量压缩）
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
}

