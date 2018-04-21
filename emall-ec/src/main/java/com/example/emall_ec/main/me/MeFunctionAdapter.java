package com.example.emall_ec.main.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emall_ec.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 2018/3/5.
 */

public class MeFunctionAdapter extends BaseAdapter {

    private List<Integer> iconList;
    private List<Integer> titleList;
    private LayoutInflater inflater;
    private Context context;


    public MeFunctionAdapter() {
    }

    public MeFunctionAdapter(List<Integer> iconList, List<Integer> titleList, Context context) {
        this.iconList = iconList;
        this.titleList = titleList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return iconList == null ? 0 : iconList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.me_function_item, null);
        ImageView item_iv = view.findViewById(R.id.item_iv);
        TextView item_tv = view.findViewById(R.id.item_tv);
//        ImageView item_foward_tv = view.findViewById(R.id.item_foward_tv);
        item_iv.setImageResource(iconList.get(position));
        item_tv.setText(titleList.get(position));
//        item_foward_tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "iconfont/foward.ttf"));
        return view;
    }
}