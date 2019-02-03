package com.example.spacetime.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FileOperation {
    public static Bitmap bitmap;

    public static void setBitmap(String path){
        bitmap = BitmapFactory.decodeFile(path);
    }

    Context context;
    private int width, height;
    public FileOperation(Context context){
        this.context = context;
    }

    public Bitmap getImage(String srcPath, int width, int height) {
        this.width = width;
        this.height = height;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        if (w < width || h < height){
            return null;
        }

        float hh = width;// 这里设置高度为800f
        float ww = height;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        int we = (int) (newOpts.outWidth / ww), he = (int) (newOpts.outHeight / hh);
        if (we > he) {// 如果宽度大的话根据宽度固定大小缩放
            be = he;
        } else{// 如果高度高的话根据宽度固定大小缩放
            be = we;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 1000) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        int x, y, bitmapWidth, bitmapHeight;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
        x = (bitmapWidth / 2) - (width / 2);
        y = (bitmapHeight / 2) - (height / 2);
        Bitmap end = Bitmap.createBitmap(bitmap, x, y, width, height);
        return end;
    }
}
