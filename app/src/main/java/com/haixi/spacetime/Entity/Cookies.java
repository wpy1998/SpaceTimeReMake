package com.haixi.spacetime.Entity;

import org.json.JSONObject;

public class Cookies {
    public static String token = null;
    public static String password = null;
    public static User owner = new User();
    public static String phoneNumber;

    public static String newPassword = null;

    public static void initCookies(){
        token = null;
        password = null;
        owner = null;
        phoneNumber = null;
        newPassword = null;
    }
}
