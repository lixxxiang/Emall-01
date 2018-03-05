package com.example.emall_ec.main.order.state;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lixiang on 2018/3/5.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderModel, BaseViewHolder> {

    public OrderAdapter(@LayoutRes int layoutResId, @Nullable List<OrderModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderModel item) {
        
    }
}
