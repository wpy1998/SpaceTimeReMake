package com.haixi.spacetime.DynamicModel.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haixi.spacetime.Common.BasicFragment;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.FragmentAddDynamicBinding;

import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class AddDynamicFragment extends BasicFragment implements View.OnClickListener {
    private FragmentAddDynamicBinding binding;

    private ImageView back;
    private TextView save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_dynamic,
                null, false);
        save = binding.getRoot().findViewById(R.id.fragmentAddDynamic_save);
        back = binding.getRoot().findViewById(R.id.fragmentAddDynamic_back);

        drawView();
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        return binding.getRoot();
    }

    private void drawView() {
        setHW(back, 24, 24);
        setMargin(back, 19, 16, 0,26, false);

        save.getLayoutParams().height = getPx(25);
        setMargin(save, 0, 16, 35, 25, false);
        setTextSize(save, 18);

        setMargin(binding.fragmentAddDynamicContent, 29, 25, 29, 25, false);
        setTextSize(binding.fragmentAddDynamicContent,16);

        setHW(binding.fragmentAddDynamicImage, 80, 80);
        setMargin(binding.fragmentAddDynamicImage,29, 0, 0, 10, false);

        binding.fragmentAddDynamicChoose.getLayoutParams().height = getPx(22);
        setMargin(binding.fragmentAddDynamicChoose, 29, 0,0,0,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragmentAddDynamic_back:
                getActivity().finish();
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
