package com.haixi.spacetime.Entity;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.haixi.spacetime.Entity.BitmapUtils.compressImageUpload;
import static com.haixi.spacetime.Entity.BitmapUtils.deleteCacheFile;
import static com.haixi.spacetime.Entity.Cookies.accessKeyId;
import static com.haixi.spacetime.Entity.Cookies.accessKeySecret;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.securityToken;
import static com.haixi.spacetime.Entity.Cookies.token;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.password;
import static com.haixi.spacetime.Entity.Cookies.newPassword;
import static com.haixi.spacetime.ForbiddenActivity.logout;

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

    //get获取访问图片的授权信息
    public void getImageToken(final int type, final String intentAction){
        if (!securityToken.equals("") && !accessKeyId.equals("") && accessKeySecret.equals("")){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("Authorization", token)
                            .url(web + "/auth/token/image").build();
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
    public void addUserToCircle(final int circleId, final String qrCodeContext,
                                final int type, final String intentAction){
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
    public void addDynamicToCircle(final int socialCircleId, final List<String> fileNames,
                                   final String content, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpLoggingInterceptor logInterceptor = new
                            HttpLoggingInterceptor(new HttpLogger());
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addNetworkInterceptor(logInterceptor)
                            .addInterceptor(new LogInterceptor())
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(180, TimeUnit.SECONDS)
                            .readTimeout(180, TimeUnit.SECONDS)
                            .build();
                    MultipartBody.Builder body = new MultipartBody.Builder()
                            .addFormDataPart("content", content).setType(MultipartBody.FORM);
                    for (int i = 0; i < fileNames.size(); i++){
                        File file = new File(fileNames.get(i));
                        body.addFormDataPart("images", file.getName(),
                                RequestBody.create(MediaType.parse("*/*"), file));
                    }
                    MultipartBody multipartBody = body.build();
                    Request request = new Request.Builder().url(web + "/circles/" + socialCircleId
                            + "/activities")
                            .addHeader("Authorization", token).post(multipartBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println("failure***********************************");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String action = response.toString();
                            sendBroadcast(action, type, intentAction);
                        }
                    });
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

    //get获取动态的评论
    public void getDynamicComments(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/activities/" + id + "/comment";
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

    //post评论动态
    public void commentInDynamic(final int id, final String comment, final int type,
                                 final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("comment", comment).build();
                    Request request = new Request.Builder().url(web + "/circles/activities/" + id
                            + "/comment")
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

    //post点赞动态
    public void likeDynamic(final int id, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder().build();
                    Request request = new Request.Builder().url(web + "/circles/activities/" + id
                            + "/like")
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

    //get获取查询的用户的动态
    public void getUserDynamic(final String phoneNumber, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/circles/activities/members/" + phoneNumber;
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

    //get获取用户所属的社交圈的基础信息
    public void getUserCircles(final String phoneNumber, final int type, final String intentAction){
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

    //get获取用户在特定社交圈中可见的社交圈动态
    public void getOwnerOneVisibleCircleDynamic(final int socialCircleId, final int type,
                                       final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/circles/" + socialCircleId
                            + "/visible-activities";
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

    //get获取所有用户加入的社交圈中可见的动态
    public void getOwnerAllVisibleCircleDynamic(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/circles/visible-activities";
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

    //get获取用户关注者的动态
    public void getOwnerFollowUser(final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/following/activities";
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
                    OkHttpClient client = new OkHttpClient.Builder().build();
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
    public void setAvatar(final String picturePath, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String path = compressImageUpload(picturePath);
                    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    File file = new File(path);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addNetworkInterceptor(logInterceptor)
                            .addInterceptor(new LogInterceptor())
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(180, TimeUnit.SECONDS)
                            .readTimeout(180, TimeUnit.SECONDS)
                            .build();
                    RequestBody formBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("avatar", file.getName(),
                                    RequestBody.create(MediaType.parse("*/*"), file))
                            .build();
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber +
                            "/avatar").addHeader("Authorization", token).put(formBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            deleteCacheFile();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String action = response.body().string();
                            sendBroadcast(action, type, intentAction);
                            deleteCacheFile();
                        }
                    });
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

    //get查看是否关注某人
    public void  isFollowingUser(final String phoneNumber, final int type, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = web + "/users/" + phoneNumber + "/following";
                    Request request = new Request.Builder().addHeader("Authorization", token)
                            .url(url).build();
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
