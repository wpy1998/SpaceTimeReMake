package com.example.spacetime.Login_and_Register.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.Others.FileOperation;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentCompleteMessageBinding;

import java.util.Calendar;

public class FragmentCompleteMessage extends Fragment implements View.OnClickListener {
    private FragmentCompleteMessageBinding binding;
    private int genderWhich;
    private Calendar calendar;

    private TextView gender, time;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_message, null, false);
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
                ARouter.getInstance()
                        .build("/spaceTime/welcome")
                        .navigation();
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
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                Toast.makeText(getContext(), "waiting for coming true", Toast.LENGTH_SHORT).show();
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
}
