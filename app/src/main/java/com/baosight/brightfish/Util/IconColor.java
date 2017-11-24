package com.baosight.brightfish.Util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by saitama on 2017/11/21.
 */
public class IconColor {
    private static final String TAG = "IconColor";
    public static Bitmap ChangeIconColor(ImageView imageView,String aimColorStr){
        imageView.setDrawingCacheEnabled(true);
        Bitmap sourceBitmap=Bitmap.createBitmap(imageView.getDrawingCache());
        if(sourceBitmap == null || sourceBitmap.isRecycled()){
            Log.d(TAG, "ChangeIconColor: ==========bitmap+"+sourceBitmap);
            throw new RuntimeException("source exception!!!");
        }
        int aimColor;
        try {
            aimColor = Color.parseColor(aimColorStr.trim());
        } catch (Exception e){
            throw new RuntimeException("aimColorStr error!!!");
        }
        //按照图标的大小创建数组
        int mBitmapWidth = sourceBitmap.getWidth();
        int mBitmapHeight = sourceBitmap.getHeight();
        int mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        int[] mArrayColor = new int[mArrayColorLengh];

        //循环bitmap 的每个像素点，查看alpha值
        int count = 0;
        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                //获得Bitmap 图片中每一个点的color颜色值
                int color = sourceBitmap.getPixel(j, i);
                int a = Color.alpha(color);
                if(a != 0){//不等于0 即不透明部分，设置成我们想要的颜色
                    mArrayColor[count] = aimColor;
                } else {//透明仍然为透明
                    int aimColor2 = Color.parseColor("#00000000");
                    mArrayColor[count] = aimColor2;
                }
                count++;
            }
        }
        //根据数组创建新的Bitmap
        Bitmap newBitmap = Bitmap.createBitmap(mBitmapWidth,mBitmapHeight, Bitmap.Config.ARGB_8888);
        newBitmap.setPixels(mArrayColor,0,mBitmapWidth,0,0,mBitmapWidth,mBitmapHeight);
        imageView.setDrawingCacheEnabled(false);
        return newBitmap;

    }
}
