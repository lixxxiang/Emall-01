package com.example.emall_ec.main.special.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.RoundImageView;
import com.example.emall_ec.R;
import com.example.emall_ec.main.classify.ClassifyDelegate;
import com.example.emall_ec.main.detail.GoodsDetailDelegate;
import com.example.emall_ec.main.discover.DiscoverDelegate;
import com.example.emall_ec.main.index.move.recycler.data.UnitBean;
import com.example.emall_ec.main.program.ProgramDelegate;
import com.example.emall_ec.main.special.beans.SpecialVerticalBean;
import com.example.emall_ec.main.special.view.SpecialDelegate;

import java.util.List;

/**
 * Created by lixiang on 2018/3/13.
 */

public class ItemVerticalAdapter extends RecyclerView.Adapter<ItemVerticalAdapter.ViewHolder> {

    private List<SpecialVerticalBean> mApps;
    private Context context;
    private SpecialDelegate delegate;

    public ItemVerticalAdapter(List<SpecialVerticalBean> apps, Context context, SpecialDelegate delegate) {
        mApps = apps;
        this.context = context;
        this.delegate = delegate;
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
            verticalClick(mApps, getAdapterPosition());

        }
    }

    private void verticalClick(List<SpecialVerticalBean> verticalList, int position) {
        EmallLogger.INSTANCE.d(verticalList.get(position).getDataType());
        switch (verticalList.get(position).getDataType()) {
            case "scene": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", verticalList.get(position).getProductId());
                bundle.putString("type", "1");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "night": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", verticalList.get(position).getProductId());
                bundle.putString("type", "5");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "video": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", verticalList.get(position).getProductId());
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
                DiscoverDelegate discoverDelegate = new DiscoverDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("URL", verticalList.get(position).getLink());
                discoverDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(discoverDelegate);
                break;

            default:
        }
    }

}


