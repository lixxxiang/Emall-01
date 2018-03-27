package com.example.emall_ec.main.classify.data;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_ec.R;

import java.util.List;

/**
 * Created by lixiang on 2018/3/27.
 */

public class VideoClassifyAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

    private GridLayoutManager glm = null;
    public VideoClassifyAdapter(@LayoutRes int layoutResId, @Nullable List<Model> data, GridLayoutManager glm) {
        super(layoutResId, data);
        this.glm = glm;
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
        helper.setText(R.id.classify_video_title, item.getPrice());
        helper.setText(R.id.classify_video_time, item.getTime());
        helper.setText(R.id.classify_video_price, item.getPrice());


        Glide.with(this.mContext)
                .load(item.getImageUrl())
                .into((ImageView) helper.getView(R.id.classify_video_image_iv));
    }
}


