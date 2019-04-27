package com.haixi.spacetime.CircleModel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;
import com.haixi.spacetime.Common.Components.BasicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityCodeBinding;

@Route(path = "/spaceTime/code")
public class CodeActivity extends BasicActivity {
    private ActivityCodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code);

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
        }
    }
}
