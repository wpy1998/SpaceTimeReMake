package com.haixi.spacetime.Entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Dynamic{
    public int imageId = -1;
    public String imageUrls;
    public String content;
    public int likeCount;
    public int dynamicId;
    public int commentCount;
    public String publishTime;
    public boolean liked;

    public Circle circle;
    public User user;

    public Dynamic(){
        user = new User();
        circle = new Circle();
    }


    public static void setDynamic(Dynamic dynamic, JSONObject jsonObject) throws JSONException {
        dynamic.commentCount = jsonObject.getInt("commentCount");
        dynamic.content = jsonObject.getString("content");
        dynamic.dynamicId = jsonObject.getInt("id");
        dynamic.likeCount = jsonObject.getInt("likeCount");
        dynamic.publishTime = jsonObject.getString("publishTime");
        dynamic.liked = jsonObject.getBoolean("liked");
        String year, month, day;
        year = dynamic.publishTime.substring(0, 4);
        month = dynamic.publishTime.substring(5, 7);
        day = dynamic.publishTime.substring(8, 10);
        dynamic.publishTime = year + "/" + month + "/" + day;
        dynamic.user.phoneNumber = jsonObject.getString("publisherPhoneNumber");
        dynamic.circle.id = jsonObject.getInt("socialCircleId");
        dynamic.user.userName = jsonObject.getString("publisherUsername");
        dynamic.liked = jsonObject.getBoolean("liked");
    }

    public String getJSONString(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("circle.name", circle.name);
            jsonObject.put("circle.id", circle.id);
            jsonObject.put("user.userName", user.userName);
            jsonObject.put("user.phoneNumber", user.phoneNumber);
            jsonObject.put("imageId",imageId);
            jsonObject.put("content", content);
            jsonObject.put("likeCount", likeCount);
            jsonObject.put("dynamicId", dynamicId);
            jsonObject.put("commentCount", commentCount);
            jsonObject.put("publishTime", publishTime);
            jsonObject.put("liked", liked);
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static void getDynamic(Dynamic dynamic, String json){
        try {
            JSONObject object = new JSONObject(json);
            dynamic.circle.name = object.getString("circle.name");
            dynamic.circle.id = object.getInt("circle.id");
            dynamic.user.userName = object.getString("user.userName");
            dynamic.user.phoneNumber = object.getString("user.phoneNumber");
            dynamic.imageId = object.getInt("imageId");
            dynamic.content = object.getString("content");
            dynamic.likeCount = object.getInt("likeCount");
            dynamic.dynamicId = object.getInt("dynamicId");
            dynamic.commentCount = object.getInt("commentCount");
            dynamic.publishTime = object.getString("publishTime");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
