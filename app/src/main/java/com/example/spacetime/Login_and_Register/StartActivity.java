package com.example.spacetime.Login_and_Register;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.BasicActivity;
import com.example.spacetime.R;
import com.example.spacetime.databinding.ActivityStartBinding;

@Route(path = "/spaceTime/start")
public class StartActivity extends BasicActivity implements View.OnClickListener {
    private ActivityStartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start);

        activityList0.add(this);

        binding.startLogin.setOnClickListener(this);
        binding.startRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_login:
                ARouter.getInstance()
                        .build("/spaceTime/login")
                        .withString("path", "loginBegin")
                        .navigation();
                break;
            case R.id.start_register:
                ARouter.getInstance()
                        .build("/spaceTime/register")
                        .withString("path", "registerBegin")
                        .navigation();
                break;
            default:
                break;
        }
    }
}
