package com.example.emall_core.ui.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emall_core.R;
import com.example.emall_core.ui.recycler.data.UnitBean;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.RoundImageView;

import java.util.List;

public class ItemUnitAdapter extends RecyclerView.Adapter<ItemUnitAdapter.ViewHolder> {

    private List<UnitBean> mApps;
    private Context context;

    public ItemUnitAdapter(List<UnitBean> apps, Context context) {
        mApps = apps;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unit, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UnitBean app = mApps.get(position);
        holder.unit_title_tv.setText(app.getTitle());
        holder.unit_detail_tv.setText(app.getDetail());
        holder.unit_description_tv.setText(app.getTitle());

        Glide.with(context)
                .load(app.getImageUrl())
                .into(holder.unit_image_iv);
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

        public RoundImageView unit_image_iv;
        public TextView unit_title_tv;
        public TextView unit_detail_tv;
        public TextView unit_description_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            unit_image_iv = itemView.findViewById(R.id.unit_image_iv);
            unit_title_tv = itemView.findViewById(R.id.unit_title_tv);
            unit_detail_tv = itemView.findViewById(R.id.unit_detail_tv);
            unit_description_tv = itemView.findViewById(R.id.unit_description_tv);
        }

        @Override
        public void onClick(View v) {
//            Log.d("UnitBean", mApps.get(getAdapterPosition()).getName());
        }
    }

}

