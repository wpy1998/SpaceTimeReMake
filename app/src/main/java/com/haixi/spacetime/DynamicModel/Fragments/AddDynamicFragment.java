package com.haixi.spacetime.DynamicModel.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.BitmapUtils;
import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentAddDynamicBinding;
import com.yanzhenjie.album.Album;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.getPx;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;

public class AddDynamicFragment extends BasicFragment implements View.OnClickListener {
    private FragmentAddDynamicBinding binding;
    private ImageView back;
    private TextView save;
    private final String intentAction = "com.haixi.spacetime.DynamicModel.Fragments" +
            ".AddDynamicFragment";
    private Circle circle;
    private final int intentAction_getUserCircles = 1, intentAction_circleName = 2;
    private int imageNumber = 0;
    private List<String> picturePaths;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_dynamic,
                null, false);
        okHttpAction = new OkHttpAction(getContext());
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);
        okHttpAction.getUserCircles(phoneNumber,intentAction_getUserCircles, intentAction);
        circle = new Circle();
        circle.id = -1;
        picturePaths = new ArrayList<>();

        save = binding.getRoot().findViewById(R.id.fragmentAddDynamic_save);
        back = binding.getRoot().findViewById(R.id.fragmentAddDynamic_back);
        drawView();
        binding.fragmentAddDynamicP1.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                // 判断是否成功。 // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                picturePaths = pathList;
                imageNumber = pathList.size();
                FileOperation fileOperation = new FileOperation(getContext());
                Bitmap bitmap = fileOperation.cutBitmap(BitmapUtils.getImage(pathList.get(0)));
                if (imageNumber >= 1)
                    binding.fragmentAddDynamicP1
                            .setImageBitmap(bitmap);
                else binding.fragmentAddDynamicP1.setImageResource(R.drawable.ic_picture);
                setHW(binding.fragmentAddDynamicView, 345, 345);
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            // 用户取消选择。
            // 根据需要提示用户取消了选择。
            binding.fragmentAddDynamicP1.setImageResource(R.drawable.ic_picture);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentAddDynamic_back:
                getActivity().finish();
                break;
            case R.id.fragmentAddDynamic_save:
                if (binding.fragmentAddDynamicContent.getText().toString().equals("")){
                    Toast.makeText(getContext(), "发布信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (circle.id == -1){
                    Toast.makeText(getContext(), "请选择发布的圈子", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "动态已发布", Toast.LENGTH_SHORT).show();
                if(picturePaths.size() == 0){
                    okHttpAction.addDynamicToCircle(circle.id,
                            binding.fragmentAddDynamicContent.getText().toString(),
                            0, intentAction);
                }else {
                    okHttpAction.addDynamicToCircle(circle.id, picturePaths,
                            binding.fragmentAddDynamicContent.getText().toString(),
                            0, intentAction);
                }
                getActivity().finish();
                break;
            case R.id.fragmentAddDynamic_p1:
                Album.startAlbum(this, 100, 1);
                break;
            default:
                break;
        }
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(), data;
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            switch (type){
                case intentAction_getUserCircles:
                    data = intent.getStringExtra("data");
                    try {
                        JSONObject object = new JSONObject(data);
                        String circles = object.getString("data");
                        JSONArray array = new JSONArray(circles);
                        for (int i = 0; i < array.length(); i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            Circle circle = new Circle();
                            circle.name = jsonObject.getString("name");
                            circle.id = jsonObject.getInt("id");
                            TagComponent tagComponent = new TagComponent(getContext(), circle, i);
                            tagComponent.setIntent(intentAction, intentAction_circleName);
                            binding.fragmentAddDynamicCircle
                                    .addView(tagComponent);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case intentAction_circleName:
                    circle.name = intent.getStringExtra("name");
                    circle.id = intent.getIntExtra("circleId", -1);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawView() {
        setHW(back, 24, 24);
        setMargin(back, 15, 16, 0,0, false);

        save.getLayoutParams().height = getPx(25);
        setMargin(save, 0, 16, 15, 0, false);
        setTextSize(save, 18);

        setMargin(binding.fragmentAddDynamicContent, 15, 25,
                15, 15, false);
        setTextSize(binding.fragmentAddDynamicContent,16);

        setHW(binding.fragmentAddDynamicView, 117, 117);
        setMargin(binding.fragmentAddDynamicView, 15, 0,
                15, 10, false);

        binding.fragmentAddDynamicChoose.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentAddDynamicChoose, 15, 20, 0,
                0, false);
        setTextSize(binding.fragmentAddDynamicChoose, 16);

        setMargin(binding.fragmentAddDynamicScroll, 10, 0,
                15, 0, false);
    }
}
