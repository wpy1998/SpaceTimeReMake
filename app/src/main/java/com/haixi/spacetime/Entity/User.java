package com.haixi.spacetime.Entity;

import org.json.JSONObject;

public class User {
    public String phoneNumber;
    public String gender;
    public String major;
    public String comeFrom;
    public String avatar;
    public String birthday;
    public String interests;
    public String profession;
    public String position;
    public String school;
    public String userName;
    public String labels;
    public String signature;

    public int imageId;

    public User(){
        phoneNumber = null;
        avatar = null;
        gender = null;
        major = null;
        comeFrom = null;
//        birthday = null;
        interests = null;
        profession = null;
        position = null;
        school = null;
        userName = null;
        labels = null;
        signature = null;
        imageId = 0;
    }

    public static void setMessage(String data, User user){
        try{
            JSONObject object1 = new JSONObject(data);
            user.birthday = object1.getString("birthday");
            String year, month, day;
            year = user.birthday.substring(0, 4);
            month = user.birthday.substring(5, 7);
            day = user.birthday.substring(8, 10);
            user.birthday = year + "/" + month + "/" + day;
            user.comeFrom = object1.getString("comeFrom");
            user.gender = object1.getString("gender");
            user.avatar = object1.getString("avatar");
            user.interests = object1.getString("interests");
            user.labels = object1.getString("labels");
            user.major = object1.getString("major");
            user.phoneNumber = object1.getString("phoneNumber");
            user.profession = object1.getString("profession");
            user.position = object1.getString("position");
            user.school = object1.getString("school");
            user.signature = object1.getString("signature");
            user.userName = object1.getString("username");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
