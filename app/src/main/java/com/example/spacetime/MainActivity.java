package com.example.spacetime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.Components.BasicActivity;
import com.example.spacetime.TopicModel.Fragments.FragmentTopic;
import com.example.spacetime.UserModel.Fragments.FragmentUser;
import com.example.spacetime.databinding.ActivityMainBinding;

import static com.example.spacetime.Components.Settings.adaptView;
import static com.example.spacetime.Components.Settings.getPx;
import static com.example.spacetime.Components.Settings.setHW;

@Route(path = "/spaceTime/main")
public class MainActivity extends BasicActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        closeL_R_W();
        activityList1.add(this);
        init();

        binding.mainConversation.setOnClickListener(this);
        binding.mainPersonal.setOnClickListener(this);
        binding.mainBrowser.setOnClickListener(this);

        binding.mainBrowser.performClick();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_conversation:
                binding.mainConversation.setImageResource(R.drawable.ic_talk_lighting);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person);
                replaceFragment(new Fragment());
                break;
            case R.id.main_personal:
                binding.mainConversation.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth);
                binding.mainPersonal.setImageResource(R.drawable.person_lighting);
                replaceFragment(new FragmentUser());
                break;
            case R.id.main_browser:
                binding.mainConversation.setImageResource(R.drawable.ic_talk);
                binding.mainBrowser.setImageResource(R.drawable.ic_earth_lighting);
                binding.mainPersonal.setImageResource(R.drawable.person);
                replaceFragment(new FragmentTopic());
                break;
            default:
                break;
        }
    }

    private void init(){
        binding.mainPartView.getLayoutParams().height = getPx(40);

        setHW(binding.mainBrowser, 30, 30);
        adaptView(binding.mainBrowser, 5, 5, 5, 5, true);

        setHW(binding.mainConversation, 30, 30);
        adaptView(binding.mainConversation, 5, 5, 5, 5, true);

        setHW(binding.mainPersonal, 30, 30);
        adaptView(binding.mainPersonal, 5, 5, 5, 5, true);
    }
}