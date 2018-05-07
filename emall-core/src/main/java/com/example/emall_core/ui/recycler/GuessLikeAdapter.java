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
import com.example.emall_core.ui.recycler.data.GuessLikeBean;
import com.example.emall_core.util.view.RoundImageView;

import java.util.List;

/**
 * Created by lixiang on 2018/3/11.
 */

public class GuessLikeAdapter extends RecyclerView.Adapter<GuessLikeAdapter.ViewHolder> {

    private List<GuessLikeBean> list;
    private Context context;

    public GuessLikeAdapter(List<GuessLikeBean> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuessLikeAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guess_like, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GuessLikeBean guessLikeBean = list.get(position);
        holder.guess_like_type_tv.setText(guessLikeBean.getDataType());
        holder.guess_like_title_tv.setText(guessLikeBean.getPosTitle());
        holder.guess_like_price_tv.setText("¥" + guessLikeBean.getPrice());
        Glide.with(context)
                .load(guessLikeBean.getImageUrl())
                .into(holder.guess_like_image_iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView guess_like_image_iv;
        public TextView guess_like_type_tv;
        public TextView guess_like_title_tv;
        public TextView guess_like_price_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            guess_like_image_iv = itemView.findViewById(R.id.item_guess_like_iv);
            guess_like_type_tv = itemView.findViewById(R.id.item_guess_like_type_tv);
            guess_like_title_tv = itemView.findViewById(R.id.item_guess_like_title_tv);
            guess_like_price_tv = itemView.findViewById(R.id.item_guess_like_price_tv);
        }

        @Override
        public void onClick(View v) {
//            Log.d("UnitBean", mApps.get(getAdapterPosition()).getName());
        }
    }
}
