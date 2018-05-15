package com.example.emall_ec.main.index.move.recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emall_core.R;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.RoundImageView;
import com.example.emall_ec.main.classify.ClassifyDelegate;
import com.example.emall_ec.main.detail.GoodsDetailDelegate;
import com.example.emall_ec.main.discover.DiscoverDelegate;
import com.example.emall_ec.main.index.IndexDelegate;
import com.example.emall_ec.main.index.move.recycler.data.TheThreeBean;
import com.example.emall_ec.main.index.move.recycler.data.UnitBean;
import com.example.emall_ec.main.program.ProgramDelegate;

import org.greenrobot.greendao.generator.Index;

import java.util.ArrayList;
import java.util.List;

public class ItemUnitAdapter extends RecyclerView.Adapter<ItemUnitAdapter.ViewHolder> {

    private List<UnitBean> mApps;
    private Context context;
    private IndexDelegate delegate;

    public ItemUnitAdapter(List<UnitBean> apps, Context context, IndexDelegate delegate) {
        mApps = apps;
        this.context = context;
        this.delegate = delegate;
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
            horizontalClick(mApps, getAdapterPosition());
        }
    }

    private void horizontalClick(List<UnitBean> horizontalList, int position) {
        EmallLogger.INSTANCE.d(horizontalList.get(position).getType());
        switch (horizontalList.get(position).getType()) {
            case "scene": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", horizontalList.get(position).getProductId());
                bundle.putString("type", "1");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "night": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", horizontalList.get(position).getProductId());
                bundle.putString("type", "5");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "video": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", horizontalList.get(position).getProductId());
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
                bundle.putString("URL", horizontalList.get(position).getLink());
                discoverDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(discoverDelegate);
                break;

            default:
        }
    }

}

