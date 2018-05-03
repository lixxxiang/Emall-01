package com.example.emall_ec.main.me.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emall_core.util.view.CacheUtil;
import com.example.emall_ec.R;

import java.util.List;

/**
 * Created by lixiang on 2018/4/3.
 */

public class AccountPrivacySettingsAdapter extends BaseAdapter {

    private List<Integer> titleList;
    private Context context;
    private LayoutInflater inflater;
    private String tel;
    private int index;

    public AccountPrivacySettingsAdapter(List<Integer> titleList, Context context, int index, String tel) {
        this.titleList = titleList;
        this.context = context;
        this.index = index;
        this.tel = tel;
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

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.account_privacy_settings_item, null);
        ImageView item_iv = view.findViewById(R.id.foward_iv);
        TextView item_tv = view.findViewById(R.id.title);
        TextView detail_tv = view.findViewById(R.id.detail_tv);

        item_iv.setImageResource(R.drawable.forward_gray);
        item_tv.setText(titleList.get(i));

        if (i == 0) {
            detail_tv.setText(tel);
        }

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
