package com.example.spacetime.Others;

import android.content.Context;
import android.content.Intent;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActionLG {
    public final String web = "http://59.110.172.61";
    private static final MediaType JSON= MediaType.parse("application/json;charset=utf-8");
    private static final MediaType URLENCODED = MediaType
            .parse("application/x-www-form-urlencoded; charset=UTF-8");

    private Context context;
    public OkHttpActionLG(Context context){
        this.context = context;
    }


    //post
    public void registerUsers(final String smsCode, final String phoneNumber,
                              final String password, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "?smsCode=" + smsCode + "&phoneNumber=" + phoneNumber +
                            "&password=" + password;
                    RequestBody body = RequestBody.create(URLENCODED, url);
                    Request request = new Request.Builder().url(web + "/users")
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get
    public void checkExistence(final String phoneNumber, final int type,
                               final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/check-existence";
                    System.out.println(url);
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("type", type);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 设置头像
    public void setAvatar(final String phoneNumber, final int type, final String
            intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = web + "/auth/sms-code?phoneNumber=" + phoneNumber;
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(URLENCODED, url);
                    Request request = new Request.Builder().url(url).post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println(action);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("type", type);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 发送验证码
    public void getSmsCode(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
