package com.haixi.spacetime.Common;

import android.content.Context;
import android.content.Intent;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.password;
import static com.haixi.spacetime.Entity.Cookies.newPassword;
import static com.haixi.spacetime.Common.Others.ForbiddenActivity.logout;

public class OkHttpAction {
    public final String web = "http://59.110.172.61";
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType URLENCODED = MediaType
            .parse("application/x-www-form-urlencoded; charset=UTF-8");

    private Context context;
    public OkHttpAction(Context context){
        this.context = context;
    }


    //Authorization Controller
    //post 发送验证码
    public void getSmsCode(final int type, final String intentAction){
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
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 检查验证码是否正确
    public void checkSmsCode(final String smsCode, final int type, final String intentAction){
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
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 刷新令牌
    public void refreshToken(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().build();
                    Request request = new Request.Builder().url(web + "/auth/token")
                            .addHeader("Authorization", token).put(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //delete 退出当前账号
    public void exitAccount(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(token);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().build();
                    Request request = new Request.Builder().url(web + "/auth/token")
                            .addHeader("Authorization", token).delete(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 使用密码登陆
    public void authorizeWithPassword(final int type, final String intentAction){
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
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 使用验证码登陆
    public void authorizeWithSmsCode(final String smsCode, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", phoneNumber)
                            .add("smsCode", smsCode).build();
                    Request request = new Request.Builder()
                            .url(web + "/auth/token/authorize-with-sms-code").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }






    //Feedback Collection Controller
    //post 提交反馈信息
    public void feedback(final String feedback, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", phoneNumber)
                            .add("feedback", feedback).build();
                    Request request = new Request.Builder().url(web + "/feedback")
                            .addHeader("Authorization", token).post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }





    //social-circle-controller
    //post创建一个圈子
    public void createCircle(final String circleName, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().add("name", circleName).build();
                    Request request = new Request.Builder().url(web + "/circles/")
                            .addHeader("Authorization", token)
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post将用户添加至圈子中
    public void addUserToCircle(final int circleId, final String phoneNumber, final String
            qrCodeContext, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("qrCodeContext", qrCodeContext)
                            .build();
                    Request request = new Request.Builder().url(web + "/circles/" + circleId +
                            "/members/" + phoneNumber)
                            .addHeader("Authorization", token)
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get获取社交圈的基础信息
    public void getCircleData(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/" + id;
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token).url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get获取某个圈子的动态
    public void getCircleDynamic(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/" + id + "/activities";
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token).url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get获取圈子的所有成员的基础信息
    public void getCircleUserMessage(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/" + id + "/members";
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token).url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get获取社交圈的二维码的内容
    public void getCodeMessage(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/" + id + "/qr-code-context";
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token).url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post在某个圈子中发布动态
    public void addDynamicToCircle(final int socialCircleId, final String content, final int type,
                                   final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("content", content).build();
                    Request request = new Request.Builder().url(web + "/circles/" + socialCircleId
                            + "/activities")
                            .addHeader("Authorization", token).post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get获取用户所属的社交圈的基础信息
    public void getUserCircles(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/circles";
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token).url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }





    //User Controller
    //post 注册用户
    public void registerUsers(final String smsCode, final int type, final String intentAction){
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
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 修改用户信息
    public void changeUserMessage(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addNetworkInterceptor(logInterceptor)
                            .addInterceptor(new LogInterceptor()).build();
                    RequestBody body = new FormBody.Builder()
                            .add("username", owner.userName)
                            .add("gender", owner.gender)
                            .add("birthday", owner.birthday)
                            .add("comeFrom", owner.comeFrom)
                            .add("profession", owner.profession)
                            .add("position", owner.position)
                            .add("school", owner.school)
                            .add("major", owner.major)
                            .add("interests", owner.interests)
                            .add("labels", owner.labels)
                            .add("signature", owner.signature).build();
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber)
                            .addHeader("Authorization", token)
                            .addHeader("Accept","*/*")
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 设置头像
    public void setAvatar(final int type, final String intentAction){
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
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 检查手机是否被使用
    public void checkExistence(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/check-existence";
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 关注某人
    public void followUser(final String userPhoneNumber, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", userPhoneNumber).build();
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber
                            + "/following").addHeader("Authorization", token).post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 重置密码
    public void resetPassword(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phoneNumber", phoneNumber)
                            .add("newPassword", newPassword).build();
                    Request request = new Request.Builder().url(web + "/users/" +
                            phoneNumber + "/password")
                            .addHeader("Authorization", token).put(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 获取用户信息
    public void getUserMessage(final String targetPhoneNumber, final int type,
                               final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(web + "/users/" + targetPhoneNumber)
                            .addHeader("Authorization", token).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    sendBroadcast(action, type, intentAction);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }




    public synchronized void sendBroadcast(String data, int type, String intentAction){
        Intent intent = new Intent(intentAction);
        intent.putExtra("type", type);
        intent.putExtra("data", data);
        logout = logout + "action=" + intentAction + "\ndata=" + data + "\n\n";
        System.out.println(logout);
        context.sendBroadcast(intent);
    }
}
