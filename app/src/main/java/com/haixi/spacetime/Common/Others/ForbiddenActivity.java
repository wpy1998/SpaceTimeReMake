package com.haixi.spacetime.Common.Others;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haixi.spacetime.Common.BasicActivity;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.ActivityForbiddenBinding;

@Route(path = "/spaceTime/forbidden")
public class ForbiddenActivity extends BasicActivity {
    ActivityForbiddenBinding binding;
    public static String logout = "logout:\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forbidden);

        binding.forbindenLogout.setText(logout);
    }
}
