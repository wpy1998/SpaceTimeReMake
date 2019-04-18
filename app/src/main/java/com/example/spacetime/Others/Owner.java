package com.example.spacetime.Others;

import org.json.JSONObject;

public class Owner {
    public static int ownerId = 0;
    public static String token = null;
    public static String phoneNumber = null;
    public static String gender = null;
    public static String major = null;
    public static String comeFrom = null;
    public static String avatar = null;
    public static String birthday = null;
    public static String interests = null;
    public static String profession = null;
    public static String school = null;
    public static String userName = null;
    public static String labels = null;
    public static String password = null;

    public static void initMessage(){
        ownerId = 0;
        token = null;
        phoneNumber = null;
        gender = null;
        major = null;
        comeFrom = null;
        avatar = null;
        birthday = null;
        interests = null;
        profession = null;
        school = null;
        userName = null;
        labels = null;
        password = null;
    }

    public static void setMessage(String data){
        try{
            JSONObject object1 = new JSONObject(data);
            ownerId = object1.getInt("id");
            birthday = object1.getString("birthday");
            comeFrom = object1.getString("comeFrom");
            gender = object1.getString("gender");
            interests = object1.getString("interests");
            labels = object1.getString("labels");
            major = object1.getString("major");
            phoneNumber = object1.getString("phoneNumber");
            profession = object1.getString("profession");
            school = object1.getString("school");
            userName = object1.getString("username");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
