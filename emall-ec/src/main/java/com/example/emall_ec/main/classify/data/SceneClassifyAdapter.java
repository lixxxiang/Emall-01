package com.example.emall_ec.main.classify.data;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
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

        helper.setText(R.id.item_classify_time_tv, "拍摄时间：" + item.getTime());

        if (!item.getProductType().equals("3")){
            helper.setText(R.id.item_classify_price_tv, "¥"+ item.getPrice());
            helper.getView(R.id.item_classify_video_mask_rl).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_classify_video_playbtn_rl).setVisibility(View.INVISIBLE);
            helper.getView(R.id.classify_duration).setVisibility(View.INVISIBLE);

            helper.getView(R.id.item_classify_video_title_tv).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_classify_video_price_tv).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_classify_price_tv).setVisibility(View.VISIBLE);
        }else{
            helper.setText(R.id.item_classify_video_title_tv, item.getTitle());
            helper.setText(R.id.item_classify_video_price_tv, "¥"+ item.getPrice());
            helper.getView(R.id.item_classify_video_mask_rl).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_classify_video_playbtn_rl).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_classify_price_tv).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_classify_video_price_tv).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_classify_video_title_tv).setVisibility(View.VISIBLE);
            helper.getView(R.id.classify_duration).setVisibility(View.VISIBLE);
            helper.setText(R.id.classify_duration, "时长："+ item.getDuration());

        }
        Glide.with(this.mContext)
                .load(item.getImageUrl())
                .into((ImageView) helper.getView(R.id.item_classify_iv));
    }
}

