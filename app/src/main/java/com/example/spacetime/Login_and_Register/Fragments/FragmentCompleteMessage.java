package com.example.spacetime.Login_and_Register.Fragments;

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
import com.example.spacetime.Others.FileOperation;
import com.example.spacetime.Others.OkHttpAction;
import com.example.spacetime.R;
import com.example.spacetime.Others.BasicFragment;
import com.example.spacetime.databinding.FragmentCompleteMessageBinding;

import org.json.JSONObject;

import java.util.Calendar;

import static com.example.spacetime.Others.Owner.birthday;
import static com.example.spacetime.Others.Owner.comeFrom;
import static com.example.spacetime.Others.Owner.interests;
import static com.example.spacetime.Others.Owner.labels;
import static com.example.spacetime.Others.Owner.major;
import static com.example.spacetime.Others.Owner.phoneNumber;
import static com.example.spacetime.Others.Owner.profession;
import static com.example.spacetime.Others.Owner.school;
import static com.example.spacetime.Others.Owner.setMessage;
import static com.example.spacetime.Others.Owner.token;

public class FragmentCompleteMessage extends BasicFragment implements View.OnClickListener {
    private FragmentCompleteMessageBinding binding;
    private int genderWhich;
    private Calendar calendar;
    private final String intentAction = "com.example.spacetime.Login_and_Register.Fragments." +
            "FragmentCompleteMessage";
    private final int intentAction_EditUserMessage = 1;
    OkHttpAction okHttpAction;
    private MyBroadcastReceiver myBroadcastReceiver;

    private TextView gender, time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(myBroadcastReceiver, intentFilter);

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
    public void onDestroyView() {
        getContext().unregisterReceiver(myBroadcastReceiver);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_complete_message_nextPage:
                okHttpAction.editUserMessage(token, phoneNumber, binding.fragmentCompleteMessageName.
                                getText().toString(), gender.getText().toString(), birthday, comeFrom,
                        profession, school, major, interests, labels, intentAction_EditUserMessage,
                        intentAction);
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
                                //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
                                year = calendar.get(Calendar.YEAR);
                                month = calendar.get(Calendar.MONTH);
                                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                time.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
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
        System.out.println("requestCode = " + requestCode + ", resultCode = " + resultCode);
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
            FileOperation.setBitmap(picturePath);
            binding.fragmentCompleteMessageImage.setImageBitmap(FileOperation.bitmap);
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_EditUserMessage:
                    String data = intent.getStringExtra("data");
                    System.out.println(token + "**********************" + data);
                    try {
                        JSONObject object = new JSONObject(data);
                        String data1 = object.getString("data");
                        setMessage(data1);
                        ARouter.getInstance()
                                .build("/spaceTime/welcome")
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
}
