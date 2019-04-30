package com.haixi.spacetime.UserModel.Fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Components.EditUserComponent;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentEditUserBinding;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.resultCode;

public class EditUserFragment extends BasicFragment implements View.OnClickListener {
    private FragmentEditUserBinding binding;
    private TextView save, title;
    private ImageView back;
    private EditUserComponent userImage, userName, userAge, userArea, userSign;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_user,
                null, false);
        okHttpAction = new OkHttpAction(getContext());

        title = binding.getRoot().findViewById(R.id.fragmentEditUser_title);
        save = binding.getRoot().findViewById(R.id.fragmentEditUser_save);
        back = binding.getRoot().findViewById(R.id.fragmentEditUser_back);
        userImage = new EditUserComponent(getContext());
        userName = new EditUserComponent(getContext());
        userAge = new EditUserComponent(getContext());
        userArea = new EditUserComponent(getContext());
        userSign = new EditUserComponent(getContext());
        binding.fragmentEditUserMainView.addView(userImage);
        binding.fragmentEditUserMainView.addView(userName);
        binding.fragmentEditUserMainView.addView(userAge);
        binding.fragmentEditUserMainView.addView(userArea);
        binding.fragmentEditUserMainView.addView(userSign);
        drawView();

        binding.getRoot().findViewById(R.id.fragmentEditUser_back)
                .setOnClickListener(this);
        binding.getRoot().findViewById(R.id.fragmentEditUser_save)
                .setOnClickListener(this);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("path", "editName");
                intent.putExtra("resultCode", resultCode);
                startActivityForResult(intent, resultCode);
            }
        });

        userSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("path", "editSign");
                intent.putExtra("resultCode", resultCode);
                startActivityForResult(intent, resultCode);
            }
        });

        refresh();
        return binding.getRoot();
    }

    @Override
    public void refresh() {
        userName.setContent(owner.userName);
        userAge.setContent(owner.birthday);
        userArea.setContent(owner.comeFrom);
        userSign.setContent(owner.signature);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentEditUser_back:
                getActivity().finish();
                break;
            case R.id.fragmentEditUser_save:
                UserActivity userActivity = (UserActivity) getActivity();
                String intentAction1 = userActivity.getIntentAction();
                okHttpAction.changeUserMessage(0, intentAction1);
                Toast.makeText(getContext(), "用户信息已保存", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void drawView() {
        setH(binding.getRoot().findViewById(R.id.fragmentEditUser_line0), 2);

        setHW(back, 24, 24);
        setMargin(back, 13, 10, 11, 0, false);

        setTextSize(title,18);
        title.getLayoutParams().height = getPx(26);
        setMargin(title, 0, 11, 0, 8, false);

        save.getLayoutParams().height = getPx(23);
        setMargin(save, 0, 11, 23, 11, false);
        setTextSize(save, 16);

        userImage.setTitle("头像");
        userImage.setImage(R.drawable.jack);
        userImage.drawComponent();

        userName.setTitle("昵称");
        userName.setContent("Hayton");
        userName.drawComponent();

        userAge.setTitle("年龄");
        userAge.setContent("21岁");
        userAge.drawComponent();

        userArea.setTitle("地区");
        userArea.setContent("北京");
        userArea.drawComponent();

        userSign.setTitle("签名");
        userSign.setContent("我就是我是不一样的烟火");
        userSign.drawComponent();
    }
}
