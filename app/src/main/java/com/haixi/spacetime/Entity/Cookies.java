package com.haixi.spacetime.Entity;

import com.haixi.spacetime.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cookies {
    public static String token = null;
    public static String password = null;
    public static User owner = new User();
    public static String phoneNumber;
    public static String newPassword = null;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String securityToken;

    public static final int resultCode = 1;

    public static void initCookies(){
        token = null;
        password = null;
        owner = new User();
        phoneNumber = null;
        newPassword = null;
    }
    public static List<Dynamic> circleDynamics = new ArrayList<Dynamic>();
    public static List<Dynamic> followDynamics = new ArrayList<Dynamic>();
    public static List<Dynamic> ownerDynamics = new ArrayList<Dynamic>();
    public static List<String> tags;
    public static int currentUserId;

    public static void setImageToken(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            accessKeyId = jsonObject.getString("accessKeyId");
            accessKeySecret = jsonObject.getString("accessKeySecret");
            securityToken = jsonObject.getString("securityToken");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
