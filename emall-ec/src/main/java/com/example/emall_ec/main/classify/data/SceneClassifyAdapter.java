package com.example.emall_ec.main.classify.data;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_ec.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lixiang on 2018/3/7.
 */

public class SceneClassifyAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

    private GridLayoutManager glm = null;
    public SceneClassifyAdapter(@LayoutRes int layoutResId, @Nullable List<Model> data, GridLayoutManager glm) {
        super(layoutResId, data);
        this.glm = glm;
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
        ViewGroup.LayoutParams parm = helper.getView(R.id.item_classify_iv).getLayoutParams();
        parm.height = glm.getWidth() / 2 - helper.getView(R.id.item_classify_iv).getPaddingLeft();
        helper.setText(R.id.item_classify_price_tv, item.getPrice());
        helper.setText(R.id.item_classify_time_tv, item.getTime());

        Glide.with(this.mContext)
                .load(item.getImageUrl())
                .into((ImageView) helper.getView(R.id.item_classify_iv));
    }
}

