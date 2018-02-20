package com.example.emall_core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_core.R;
import com.example.emall_core.ui.banner.BannerCreator;
import com.example.emall_core.util.log.EmallLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 18/02/2018.
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;
    //设置图片加载策略
    private static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        addItemType(ItemType.EVERYDAY_PIC, R.layout.item_multiple_everyday_pic);
        addItemType(ItemType.SCROLL_HORIZIONTAL, R.layout.item_multiple_scroll_horiziontal);
        addItemType(ItemType.THE_THREE, R.layout.item_multiple_the_three);
        addItemType(ItemType.GUESS_LIKE_TITLE, R.layout.item_multiple_guess_like_title);
        addItemType(ItemType.GUESS_LIKE, R.layout.item_multiple_image_guess_like);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;
            case ItemType.EVERYDAY_PIC:
//                text = entity.getField(MultipleFields.TEXT);
//                holder.setText(R.id.text_single, text);
                break;
            case ItemType.SCROLL_HORIZIONTAL:
//                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .apply(RECYCLER_OPTIONS)
//                        .into((ImageView) holder.getView(R.id.img_single));
                break;
            case ItemType.THE_THREE:
//                text = entity.getField(MultipleFields.TEXT);
//                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
//                Glide.with(mContext)
//                        .load(imageUrl)
//                        .apply(RECYCLER_OPTIONS)
//                        .into((ImageView) holder.getView(R.id.img_multiple));
//                holder.setText(R.id.tv_multiple, text);
                break;
            case ItemType.GUESS_LIKE_TITLE:
                break;
            case ItemType.GUESS_LIKE:
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}

