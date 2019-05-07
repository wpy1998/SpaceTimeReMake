package com.haixi.spacetime.DynamicModel.Components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.DynamicContentViewBinding;

import org.json.JSONObject;

import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.getPx;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;
import static com.haixi.spacetime.Entity.Cookies.phoneNumber;

public class DynamicComponent extends LinearLayout implements View.OnClickListener {
    private DynamicContentViewBinding binding;
    private Context context;
    private LinearLayout titleView;
    public TextView userName, circleName;
    public ImageView userImage, setting;
    private String intentAction = "com.haixi.spacetime.DynamicModel.Components.DynamicComponent";
    private IntentFilter intentFilter;
    private UserInfoBroadcastReceiver userInfoBroadcastReceiver;
    private OkHttpAction okHttpAction;

    public Dynamic dynamic;
    public TextView text;

    public DynamicComponent(Context context, Dynamic dynamic, User user){
        super(context);
        intentFilter = new IntentFilter();
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter.addAction(intentAction);
        getContext().registerReceiver(userInfoBroadcastReceiver, intentFilter);

        this.context = context;
        this.dynamic = dynamic;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dynamic_content_view, this, true);
        titleView = binding.getRoot()
                .findViewById(R.id.dynamicContentView_titleView);
        userImage = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userImage);
        userName = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userName);
        circleName = binding.getRoot()
                .findViewById(R.id.dynamicContentView_circleName);
        setting = binding.getRoot().findViewById(R.id.dynamicContentView_Setting);
        text = binding.dynamicContentViewText;
        drawView();
        refreshData();

        binding.dynamicContentViewLike.setOnClickListener(this);
        binding.dynamicContentViewComment.setOnClickListener(this);
        if (user != null && user.phoneNumber.equals(phoneNumber)){
            binding.dynamicContentViewMainView.removeView(titleView);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        getContext().unregisterReceiver(userInfoBroadcastReceiver);
        super.onDetachedFromWindow();
    }

    private void refreshData(){
        if (dynamic.imageId == -1)
            dynamic.imageId = R.drawable.william;
        userImage.setImageResource(dynamic.imageId);
        userName.setText(dynamic.user.userName);
        binding.dynamicContentViewText.setText(dynamic.content);
        circleName.setText(dynamic.circle.name);
        binding.dynamicContentViewCommentNumber.setText("" + dynamic.commentCount);
        binding.dynamicContentViewLikeNumber.setText("" + dynamic.likeCount);
        binding.dynamicContentViewPublishTime.setText(dynamic.publishTime);
        if (dynamic.liked){
            binding.dynamicContentViewLike.
                    setImageResource(R.drawable.ic_like_lighting);
        }else {
            binding.dynamicContentViewLike.
                    setImageResource(R.drawable.ic_like);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamicContentView_like:
                okHttpAction = new OkHttpAction(getContext());
                okHttpAction.likeDynamic(dynamic.dynamicId, dynamic.dynamicId, intentAction);
                break;
            default:
                break;
        }
    }

    private class UserInfoBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(intentAction)){
                return;
            }
            int type = intent.getIntExtra("type", 0);
            if (type != dynamic.dynamicId){
                return;
            }
            try {
                String data = intent.getStringExtra("data");
                JSONObject jsonObject = new JSONObject(data);
                String data1 = jsonObject.getString("data");
                JSONObject object = new JSONObject(data1);
                dynamic.liked = object.getBoolean("liked");

                if (dynamic.liked){
                    (dynamic.likeCount)++;
                }else {
                    (dynamic.likeCount)--;
                }
                refreshData();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void drawView() {
        titleView.getLayoutParams().height = getPx(60);

        setHW(userImage, 50, 50);
        setMargin(userImage, 12, 10, 9, 0, false);

        userName.getLayoutParams().height = getPx(22);
        setMargin(userName, 0, 10, 0, 8, false);
        setTextSize(userName, 16);

        circleName.getLayoutParams().height = getPx(20);
        setTextSize(circleName, 14);

        setMargin(setting, 0, 0, 20, 0, false);

        setMargin(binding.dynamicContentViewText, 16, 19, 16,
                7, true);

        setHW(binding.dynamicContentViewLike, 24, 24);
        setMargin(binding.dynamicContentViewLike, 20, 9, 7,
                20, true);

        binding.dynamicContentViewLikeNumber.getLayoutParams().
                height = getPx(20);
        setMargin(binding.dynamicContentViewLikeNumber, 0, 13, 21,
                21, true);
        setTextSize(binding.dynamicContentViewLikeNumber, 14);

        setHW(binding.dynamicContentViewComment, 24, 24);
        setMargin(binding.dynamicContentViewComment, 0, 8, 10,
                21, true);

        binding.dynamicContentViewCommentNumber.getLayoutParams().
                height = getPx(20);
        setMargin(binding.dynamicContentViewCommentNumber, 0, 13,
                20, 20, true);
        setTextSize(binding.dynamicContentViewCommentNumber, 14);

        setH(binding.dynamicContentViewPublishTime, 20);
        setMargin(binding.dynamicContentViewPublishTime, 0, 13,
                20, 20, true);
        setTextSize(binding.dynamicContentViewPublishTime, 14);
    }
}
