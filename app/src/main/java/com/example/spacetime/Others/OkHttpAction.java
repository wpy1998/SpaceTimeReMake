package com.example.spacetime.Others;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.spacetime.Others.Cookies.token;
import static com.example.spacetime.Others.Cookies.ownerId;
import static com.example.spacetime.Others.Cookies.phoneNumber;
import static com.example.spacetime.Others.Cookies.gender;
import static com.example.spacetime.Others.Cookies.major;
import static com.example.spacetime.Others.Cookies.comeFrom;
import static com.example.spacetime.Others.Cookies.birthday;
import static com.example.spacetime.Others.Cookies.interests;
import static com.example.spacetime.Others.Cookies.profession;
import static com.example.spacetime.Others.Cookies.school;
import static com.example.spacetime.Others.Cookies.userName;
import static com.example.spacetime.Others.Cookies.labels;
import static com.example.spacetime.Others.Cookies.password;
import static com.example.spacetime.Others.Cookies.newPassword;
import static com.example.spacetime.Others.ForbiddenActivity.logout;

public class OkHttpAction {
    public final String web = "http://59.110.172.61";
    private static final MediaType JSON= MediaType.parse("application/json;charset=utf-8");
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

    //put 修改用户信息
    public void editUserMessage(final int type, final String intentAction){
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
                    System.out.println(token);
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
        try {
            JSONObject jsonObject = new JSONObject(data);
            logout = logout + "action=" + intentAction + "\ndata=" + data + "\n\n";
            int status = jsonObject.getInt("status");
            if (status >= 400 && status < 500){
                Toast.makeText(context, "status=" + status +
                        "发送信息有误，请重新发送", Toast.LENGTH_SHORT).show();
                intent.putExtra("type", 0);
                return;
            }else if (status >= 500 && status < 600){
                Toast.makeText(context, "status=" + status +
                        "服务器异常", Toast.LENGTH_SHORT).show();
                intent.putExtra("type", 0);
            }else intent.putExtra("type", type);
            intent.putExtra("data", data);
            context.sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
