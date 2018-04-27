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

public class SearchPoiCitiesAdapter extends BaseAdapter {

    private List<String> citiesList;
    private List<String> countList;
    private LayoutInflater inflater;
    private Context context;


    public SearchPoiCitiesAdapter() {
    }

    public SearchPoiCitiesAdapter(List<String> citiesList, List<String> countList, Context context) {
        this.citiesList = citiesList;
        this.countList = countList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countList == null ? 0 : countList.size();
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
        View view = inflater.inflate(R.layout.item_search_poi_cities, null);
        TextView item_tv = view.findViewById(R.id.search_poi_cities_title_tv);
        TextView item_detail_tv = view.findViewById(R.id.search_poi_cities_detail_tv);
        item_tv.setText(citiesList.get(position));
        item_detail_tv.setText(countList.get(position) + "个");
        return view;
    }
}