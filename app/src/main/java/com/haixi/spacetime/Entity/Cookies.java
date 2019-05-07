package com.haixi.spacetime.Entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

public class Cookies {
    public static String token = null;
    public static String password = null;
    public static User owner = new User();
    public static String phoneNumber;
    public static String newPassword = null;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String securityToken;

    public static String filePath = "/storage/emulated/0/SpaceTime/";

    public static final int resultCode = 1;

    public static Bitmap bitmap;

    public static void setBitmap(String path){
        bitmap = BitmapFactory.decodeFile(path);
    }


    public static void initCookies(){
        token = null;
        password = null;
        owner = new User();
        phoneNumber = null;
        newPassword = null;
    }

    public static void setImageToken(String data){
        try {
            JSONObject object = new JSONObject(data);
            String data1 = object.getString("data");
            JSONObject jsonObject = new JSONObject(data1);
            accessKeyId = jsonObject.getString("accessKeyId");
            accessKeySecret = jsonObject.getString("accessKeySecret");
            securityToken = jsonObject.getString("securityToken");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
