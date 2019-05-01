package com.haixi.spacetime.Entity;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

@Route(path = "/spaceTime/dynamic/json")
public class Dynamic implements SerializationService {
    public int imageId = -1;
    public String imageUrls;
    public String content;
    public int likeCount;
    public int dynamicId;
    public int commentCount;
    public String publishTime;

    public Circle circle;
    public User user;

    public static void setDynamic(Dynamic dynamic, JSONObject jsonObject) throws JSONException {
        dynamic.user = new User();
        dynamic.circle = new Circle();
        dynamic.commentCount = jsonObject.getInt("commentCount");
        dynamic.content = jsonObject.getString("content");
        dynamic.dynamicId = jsonObject.getInt("id");
        dynamic.likeCount = jsonObject.getInt("likeCount");
        dynamic.publishTime = jsonObject.getString("publishTime");
        String year, month, day;
        year = dynamic.publishTime.substring(0, 4);
        month = dynamic.publishTime.substring(5, 7);
        day = dynamic.publishTime.substring(8, 10);
        dynamic.publishTime = year + "/" + month + "/" + day;
        dynamic.user.phoneNumber = jsonObject.getString("publisherPhoneNumber");
        dynamic.circle.id = jsonObject.getInt("socialCircleId");
        dynamic.user.userName = jsonObject.getString("publisherUsername");
    }

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return null;
    }

    @Override
    public String object2Json(Object instance) {
        return null;
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}
