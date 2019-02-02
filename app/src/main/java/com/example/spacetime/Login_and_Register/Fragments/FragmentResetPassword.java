package com.example.spacetime.Login_and_Register.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentResetPasswordBinding;

import static com.example.spacetime.BasicActivity.closeL_R_W;

public class FragmentResetPassword extends Fragment implements View.OnClickListener {
    private FragmentResetPasswordBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, null, false);
        binding.resetPasswordNextPage.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_password_nextPage:
                ARouter.getInstance()
                        .build("/spaceTime/main")
                        .withString("path", "completeMessage")
                        .navigation();
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
