package com.example.emall_ec.main.order.state;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_core.ui.recycler.MultipleItemEntity;
import com.example.emall_core.ui.recycler.MultipleViewHolder;
import com.example.emall_ec.R;

import java.util.List;

/**
 * Created by lixiang on 2018/3/5.
 */

public class OrderAdapter extends BaseQuickAdapter<MultipleItemEntity, MultipleViewHolder> {

    public OrderAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    public OrderAdapter(@Nullable List<MultipleItemEntity> data) {
        super(data);
    }

    public OrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        Glide.with(mContext)
                .load(item.getField(OrderFeilds.IMAGEDETAILURL))
                .into((ImageView) helper.getView(R.id.item_order_image_iv));
    }
}
