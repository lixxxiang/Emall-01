package com.example.emall_ec.main.me.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.example.emall_core.util.view.CacheUtil;
import com.example.emall_ec.R;

import java.util.List;

/**
 * Created by lixiang on 2018/4/3.
 */

public class SettingAdapter extends BaseAdapter {
    private List<Integer> titleList;
    private Context context;
    private LayoutInflater inflater;
    private int index;

    public SettingAdapter(Context context, List<Integer> titleList, int index) {
        this.titleList = titleList;
        this.context = context;
        this.index = index;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.setting_item, null);
        String string[] = {
                "每次提醒",
                "提醒一次"
        };
        ImageView item_iv = view.findViewById(R.id.foward_iv);
        TextView item_tv = view.findViewById(R.id.title);
        TextView detail_tv = view.findViewById(R.id.detail_tv);
        if (i == 0) {
            item_iv.setImageResource(R.drawable.ic_foward);

        }else if(i ==1) {
            try {
                detail_tv.setText(CacheUtil.getTotalCacheSize(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (i == 2){
            detail_tv.setText(string[index]);
        }

        item_tv.setText(titleList.get(i));

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
