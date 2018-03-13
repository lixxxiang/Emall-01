package com.example.emall_ec.main.special;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.emall_core.util.view.RoundImageView;
import com.example.emall_ec.R;
import com.example.emall_ec.main.special.beans.SpecialHorizontalBean;

import java.util.List;

/**
 * Created by lixiang on 2018/3/13.
 */

public class ItemSpecialAdapter extends RecyclerView.Adapter<ItemSpecialAdapter.ViewHolder> {

    private List<SpecialHorizontalBean> mApps;
    private Context context;

    public ItemSpecialAdapter(List<SpecialHorizontalBean> apps, Context context) {
        mApps = apps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemSpecialAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpecialHorizontalBean app = mApps.get(position);
        Glide.with(context)
                .load(app.getImageUrl())
                .into(holder.special_item_horizontal_riv);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoundImageView special_item_horizontal_riv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            special_item_horizontal_riv = itemView.findViewById(R.id.special_item_horizontal_riv);
        }

        @Override
        public void onClick(View v) {
//            Log.d("UnitBean", mApps.get(getAdapterPosition()).getName());
        }
    }

}