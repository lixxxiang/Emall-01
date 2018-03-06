package com.example.emall_ec.main.order.state;

import android.content.Context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        Util util = null;
        final int flag = i;
        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
        if (view == null) {
            util = new Util();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
            util.imageView = view.findViewById(R.id.item_order_image_iv);
            view.setTag(util);
        } else {
            util = (Util) view.getTag();
        }
        Glide.with(context)
                .load(imageList.get(i))
                .into(util.imageView);
        return view;
    }

    class Util{
        ImageView imageView;
    }
}
