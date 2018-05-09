package com.example.emall_ec.main.order.state.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_ec.R;
import com.example.emall_ec.main.demand.PayMethodDelegate;
import com.example.emall_ec.main.order.ProductDeliveryDelegate;
import com.example.emall_ec.main.order.state.AllDelegate;
import com.example.emall_ec.main.order.state.ObligationDelegate;
import com.example.emall_ec.main.order.state.data.OrderDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.emall_ec.main.order.state.adapter.AllListAdapter.programArray;

/**
 * Created by lixiang on 2018/3/6.
 */

public class ObligationListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderDetail> dataList;
    private List<String> imageList = new ArrayList<>();
    private int resource;
    public static String[] typeArray = {"", "光学1级", "编程摄影", "视频", "镶嵌", "夜景", "剪裁（边缘）", "剪裁（区块）", "良田计划"};

    public static String[] stateArray = {"待审核", "审核未通过", "待支付", "生产中", "已完成"};
    public static String[] payMethodArray = {"支付宝", "微信支付", "银行汇款", "线下支付"};

    public BtnListener btnListener;
    ObligationDelegate delegate;

    public ObligationListAdapter(ObligationDelegate delegate, List<OrderDetail> dataList,
                                 int resource, Context context) {
        this.delegate = delegate;
        this.dataList = dataList;
        this.resource = resource;
        this.context = context;

        for (int i = 0; i < dataList.get(0).getData().size(); i++) {

                imageList.add(dataList.get(0).getData().get(i).getDetails().getImageDetailUrl());
        }
    }

    @Override
    public int getCount() {
        return dataList.get(0).getData().size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public interface BtnListener {
        public void onBtnClick();
    }

    public void setOnBtnClickListener(BtnListener listener) {
        this.btnListener = listener;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        Util util = null;
        if (view == null) {
            util = new Util();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
            util.orderId = view.findViewById(R.id.item_order_orderid_tv);
            util.title = view.findViewById(R.id.item_order_title_tv);
            util.time = view.findViewById(R.id.item_order_time_tv);
            util.price = view.findViewById(R.id.item_order_price_tv);
            util.imageView = view.findViewById(R.id.item_order_image_iv);
            util.state = view.findViewById(R.id.item_order_state_tv);

            util.btn = view.findViewById(R.id.item_order_btn);
            util.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EmallLogger.INSTANCE.d(dataList.get(0).getData().get(i).getState());
                    if (dataList.get(0).getData().get(i).getState() == 2) {
                        PayMethodDelegate payMethodDelegate = new PayMethodDelegate().create();
                        Bundle bundle = new Bundle();
                        bundle.putString("PARENT_ORDER_ID", dataList.get(0).getData().get(i).getOrderId());
                        bundle.putString("TYPE", "2");
                        bundle.putString("PAGE_FROM", "ORDER_LIST");

                        payMethodDelegate.setArguments(bundle);
                        delegate.getParentDelegate().start(payMethodDelegate);

                    } else if (dataList.get(0).getData().get(i).getState() == 4) {
                        ProductDeliveryDelegate productDeliveryDelegate = new ProductDeliveryDelegate().create();
                        delegate.getParentDelegate().start(productDeliveryDelegate);
                    }
                }
            });
            util.btn.setTag(i);
            view.setTag(util);
        } else {
            util = (Util) view.getTag();
        }
        util.orderId.setText(String.format(context.getString(R.string.orderId), dataList.get(0).getData().get(i).getOrderId()));
        util.title.setText(typeArray[dataList.get(0).getData().get(i).getType()]);
        if (dataList.get(0).getData().get(i).getType() == 2) {
            util.time.setText("类型：" + programArray[Integer.parseInt(dataList.get(0).getData().get(i).getDetails().getProductType())]);
        } else {
            if (dataList.get(0).getData().get(i).getDetails().getCenterTime() == null)
                util.time.setText(timeFormat(dataList.get(0).getData().get(i).getDetails().getStartTime()));
            else
                util.time.setText(timeFormat(dataList.get(0).getData().get(i).getDetails().getCenterTime()));
        }
        util.price.setText(String.format("¥%s", new DecimalFormat("######0.00").format(dataList.get(0).getData().get(i).getPayment())));
        util.state.setText(stateFormat(dataList.get(0).getData().get(i).getState(), dataList.get(0).getData().get(i).getPlanCommitTime()));
        buttonFormat(dataList.get(0).getData().get(i).getState(), util.btn);

        util.imageView.setImageResource(R.drawable.program);
        util.imageView.setTag(R.id.imageid, imageList.get(i));
        if (util.imageView.getTag(R.id.imageid) != null && imageList.get(i) == util.imageView.getTag(R.id.imageid)) {
            Glide.with(context).load(imageList.get(i)).into(util.imageView);
        }
        return view;
    }

    private void buttonFormat(int state, Button btn) {
        if (state == 2) {
            btn.setText("去付款");
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.order_btn_shape_red);
            btn.setBackgroundColor(Color.parseColor("#B80017"));
        } else if (state == 4) {
            btn.setText("前往下载");
            btn.setBackgroundResource(R.drawable.order_btn_shape);
            btn.setTextColor(Color.parseColor("#5C5C5C"));
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


    class Util {
        ImageView imageView;
        TextView orderId;
        Button btn;
        TextView title;
        TextView time;
        TextView price;
        TextView state;
    }
}
