package com.example.emall_ec.main.order.state;

import android.content.Context;
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

public class OrderListAdapter extends BaseAdapter{

    private Context context;
    private List<OrderDetail> dataList;
    private List<String> imageList = new ArrayList<>();
    private int resource;

    public OrderListAdapter(Context context, List<OrderDetail> dataList,
                            int resource) {
        this.context = context;
        this.dataList = dataList;
        this.resource = resource;

        EmallLogger.INSTANCE.d(dataList.get(0).getData().get(0).getDetails().getImageDetailUrl());
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
            util.imageView = view.findViewById(R.id.item_order_image_iv);
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
        Glide.with(context)
                .load(imageList.get(i))
                .into(util.imageView);
        return view;
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
