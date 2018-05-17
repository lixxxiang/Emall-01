package com.example.emall_ec.main.index.move.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emall_core.R;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.SquareImageView;
import com.example.emall_ec.main.classify.ClassifyDelegate;
import com.example.emall_ec.main.detail.GoodsDetailDelegate;
import com.example.emall_ec.main.discover.DiscoverDelegate;
import com.example.emall_ec.main.index.IndexDelegate;
import com.example.emall_ec.main.index.move.recycler.data.GuessLikeBean;
import com.example.emall_ec.main.index.move.recycler.data.UnitBean;
import com.example.emall_ec.main.program.ProgramDelegate;

import java.util.List;

/**
 * Created by lixiang on 2018/3/11.
 */

public class GuessLikeAdapter extends RecyclerView.Adapter<GuessLikeAdapter.ViewHolder> {

    private List<GuessLikeBean> list;
    private Context context;
    private IndexDelegate delegate;

    public GuessLikeAdapter(List<GuessLikeBean> list, Context context, IndexDelegate delegate){
        this.list = list;
        this.context = context;
        this.delegate = delegate;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_guess_like, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GuessLikeBean guessLikeBean = list.get(position);
        holder.guess_like_type_tv.setText(guessLikeBean.getPosTitle());
        holder.guess_like_title_tv.setText(guessLikeBean.getPosDescription());
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

        public SquareImageView guess_like_image_iv;
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
            guessLikeClick(list, getAdapterPosition());
        }
    }

    private void guessLikeClick(List<GuessLikeBean> guessLikeList, int position) {
        EmallLogger.INSTANCE.d(guessLikeList.get(position).getDataType());
        switch (guessLikeList.get(position).getDataType()) {
            case "scene": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", guessLikeList.get(position).getProductId());
                bundle.putString("type", "1");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "night": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", guessLikeList.get(position).getProductId());
                bundle.putString("type", "5");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "video": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", guessLikeList.get(position).getProductId());
                bundle.putString("type", "3");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "sceneSearch": {
                ClassifyDelegate classifyDelegate = new ClassifyDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "SCENE");
                classifyDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(classifyDelegate);
                break;
            }
            case "nightSearch": {
                ClassifyDelegate classifyDelegate = new ClassifyDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "NOCTILUCENCE");
                classifyDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(classifyDelegate);
                break;
            }
            case "videoSearch": {
                ClassifyDelegate classifyDelegate = new ClassifyDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "VIDEO");
                classifyDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(classifyDelegate);
                break;
            }
            case "programSearch": {
                delegate.getParentDelegate().start(new ProgramDelegate().create());
                break;
            }
            case "webview":
//                DiscoverDelegate discoverDelegate = new DiscoverDelegate().create();
//                Bundle bundle = new Bundle();
//                bundle.putString("URL", guessLikeList.get(position).get());
//                discoverDelegate.setArguments(bundle);
//                delegate.getParentDelegate().start(discoverDelegate);
                Toast.makeText(delegate.getActivity(), "IT CANNOT BE WEBVIEW", Toast.LENGTH_LONG).show();
                break;

            default:
        }
    }
}
