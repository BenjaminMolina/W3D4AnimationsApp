package com.texocoyotl.w3d4animationsapp;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by admin on 4/14/2016.
 */
public class Utils {

    private static String TAG = "LOG_";

    public static Bitmap cropBitmap(Bitmap source){
        Bitmap dstBmp;
        if (source.getWidth() >= source.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    source,
                    source.getWidth()/2 - source.getHeight()/2,
                    0,
                    source.getHeight(),
                    source.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    source,
                    0,
                    source.getHeight()/2 - source.getWidth()/2,
                    source.getWidth(),
                    source.getWidth()
            );
        }
        return dstBmp;
    }

    public static Bitmap getBitmapPiece(Bitmap source, int x, int y, int size){
        //Log.d(TAG, "getBitmapPiece: " + x + " " + y);

        int startX = source.getWidth() * x / size;
        int startY = source.getHeight() * y / size;

        //Log.d(TAG, "getBitmapPiece: piece " + startX + " " + startY + " " + size);

        return Bitmap.createBitmap(source, startX, startY, source.getWidth() / size, source.getHeight() / size);
    }
}
