package com.example.emall_ec.main.classify.data;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_ec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 2018/3/7.
 */

public class ClassifyAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

    private List<String> imageData = new ArrayList<>();

    public ClassifyAdapter(@LayoutRes int layoutResId, @Nullable List<Model> data) {
        super(layoutResId, data);
//        int size = data.get(0).getData().getSearchReturnDtoList().size();
//        EmallLogger.INSTANCE.d(size);
//        for (int i = 0; i < size; i++) {
//            imageData.add(data.get(0).getData().getSearchReturnDtoList().get(i).getThumbnailUrl());
//        }
//        imageData.add(data.get())
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
//        helper.setText(R.id.item_classify_iv, item.getTitle())
//                .setText(R.id.tv_content, item.getContent())
//                .setImageResource(R.id.iv_img, R.mipmap.ic_launcher);
        Glide.with(this.mContext)
                .load(item.getImageUrl())
                .into((ImageView) helper.getView(R.id.item_classify_iv));
    }
}

