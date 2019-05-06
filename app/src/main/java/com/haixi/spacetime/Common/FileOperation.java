package com.haixi.spacetime.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FileOperation {
    public static Bitmap bitmap;

    Context context;
    public FileOperation(Context context){
        this.context = context;
    }

    public static void setBitmap(String path){
        bitmap = BitmapFactory.decodeFile(path);
    }
}
