package com.haixi.spacetime.UserModel.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.CircleImageView;
import com.haixi.spacetime.Common.FileOperation;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentUserBinding;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Entity.Cookies.accessKeyId;
import static com.haixi.spacetime.Entity.Cookies.accessKeySecret;
import static com.haixi.spacetime.Entity.Cookies.filePath;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.Cookies.securityToken;
import static com.haixi.spacetime.Entity.Cookies.setImageToken;
import static com.haixi.spacetime.Entity.User.setMessage;

@SuppressLint("ValidFragment")
public class UserFragment extends BasicFragment implements View.OnClickListener {
    private FragmentUserBinding binding;
    private TextView dynamic, message, name, ageLocation, setting;
    private LinearLayout userView, chooseView;
    private ImageView gender;
    private CircleImageView image;

    private UserDynamicFragment userDynamic;
    private MessageFragment userMessage;
    public User user;
    private boolean isFollow;
    private String intentAction = "com.haixi.spacetime.UserModel.Fragments.UserFragment";
    private final int intentAction_getUserMessage = 1, intentAction_isFollowing = 2,
        intentAction_followed = 3, intentAction_getImageToken = 4, intentAction_setImage = 5;

    private FileOperation fileOperation;

    public UserFragment(User user){
        this.user = user;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user,
                null, false);
        intentAction = intentAction + "." + user.phoneNumber;
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        fileOperation = new FileOperation(getContext());
        okHttpAction = new OkHttpAction(getContext());
        okHttpAction.getUserMessage(user.phoneNumber, intentAction_getUserMessage, intentAction);
        okHttpAction.getImageToken(intentAction_getImageToken, intentAction);

        setting = binding.getRoot().findViewById(R.id.fragment_user_setting);
        dynamic = binding.getRoot().findViewById(R.id.fragment_user_dynamic);
        message = binding.getRoot().findViewById(R.id.fragment_user_message);
        name = binding.getRoot().findViewById(R.id.fragment_user_name);
        ageLocation = binding.getRoot().findViewById(R.id.
                fragment_user_age_and_loaction);
        userView = binding.getRoot().findViewById(R.id.fragment_user_userView);
        chooseView = binding.getRoot().findViewById(R.id.fragment_user_choose);
        image = binding.getRoot().findViewById(R.id.fragment_user_image);
        gender = binding.getRoot().findViewById(R.id.fragment_user_gender);

        drawFragment();
        setting.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        message.setOnClickListener(this);
        dynamic.performClick();

        binding.fragmentUserSwipeRefreshLayout
                .setProgressBackgroundColorSchemeColor(R.color.colorWhite);
        binding.fragmentUserSwipeRefreshLayout.setColorSchemeResources(R.color.colorBackGround,
                R.color.colorBlue, R.color.colorWhite);
        binding.fragmentUserSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        okHttpAction.getUserMessage(user.phoneNumber, intentAction_getUserMessage, intentAction);
    }

    @Override
    public void refresh() {
        binding.fragmentUserSwipeRefreshLayout.setRefreshing(false);
        if (user.phoneNumber.equals(phoneNumber)){
            setting.setText("用户设置");
            name.setText(owner.userName);
            ageLocation.setText(owner.comeFrom);
        }else {
            if (user.isFollowing){
                setting.setText("已关注");
            }else {
                setting.setText("未关注");
            }
            name.setText(user.userName);
            ageLocation.setText(user.comeFrom);
            isFollow = false;
        }

        boolean end = fileOperation.isFileExist(user.avatar);
        if (end){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(filePath + user.avatar);
                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                image.setImageBitmap(bitmap);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void switchFragment(BasicFragment fragment){
        if (fragment == null){
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (!userMessage.isAdded()){
            transaction.add(R.id.fragment_user_mainView, userMessage);
        }
        if (!userDynamic.isAdded()){
            transaction.add(R.id.fragment_user_mainView, userDynamic);
        }
        if (fragment == userDynamic){
            transaction.hide(userMessage).show(userDynamic);
        }else {
            transaction.hide(userDynamic).show(userMessage);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_user_setting:
                if (user.phoneNumber.equals(phoneNumber)){
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("path", "setting");
                    startActivityForResult(intent, resultCode);
                }else{
                    okHttpAction.followUser(user.phoneNumber, intentAction_followed, intentAction);
                }
                break;
            case R.id.fragment_user_dynamic:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlue));
                message.setTextColor(getResources().getColor(R.color.colorBlack));
                switchFragment(userDynamic);
                break;
            case R.id.fragment_user_message:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlack));
                message.setTextColor(getResources().getColor(R.color.colorBlue));
                switchFragment(userMessage);
                break;
            default:
                break;
        }
    }

    private Bitmap bitmap;
    private class UserInfoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_getUserMessage:
                    data = intent.getStringExtra("data");
                    try{
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        if (user.phoneNumber.equals(owner.phoneNumber)){
                            setMessage(data1, owner);
                            user = owner;
                        } else {
                            setMessage(data1, user);
                        }
                        userDynamic = new UserDynamicFragment(user);
                        userMessage = new MessageFragment(user);
                        binding.fragmentUserMainView.removeAllViews();
                        dynamic.performClick();
                        if (!user.phoneNumber.equals(owner.phoneNumber)){
                            okHttpAction.isFollowingUser(user.phoneNumber,
                                    intentAction_isFollowing, intentAction);
                            return;
                        }
                        refresh();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case intentAction_isFollowing:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        user.isFollowing = jsonObject.getBoolean("data");
                        refresh();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case intentAction_followed:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String data1 = jsonObject.getString("data");
                        JSONObject object = new JSONObject(data1);
                        user.isFollowing = object.getBoolean("followed");
                        refresh();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;

                case intentAction_getImageToken:
                    data = intent.getStringExtra("data");
                    setImageToken(data);
                    boolean end = fileOperation.isFileExist(user.avatar);
                    if (owner.avatar != "" && end == false){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fileOperation.downloadPicture(accessKeyId, accessKeySecret, securityToken,
                                        owner.avatar);
                                Intent intent1 = new Intent(intentAction);
                                intent1.putExtra("type", intentAction_setImage);
                                getContext().sendBroadcast(intent1);
                            }
                        }).start();
                    }
                    break;

                case intentAction_setImage:
                    refresh();
                    break;

                default:
                    break;
            }
        }
    }

    private void drawFragment() {
        userView.getLayoutParams().height = getPx(118);
        chooseView.getLayoutParams().height = getPx(51);

        setHW(image, 70, 70);
        setMargin(image, 20, 17, 21, 0, false);

        name.getLayoutParams().height = getPx(41);
        setTextSize(name, 24);
        setMargin(name, 0, 17, 6, 7, false);

        setHW(gender, 24, 24);
        setMargin(gender, 0, 25, 0, 69, false);

        setH(ageLocation, 22);
        setMargin(ageLocation, 0, 0, 0, 31, false);
        setTextSize(ageLocation, 16);

        setting.getLayoutParams().height = getPx(33);
        setMargin(setting, 0, 21, 22, 3, false);

        dynamic.getLayoutParams().height = getPx(41);
        setTextSize(dynamic, 16);
    }
}
