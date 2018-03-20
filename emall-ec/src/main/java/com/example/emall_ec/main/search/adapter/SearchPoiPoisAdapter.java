package com.example.emall_ec.main.search.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emall_ec.R;

import java.util.List;

/**
 * Created by lixiang on 2018/3/19.
 */

public class SearchPoiPoisAdapter extends BaseAdapter {

    private List<String> poisList;
    private List<String> addressList;
    private LayoutInflater inflater;
    private Context context;


    public SearchPoiPoisAdapter() {
    }

    public SearchPoiPoisAdapter(List<String> poisList, List<String> addressList, Context context) {
        this.poisList = poisList;
        this.addressList = addressList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return addressList == null ? 0 : addressList.size();
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
        View view = inflater.inflate(R.layout.item_search_poi_pois, null);
        TextView item_tv = view.findViewById(R.id.search_poi_pois_title_tv);
        TextView item_detail_tv = view.findViewById(R.id.search_poi_pois_detail_tv);
        item_tv.setText(poisList.get(position));
        item_detail_tv.setText(addressList.get(position));
        return view;
    }
}