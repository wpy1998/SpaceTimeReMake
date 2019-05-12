package com.haixi.spacetime.DynamicModel.Components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haixi.spacetime.Entity.FileOperation;
import com.haixi.spacetime.Entity.OkHttpAction;
import com.haixi.spacetime.Entity.Dynamic;
import com.haixi.spacetime.Entity.User;
import com.haixi.spacetime.R;
import com.haixi.spacetime.databinding.DynamicContentViewBinding;

import java.util.ArrayList;
import java.util.List;

import static com.haixi.spacetime.Entity.BitmapUtils.getImage;
import static com.haixi.spacetime.Entity.Cookies.filePath;
import static com.haixi.spacetime.Entity.Settings.setH;
import static com.haixi.spacetime.Entity.Settings.setMargin;
import static com.haixi.spacetime.Entity.Settings.getPx;
import static com.haixi.spacetime.Entity.Settings.setHW;
import static com.haixi.spacetime.Entity.Settings.setTextSize;

public class DynamicComponent extends LinearLayout implements View.OnClickListener{
    private DynamicContentViewBinding binding;
    private Context context;
    private LinearLayout titleView;
    public TextView userName, circleName;
    public ImageView userImage, setting;
    private OkHttpAction okHttpAction;
    private String[] paths;
    public List<String > popupMenuItemList;
    public Dynamic dynamic;
    public TextView text;

    public DynamicComponent(Context context, Dynamic dynamic, User user){
        super(context);
        paths = dynamic.imageUrls.split(";");
       popupMenuItemList = new ArrayList<>();
        popupMenuItemList.add("选项");
        popupMenuItemList.add("删除");

        this.context = context;
        this.dynamic = dynamic;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dynamic_content_view, this, true);
        titleView = binding.getRoot()
                .findViewById(R.id.dynamicContentView_titleView);
        userImage = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userImage);
        userName = binding.getRoot()
                .findViewById(R.id.dynamicContentView_userName);
        circleName = binding.getRoot()
                .findViewById(R.id.dynamicContentView_circleName);
        setting = binding.getRoot().findViewById(R.id.dynamicContentView_Setting);
        text = binding.dynamicContentViewText;
        drawView();
        refreshData();
        refreshPicture();
        binding.dynamicContentViewLike.setOnClickListener(this);
        binding.dynamicContentViewComment.setOnClickListener(this);
    }

    private void refreshData(){
        if (dynamic.imageId == -1)
            dynamic.imageId = R.drawable.william;
        userImage.setImageResource(dynamic.imageId);
        userName.setText(dynamic.user.userName);
        binding.dynamicContentViewText.setText(dynamic.content);
        circleName.setText(dynamic.circle.name);
        binding.dynamicContentViewCommentNumber.setText("" + dynamic.commentCount);
        binding.dynamicContentViewLikeNumber.setText("" + dynamic.likeCount);
        binding.dynamicContentViewPublishTime.setText(dynamic.publishTime);
        if (dynamic.liked){
            binding.dynamicContentViewLike.
                    setImageResource(R.drawable.ic_likelight);
        }else {
            binding.dynamicContentViewLike.
                    setImageResource(R.drawable.ic_like);
        }
        binding.fragmentDynamicOther.removeView(binding.dynamicContentViewComment);
        binding.fragmentDynamicOther.removeView(binding.dynamicContentViewCommentNumber);
    }

    private void refreshPicture(){
        if (!dynamic.imageUrls.equals("")){
            Bitmap bitmap = getImage(filePath + "Picture/" + dynamic.imageUrls);
            FileOperation fileOperation = new FileOperation(getContext());
            binding.dynamicContentViewImage.setImageBitmap(fileOperation.cutBitmap(bitmap));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamicContentView_like:
                okHttpAction = new OkHttpAction(getContext());
                okHttpAction.likeDynamic(dynamic.dynamicId, 0, "");
                if (dynamic.liked){
                    dynamic.liked = false;
                    (dynamic.likeCount)--;
                }else {
                    dynamic.liked = true;
                    (dynamic.likeCount)++;
                }
                refreshData();
                break;
            default:
                break;
        }
    }

    private void drawView() {
        titleView.getLayoutParams().height = getPx(60);

        setHW(userImage, 45, 45);
        setMargin(userImage, 12, 10, 9, 0, false);

//        userName.getLayoutParams().height = getPx(22);
        setMargin(userName, 0, 10, 0, 5, false);
        setTextSize(userName, 16);

//        circleName.getLayoutParams().height = getPx(20);
        setTextSize(circleName, 14);

        setMargin(setting, 0, 0, 20, 0, false);

        setMargin(binding.dynamicContentViewText, 16, 10, 16,
                7, true);
        setTextSize(binding.dynamicContentViewText, 18);

        setHW(binding.dynamicContentViewLike, 24, 24);
        setMargin(binding.dynamicContentViewLike, 16, 9, 7,
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

        setH(binding.dynamicContentViewPublishTime, 20);
        setMargin(binding.dynamicContentViewPublishTime, 0, 13,
                20, 20, true);
        setTextSize(binding.dynamicContentViewPublishTime, 14);

        if (dynamic.imageUrls.equals("")){
            binding.dynamicContentViewMainView.removeView(binding.dynamicContentViewView);
        }else {
            setHW(binding.dynamicContentViewImage, 375, 375);
            setMargin(binding.dynamicContentViewImage, 0, 10, 0, 0, false);
        }
    }
}
