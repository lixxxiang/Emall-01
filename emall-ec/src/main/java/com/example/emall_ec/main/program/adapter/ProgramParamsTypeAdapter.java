package com.example.emall_ec.main.program.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class ProgramParamsTypeAdapter extends BaseAdapter {

    private List<Integer> titleList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;

    public ProgramParamsTypeAdapter() {
    }

    public ProgramParamsTypeAdapter(List<Integer> titleList, Context context) {
        this.titleList = titleList;
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

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.item_program_params_type, null);
        TextView item_tv = view.findViewById(R.id.program_params_type_title_tv);
        ImageView item_iv = view.findViewById(R.id.program_params_type_iv);
        item_tv.setText(titleList.get(position));
        if(position == selectedItem) {
            item_iv.setBackgroundResource(R.drawable.ic_check);
        }
        return view;
    }
}