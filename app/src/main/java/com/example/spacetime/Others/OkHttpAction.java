package com.example.spacetime.Others;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpAction {
    public final String web = "http://59.110.172.61";
    private static final MediaType JSON= MediaType.parse("application/json;charset=utf-8");
    private static final MediaType URLENCODED = MediaType
            .parse("application/x-www-form-urlencoded; charset=UTF-8");

    private Context context;
    public OkHttpAction(Context context){
        this.context = context;
    }


    //authorization-controller************************
    //post 发送验证码
    public void getSmsCode(final String phoneNumber, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", phoneNumber).build();
                    Request request = new Request.Builder().url(web + "/auth/sms-code")
                            .post(body).build();
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

    //get 检查验证码是否正确
    public void checkSmsCode(final String phoneNumber, final String smsCode, final int type,
                             final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = web + "/auth/sms-code/check?phoneNumber=" + phoneNumber +
                            "&smsCode=" + smsCode;
                    OkHttpClient client = new OkHttpClient();
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

    //post 使用密码验证登陆
    public void authorizeWithPassword(final String phoneNumber, final String password, final int type,
                                final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().add("phoneNumber", phoneNumber)
                            .add("password", password).build();
                    Request request = new Request.Builder()
                            .url(web + "/auth/token/authorize-with-password").post(body).build();
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


    //authorization-controller************************
    //post 注册用户
    public void registerUsers(final String smsCode, final String phoneNumber, final String password,
                              final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().add("smsCode", smsCode)
                            .add("phoneNumber", phoneNumber)
                            .add("password", password).build();
                    Request request = new Request.Builder().url(web + "/users")
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    intent.putExtra("type", type);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 检查手机是否被使用
    public void checkExistence(final String phoneNumber, final int type,
                               final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/check-existence";
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
                    Request request = new Request.Builder().url(url).put(body).build();
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

    //put 修改用户信息
    public void editUserMessage(final String token, final String phoneNumber, final String userName,
                                final String gender, final String birthday, final String comeFrom,
                                final String profession, final String school, final String major,
                                final String interests, final String labels, final int type,
                                final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("username", userName)
                            .add("gender", gender)
                            .add("birthday", birthday)
                            .add("comeFrom", comeFrom)
                            .add("profession", profession)
                            .add("school", school)
                            .add("major", major)
                            .add("interests", interests)
                            .add("labels", labels).build();
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber)
                            .addHeader("Authorization", token).put(body).build();
                    System.out.println(body.toString());
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println("************************" + action);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    intent.putExtra("type", type);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
