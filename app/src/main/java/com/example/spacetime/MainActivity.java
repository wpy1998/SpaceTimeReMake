package com.example.spacetime;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.spacetime.databinding.ActivityMainBinding;

@Route(path = "/spaceTime/main")
public class MainActivity extends BasicActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        closeL_R_W();
        Intent intentFront=getIntent();
        String path=intentFront.getStringExtra("path");
        Toast.makeText(MainActivity.this,path,Toast.LENGTH_SHORT).show();
    }
}