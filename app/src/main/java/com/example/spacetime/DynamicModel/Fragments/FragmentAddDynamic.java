package com.example.spacetime.DynamicModel.Fragments;

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

import com.example.spacetime.R;
import com.example.spacetime.databinding.FragmentAddDynamicBinding;

import static com.example.spacetime.Others.Settings.adaptView;
import static com.example.spacetime.Others.Settings.getPx;
import static com.example.spacetime.Others.Settings.setHW;
import static com.example.spacetime.Others.Settings.setTextSize;

public class FragmentAddDynamic extends Fragment implements View.OnClickListener {
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
        adaptView(back, 19, 16, 0,26, false);

        save.getLayoutParams().height = getPx(25);
        adaptView(save, 0, 16, 35, 25, false);
        setTextSize(save, 18);

        adaptView(binding.fragmentAddDynamicContent, 29, 25, 29, 25, false);
        setTextSize(binding.fragmentAddDynamicContent,16);

        setHW(binding.fragmentAddDynamicImage, 80, 80);
        adaptView(binding.fragmentAddDynamicImage,29, 0, 0, 10, false);

        binding.fragmentAddDynamicChoose.getLayoutParams().height = getPx(22);
        adaptView(binding.fragmentAddDynamicChoose, 29, 0,0,0,false);
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
