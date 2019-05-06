package com.haixi.spacetime.LoginAndRegister.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haixi.spacetime.Common.FileOperation;
import com.haixi.spacetime.Common.OkHttpAction;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.databinding.FragmentCompleteMessageBinding;

import org.json.JSONObject;

import java.util.Calendar;

import static com.haixi.spacetime.Entity.Cookies.bitmap;
import static com.haixi.spacetime.Entity.Cookies.owner;
import static com.haixi.spacetime.Entity.Cookies.setBitmap;
import static com.haixi.spacetime.Entity.User.setMessage;

public class CompleteMessageFragment extends BasicFragment implements View.OnClickListener {
    private FragmentCompleteMessageBinding binding;
    private int genderWhich;
    private Calendar calendar;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "CompleteMessageFragment";
    private final int intentAction_EditUserMessage = 1;

    private TextView gender, time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadCastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        okHttpAction = new OkHttpAction(getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_message,
                null, false);
        binding.fragmentCompleteMessageImage.setOnClickListener(this);
        binding.fragmentCompleteMessageNextPage.setOnClickListener(this);
        binding.fragmentCompleteMessageGender.setOnClickListener(this);
        binding.fragmentCompleteMessageTime.setOnClickListener(this);

        gender=binding.fragmentCompleteMessageGender;
        time=binding.fragmentCompleteMessageTime;
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_complete_message_nextPage:
                owner = new User();
                owner.userName = binding.fragmentCompleteMessageName.getText().toString();
                owner.gender = gender.getText().toString();
                owner.birthday = binding.fragmentCompleteMessageTime.getText().toString();
                okHttpAction.changeUserMessage(intentAction_EditUserMessage, intentAction);
                break;
            case R.id.fragment_complete_message_gender:
                new AlertDialog.Builder(getContext()).setTitle("请选择您的性别").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "男", "女", "保密"}, genderWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                genderWhich = which;
                                if (genderWhich == 2){
                                    gender.setText("保密");
                                }else if (genderWhich == 0){
                                    gender.setText("男");
                                }else if (genderWhich == 1){
                                    gender.setText("女");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.fragment_complete_message_time:
                calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //修改日历控件的年，月，日
                                //这里的year,monthOfYear,dayOfMonth的值
                                // 与DatePickerDialog控件设置的最新值一致
                                year = calendar.get(Calendar.YEAR);
                                month = calendar.get(Calendar.MONTH);
                                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                time.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.fragment_complete_message_image:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("resultCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == 1 && resultCode == -1){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //获取选择照片的数据视图
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println("picturePath = " + picturePath);
            cursor.close();
            setBitmap(picturePath);
            binding.fragmentCompleteMessageImage.setImageBitmap(bitmap);
        }
    }

    private class UserInfoBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_EditUserMessage:
                    String data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        setMessage(data1, owner);
                        ARouter.getInstance()
                                .build("/spaceTime/main")
                                .navigation();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void drawFragment(){

    }
}
