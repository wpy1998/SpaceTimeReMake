package com.haixi.spacetime.CircleModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;
import com.haixi.spacetime.Entity.BasicActivity;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.Entity.Circle;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityCodeBinding;

import org.json.JSONObject;

@Route(path = "/spaceTime/code")
public class CodeActivity extends BasicActivity {
    private ActivityCodeBinding binding;
    private final String intentAction = "com.haixi.spacetime.CircleModel.CodeActivity";
    private final int intentAction_addToCircle = 1;
    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code);
        userInfoBroadcastReceiver = new UserInfoBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentAction);
        registerReceiver(userInfoBroadcastReceiver, intentFilter);

        Intent intent = new Intent(CodeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);//将扫描出的信息显示出来
            binding.codeText.setText(scanResult);
            try{
                JSONObject jsonObject = new JSONObject(scanResult);
                circle = new Circle();
                String qrContent = jsonObject.getString("QRContent");
                circle.id = jsonObject.getInt("circleId");
                circle.name = jsonObject.getString("circleName");
                okHttpAction = new OkHttpAction(this);
                okHttpAction.addUserToCircle(circle.id, qrContent, intentAction_addToCircle, intentAction);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            finish();
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
            if (type == intentAction_addToCircle){
                data = intent.getStringExtra("data");
                binding.codeText.setText("");
                finish();
                ARouter.getInstance()
                        .build("/spaceTime/circle")
                        .withString("path", "circleMessage")
                        .withString("circleName", circle.name)
                        .withInt("circleId", circle.id)
                        .navigation();
            }
        }
    }
}
