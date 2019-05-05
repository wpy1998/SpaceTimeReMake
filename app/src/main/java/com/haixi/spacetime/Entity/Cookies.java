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

    public final String filePath = "";

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

    public static boolean isExistTag(String tag){
        for (String tag1: tags){
            if (tag.equals(tag1)){
                return true;//存在
            }
        }
        return false;
    }
}
