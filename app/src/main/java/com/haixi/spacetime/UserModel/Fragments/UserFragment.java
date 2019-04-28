package com.haixi.spacetime.UserModel.Fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.CircleImageView;
import com.haixi.spacetime.Common.Entity.User;
import com.haixi.spacetime.DynamicModel.Fragments.SocialFragment;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentUserBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Entity.Cookies.ownerId;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

@SuppressLint("ValidFragment")
public class UserFragment extends BasicFragment implements View.OnClickListener {
    private FragmentUserBinding binding;

    private Button setting;
    private TextView dynamic, message, name, ageLocation;
    private LinearLayout userView, chooseView;
    private ImageView gender;
    private CircleImageView image;
    private SocialFragment userDynamic;
    private MessageFragment userMessage;

    private int userId = 0;
    private boolean isFollow;

    public UserFragment(){
        userId = ownerId;
    }

    public UserFragment(int userId){
        this.userId = userId;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user,
                null, false);
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

        User user = new User();
        user.userId = ownerId;
        userDynamic = new SocialFragment(user);
        userMessage = new MessageFragment();
        if (userId != ownerId){
            setting.setText("关注");
            isFollow = false;
        }

        drawFragment();
        setting.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        message.setOnClickListener(this);

        dynamic.performClick();
        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_user_mainView,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_user_setting:
                if (userId == ownerId){
                    ARouter.getInstance()
                            .build("/spaceTime/user")
                            .withString("path", "setting")
                            .navigation();
                }else if(!isFollow){
                    setting.setText("已关注");
                    isFollow = true;
                }else {
                    setting.setText("关注");
                    isFollow = false;
                }
                break;
            case R.id.fragment_user_dynamic:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlue));
                message.setTextColor(getResources().getColor(R.color.colorBlack));
                replaceFragment(userDynamic);
                setting.setText("设置");
                break;
            case R.id.fragment_user_message:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlack));
                message.setTextColor(getResources().getColor(R.color.colorBlue));
                replaceFragment(userMessage);
                if (userId != ownerId){
                    setting.setText("关注");
                    isFollow = false;
                }
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
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
