package com.example.emall_ec.main.program.adapter;

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

import java.util.List;

/**
 * Created by lixiang on 2018/3/19.
 */

public class ProgramParamsAdapter extends BaseAdapter {

    private List<Integer> titleList;
    private List<String> detailList;
    private LayoutInflater inflater;
    private Context context;


    public ProgramParamsAdapter() {
    }

    public ProgramParamsAdapter( List<Integer> titleList, List<String> detailList,Context context) {
        this.titleList = titleList;
        this.detailList = detailList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.size();
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
        View view = inflater.inflate(R.layout.item_program_params, null);
        TextView item_tv = view.findViewById(R.id.program_params_title_tv);
        TextView item_detail_tv = view.findViewById(R.id.program_params_detail_tv);
        item_tv.setText(titleList.get(position));
        item_detail_tv.setText(detailList.get(position));
        return view;
    }
}