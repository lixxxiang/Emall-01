package com.example.emall_ec.main.order.state;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_ec.R;
import com.example.emall_ec.main.order.state.data.OrderDetail;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiang on 2018/3/6.
 */

public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderDetail> dataList;
    private List<String> imageList = new ArrayList<>();
    private int resource;
    public static String[] typeArray = {"标准景", "编程摄影", "视频", "镶嵌", "夜景", "剪裁（边缘）", "剪裁（区块）", "良田计划"};
    public static String[] stateArray = {"待审核", "审核未通过", "待支付", "生产中", "已完成"};
    public static String[] payMethodArray = {"支付宝", "微信支付", "银行汇款", "线下支付"};

    public OrderListAdapter(Context context, List<OrderDetail> dataList,
                            int resource) {
        this.context = context;
        this.dataList = dataList;
        this.resource = resource;

//        EmallLogger.INSTANCE.d(dataList.get(0).getData().get(0).getDetails().getImageDetailUrl());
        for (int i = 0; i < dataList.get(0).getData().size(); i++) {
            imageList.add(dataList.get(0).getData().get(i).getDetails().getImageDetailUrl());
        }
        System.out.println(imageList);
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
                    EmallLogger.INSTANCE.d(i + "clicked");
                }
            });
            util.btn.setTag(i);
            view.setTag(util);
        } else {
            util = (Util) view.getTag();
        }
        util.orderId.setText(String.format(context.getString(R.string.orderId), dataList.get(0).getData().get(i).getOrderId()));
        util.title.setText(typeArray[dataList.get(0).getData().get(i).getType()]);
        util.time.setText(timeFormat(dataList.get(0).getData().get(i).getDetails().getCenterTime()));
        util.price.setText(String.format("¥%s", dataList.get(0).getData().get(i).getPayment()));
        util.state.setText(stateFormat(dataList.get(0).getData().get(i).getState(), dataList.get(0).getData().get(i).getPlanCommitTime()));
        buttonFormat(dataList.get(0).getData().get(i).getState(), util.btn);
        Glide.with(context)
                .load(imageList.get(i))
                .into(util.imageView);
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
        if (state != 4) {
            return String.format("%s：预计 %s 交付", stateArray[state], planCommitTime);
        } else {
            return String.format("%s：已交付", stateArray[state]);
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
