package com.haixi.spacetime.DynamicModel.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.haixi.spacetime.Entity.BasicFragment;
import com.haixi.spacetime.Entity.BitmapUtils;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.DynamicModel.Components.TagComponent;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentAddDynamicBinding;
import com.yanzhenjie.album.Album;

import org.json.JSONArray;
import org.json.JSONObject;

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

        save = binding.getRoot().findViewById(R.id.fragmentAddDynamic_save);
        back = binding.getRoot().findViewById(R.id.fragmentAddDynamic_back);
        drawView();
        binding.fragmentAddDynamicP1.setOnClickListener(this);
        binding.fragmentAddDynamicP2.setOnClickListener(this);
        binding.fragmentAddDynamicP3.setOnClickListener(this);
        binding.fragmentAddDynamicP4.setOnClickListener(this);
        binding.fragmentAddDynamicP5.setOnClickListener(this);
        binding.fragmentAddDynamicP6.setOnClickListener(this);
        binding.fragmentAddDynamicP7.setOnClickListener(this);
        binding.fragmentAddDynamicP8.setOnClickListener(this);
        binding.fragmentAddDynamicP9.setOnClickListener(this);
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
                if (imageNumber >= 1)
                    binding.fragmentAddDynamicP1.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(0)));
                else binding.fragmentAddDynamicP1.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 2)
                    binding.fragmentAddDynamicP2.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(1)));
                else binding.fragmentAddDynamicP2.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 3)
                    binding.fragmentAddDynamicP3.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(2)));
                else binding.fragmentAddDynamicP3.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 4)
                    binding.fragmentAddDynamicP4.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(3)));
                else binding.fragmentAddDynamicP4.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 5)
                    binding.fragmentAddDynamicP5.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(4)));
                else binding.fragmentAddDynamicP5.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 6)
                    binding.fragmentAddDynamicP6.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(5)));
                else binding.fragmentAddDynamicP6.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 7)
                    binding.fragmentAddDynamicP7.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(6)));
                else binding.fragmentAddDynamicP7.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 8)
                binding.fragmentAddDynamicP8.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(7)));
                else binding.fragmentAddDynamicP8.imageView.setImageResource(R.drawable.ic_picture);

                if (imageNumber >= 9)
                    binding.fragmentAddDynamicP9.imageView
                        .setImageBitmap(BitmapUtils.getImage(pathList.get(8)));
                else binding.fragmentAddDynamicP9.imageView.setImageResource(R.drawable.ic_picture);
            }
        }
        else if (resultCode == RESULT_CANCELED) {
            // 用户取消选择。
            // 根据需要提示用户取消了选择。
            binding.fragmentAddDynamicP1.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP2.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP3.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP4.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP5.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP6.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP7.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP8.imageView.setImageResource(R.drawable.ic_picture);
            binding.fragmentAddDynamicP9.imageView.setImageResource(R.drawable.ic_picture);
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
                okHttpAction.addDynamicToCircle(circle.id, picturePaths,
                        binding.fragmentAddDynamicContent.getText().toString(),
                        0, intentAction);
                getActivity().finish();
                break;
            case R.id.fragmentAddDynamic_p1:
                Album.startAlbum(this, 100, 9);
                break;
            case R.id.fragmentAddDynamic_p2:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p3:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p4:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p5:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p6:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p7:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p8:
                binding.fragmentAddDynamicP1.performClick();
                break;
            case R.id.fragmentAddDynamic_p9:
                binding.fragmentAddDynamicP1.performClick();
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
        setMargin(back, 15, 16, 0,26, false);

        save.getLayoutParams().height = getPx(25);
        setMargin(save, 0, 16, 15, 25, false);
        setTextSize(save, 18);

        setMargin(binding.fragmentAddDynamicContent, 15, 25,
                15, 25, false);
        setTextSize(binding.fragmentAddDynamicContent,16);

        setMargin(binding.fragmentAddDynamicImage, 15, 0,
                15, 10, false);

        binding.fragmentAddDynamicChoose.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentAddDynamicChoose, 15, 0, 0,
                0, false);

        setMargin(binding.fragmentAddDynamicScroll, 15, 0,
                15, 0, false);
    }
}
