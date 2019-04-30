package com.haixi.spacetime.UserModel.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haixi.spacetime.CircleImageView;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.DynamicModel.Fragments.SocialFragment;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.MainActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentUserBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

@SuppressLint("ValidFragment")
public class UserFragment extends BasicFragment implements View.OnClickListener {
    private FragmentUserBinding binding;

    private TextView dynamic, message, name, ageLocation, setting;
    private LinearLayout userView, chooseView;
    private ImageView gender;
    private CircleImageView image;
    private BasicFragment userDynamic;
    private MessageFragment userMessage;

    public User user;
    private boolean isFollow;

    public UserFragment(User user){
        this.user = user;
        if (this.user == null){
            this.user = new User();
        }
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


        userDynamic = new SocialFragment(user);
        userMessage = new MessageFragment(user);

        drawFragment();
        setting.setOnClickListener(this);
        dynamic.setOnClickListener(this);
        message.setOnClickListener(this);

        dynamic.performClick();
        refresh();
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.refresh(3);
    }

    @Override
    public void refresh() {
        if (user.userId != owner.userId){
            setting.setText("未关注");
            isFollow = false;
        }else {
            setting.setText("用户设置");
            name.setText(owner.userName);
            ageLocation.setText(owner.comeFrom);
        }
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
                if (user.userId == owner.userId){
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("path", "setting");
                    startActivityForResult(intent, resultCode);
                }else if(!isFollow){
                    setting.setText("已关注");
                    isFollow = true;
                }else {
                    setting.setText("未关注");
                    isFollow = false;
                }
                break;
            case R.id.fragment_user_dynamic:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlue));
                message.setTextColor(getResources().getColor(R.color.colorBlack));
                replaceFragment(userDynamic);
                break;
            case R.id.fragment_user_message:
                dynamic.setTextColor(getResources().getColor(R.color.colorBlack));
                message.setTextColor(getResources().getColor(R.color.colorBlue));
                replaceFragment(userMessage);
                break;
            default:
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
