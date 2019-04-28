package com.haixi.spacetime.Entity;

import org.json.JSONObject;

public class User {
    public int userId;
    public String phoneNumber;
    public String gender;
    public String major;
    public String comeFrom;
    public String avatar;
    public String birthday;
    public String interests;
    public String profession;
    public String school;
    public String userName;
    public String labels;


    public static void setMessage(String data, User user){
        try{
            JSONObject object1 = new JSONObject(data);
            user.userId = object1.getInt("id");
            user.birthday = object1.getString("birthday");
            user.comeFrom = object1.getString("comeFrom");
            user.gender = object1.getString("gender");
            user.interests = object1.getString("interests");
            user.labels = object1.getString("labels");
            user.major = object1.getString("major");
            user.phoneNumber = object1.getString("phoneNumber");
            user.profession = object1.getString("profession");
            user.school = object1.getString("school");
            user.userName = object1.getString("username");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
