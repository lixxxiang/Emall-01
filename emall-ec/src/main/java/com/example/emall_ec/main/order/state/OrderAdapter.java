package com.example.emall_ec.main.order.state;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_core.ui.recycler.MultipleViewHolder;
import com.example.emall_ec.R;
import com.example.emall_ec.main.order.state.data.OrderDetail;
import com.example.emall_ec.main.order.state.data.OrderDetailResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 2018/3/5.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderDetailResult, MultipleViewHolder> {

    ArrayList<String> list = new ArrayList<>();

    public OrderAdapter(int layoutResId, @Nullable List<OrderDetailResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MultipleViewHolder helper, OrderDetailResult item) {
        Glide.with(mContext)
                .load(item.getData().getImageDetailUrl())
                .into((ImageView) helper.getView(R.id.item_order_image_iv));
    }
}
//public class OrderAdapter extends BaseQuickAdapter<Model, MultipleViewHolder> {
//
//    ArrayList<String> list = new ArrayList<>();
//
//    public OrderAdapter(int layoutResId, @Nullable List<Model> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(MultipleViewHolder helper, Model item) {
////        ArrayList<String> list = item.getField(OrderFeilds.IMAGEDETAILURL);
//        Glide.with(mContext)
//                .load(item.getImgUrl())
//                .into((ImageView) helper.getView(R.id.item_order_image_iv));
//    }
//
//}