package com.example.emall_ec.main.special;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.emall_core.util.view.RoundImageView;
import com.example.emall_ec.R;
import com.example.emall_ec.main.special.beans.SpecialVerticalBean;

import java.util.List;

/**
 * Created by lixiang on 2018/3/13.
 */

public class ItemVerticalAdapter extends RecyclerView.Adapter<ItemVerticalAdapter.ViewHolder> {

    private List<SpecialVerticalBean> mApps;
    private Context context;

    public ItemVerticalAdapter(List<SpecialVerticalBean> apps, Context context) {
        mApps = apps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpecialVerticalBean app = mApps.get(position);
//        holder.special_vertical_title_tv.setText(app.getPosTitle());
//        holder.special_vertical_detail_tv.setText(app.getPosDescription());

        Glide.with(context)
                .load(app.getImageUrl())
                .into(holder.special_vertical_riv);
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

        public RoundImageView special_vertical_riv;
//        public TextView special_vertical_title_tv;
//        public TextView special_vertical_detail_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            special_vertical_riv = itemView.findViewById(R.id.special_vertical_riv);
//            special_vertical_title_tv = itemView.findViewById(R.id.special_vertical_title_tv);
//            special_vertical_detail_tv = itemView.findViewById(R.id.special_vertical_detail_tv);
        }

        @Override
        public void onClick(View v) {
//            Log.d("UnitBean", mApps.get(getAdapterPosition()).getName());
        }
    }

}


