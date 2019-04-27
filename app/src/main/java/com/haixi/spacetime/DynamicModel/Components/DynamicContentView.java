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
import android.widget.Toast;

import com.haixi.spacetime.DynamicModel.Entity.Dynamic;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.DynamicContentViewBinding;

import java.util.List;

import static com.haixi.spacetime.Common.Entity.Cookies.ownerId;
import static com.haixi.spacetime.Common.Settings.setMargin;
import static com.haixi.spacetime.Common.Settings.getPx;
import static com.haixi.spacetime.Common.Settings.setHW;
import static com.haixi.spacetime.Common.Settings.setTextSize;

public class DynamicContentView extends LinearLayout
        implements View.OnClickListener {
    private DynamicContentViewBinding binding;
    private Context context;
    private LinearLayout titleView;
    private TextView userName, publishTime;
    private ImageView userImage;
    private boolean isLike = false;
    private int likeNumber, commentNumber;

    public Dynamic dynamic;

    public DynamicContentView(Context context, Dynamic dynamic){
        super(context);
        this.dynamic = dynamic;
        init(context);
        if (dynamic.getUserId() == ownerId){
            binding.dynamicContentViewMainView.removeView(titleView);
        }
    }

    private void init(Context context){
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dynamic_content_view, this, true);
        titleView = binding.getRoot()
                .findViewById(R.id.dynamicContentView_titleView);
        userImage = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userImage);
        userName = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userName);
        publishTime = binding.getRoot()
                .findViewById(R.id.dynamicContentView_publishTime);
        drawView();

        for (String tag: dynamic.tags){
            addTag(tag);
        }
        userImage.setImageResource(dynamic.imageId);
        userName.setText(dynamic.name);
        binding.dynamicContentViewText.setText(dynamic.content);
        publishTime.setText("1天前");

        binding.dynamicContentViewLike.setOnClickListener(this);
        binding.dynamicContentViewComment.setOnClickListener(this);
        binding.dynamicContentViewText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamicContentView_like:
                if (isLike){
                    isLike = false;
                    binding.dynamicContentViewLike.
                            setImageResource(R.drawable.ic_like);
                    likeNumber--;
                    binding.dynamicContentViewLikeNumber.setText("" + likeNumber);
                }else {
                    isLike = true;
                    binding.dynamicContentViewLike.
                            setImageResource(R.drawable.ic_like_lighting);
                    likeNumber++;
                    binding.dynamicContentViewLikeNumber.setText("" + likeNumber);
                }
                break;
            case R.id.dynamicContentView_text:
                break;
            default:
                Toast.makeText(getContext(), "waiting for coming true",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addTag(final String tag){
        TextView textView = new TextView(getContext());
        binding.dynamicContentViewTag.addView(textView);
        textView.setText("#" + tag);
        textView.setTextColor(getResources().getColor(R.color.colorBlue));
        textView.getLayoutParams().height = getPx(20);
        setMargin(textView, 5, 5, 5, 5, false);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tag, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void drawView() {
        titleView.getLayoutParams().height = getPx(60);

        setHW(userImage, 50, 50);
        setMargin(userImage, 12, 10, 9, 0, false);

        userName.getLayoutParams().height = getPx(22);
        setMargin(userName, 0, 10, 0, 8, false);
        setTextSize(userName, 16);

        publishTime.getLayoutParams().height = getPx(20);
        setTextSize(publishTime, 14);

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

        binding.dynamicContentViewTime.getLayoutParams().height = getPx(20);
        setMargin(binding.dynamicContentViewTime, 0, 13, 24,
                20, true);
        setTextSize(binding.dynamicContentViewTime, 14);

        setMargin(binding.dynamicContentViewHorizontalScrollView, 20, 0,
                20, 0, true);
    }
}
