package com.haixi.spacetime.UserModel.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.haixi.spacetime.Common.FileOperation;
import com.haixi.spacetime.R;
import com.haixi.spacetime.UserModel.Components.EditUserComponent;
import com.haixi.spacetime.UserModel.UserActivity;
import com.haixi.spacetime.databinding.FragmentEditUserBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Common.Settings.setH;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.resultCode;
import static com.haixi.spacetime.Entity.User.setMessage;

public class EditUserFragment extends BasicFragment implements View.OnClickListener {
    private FragmentEditUserBinding binding;
    private TextView save, title;
    private ImageView back;
    private EditUserComponent userImage, userName, userAge, userArea, userSign;
    private final String intentAction = "com.haixi.spacetime.UserModel.Fragments.EditUserFragment";
    private final int intentAction_changeUserMessage = 1, intentAction_setAvatar = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_user,
                null, false);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

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

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, 2);
            }
        });

        refresh();
        return binding.getRoot();
    }

    String picturePath = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("resultCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == 2 && resultCode == -1){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //获取选择照片的数据视图
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            System.out.println("picturePath = " + picturePath);
            cursor.close();
            FileOperation.setBitmap(picturePath);
            userImage.setImage(FileOperation.bitmap);
        }
        refresh();
    }

    @Override
    public void refresh() {
        userName.setContent(owner.userName);
        userAge.setContent(owner.birthday);
        userArea.setContent(owner.comeFrom);
        userSign.setContent(owner.signature);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentEditUser_back:
                getActivity().finish();
                break;
            case R.id.fragmentEditUser_save:
                okHttpAction = new OkHttpAction(getContext());
                okHttpAction.changeUserMessage(intentAction_changeUserMessage, intentAction);
                if (picturePath != null){
                    okHttpAction.setAvatar(picturePath, intentAction_setAvatar, intentAction);
                    picturePath = null;
                }
                break;
            default:
                break;
        }
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_changeUserMessage:
                    data = intent.getStringExtra("data");
                    try{
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        setMessage(data1, owner);
                        Toast.makeText(getContext(), "用户信息已保存", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case intentAction_setAvatar:
                    data = intent.getStringExtra("data");
                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
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
