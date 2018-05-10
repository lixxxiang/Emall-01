package com.example.emall_ec.main.order.state.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_ec.R;
import com.example.emall_ec.main.order.state.data.OrderListModel;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.emall_ec.main.order.state.adapter.AllListAdapter.programArray;
import static com.example.emall_ec.main.order.state.adapter.AllListAdapter.stateArray;
import static com.example.emall_ec.main.order.state.adapter.AllListAdapter.typeArray;

/**
 * Created by lixiang on 2018/3/6.
 */

public class All2RecyclerViewAdapter extends BaseQuickAdapter<OrderListModel, BaseViewHolder> {

    public All2RecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<OrderListModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListModel item) {

        helper.setText(R.id.item_order_orderid_tv, String.format(this.mContext.getString(R.string.orderId), item.getOrderId()));
        helper.setText(R.id.item_order_title_tv, typeArray[item.getType()]);
        if (item.getType() == 2) {
            helper.setText(R.id.item_order_time_tv, "类型：" + programArray[Integer.parseInt(item.getProductType())]);
        } else {
            if (item.getCenterTime() == null)
                helper.setText(R.id.item_order_time_tv, timeFormat(item.getStartTime()));
            else
                helper.setText(R.id.item_order_time_tv, timeFormat(item.getCenterTime()));
        }
        helper.setText(R.id.item_order_price_tv, String.format("¥%s", new DecimalFormat("######0.00").format(item.getPayment())));
        helper.setText(R.id.item_order_state_tv, stateFormat(item.getState(), item.getPlanCommitTime()));

        if (item.getImageDetailUrl() == null)
            helper.setImageResource(R.id.item_order_image_iv, R.drawable.program);
        else
            Glide.with(this.mContext)
                    .load(item.getImageDetailUrl())
                    .into((ImageView) helper.getView(R.id.item_order_image_iv));

        buttonFormat(item.getState(), (Button) helper.getView(R.id.item_order_btn));
        helper.addOnClickListener(R.id.item_order_btn);
    }

    private void buttonFormat(int state, Button btn) {
        if (state == 2) {
            btn.setText("去付款");
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.order_btn_shape_red);
            btn.setBackgroundColor(Color.parseColor("#B80017"));
            btn.setVisibility(View.VISIBLE);
        } else if (state == 4) {
            btn.setText("前往下载");
            btn.setBackgroundResource(R.drawable.order_btn_shape);
            btn.setTextColor(Color.parseColor("#5C5C5C"));
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.INVISIBLE);
        }
    }

    public static String timeFormat(String centerTime) {
        return String.format("拍摄于 %s（北京时间）", centerTime.replace(" ", "，"));
    }

    private String stateFormat(int state, String planCommitTime) {
        if (state == 3) {
            return String.format("%s：预计 %s 交付", stateArray[state], planCommitTime);
        } else {
            return String.format("%s", stateArray[state]);
        }
    }
}
