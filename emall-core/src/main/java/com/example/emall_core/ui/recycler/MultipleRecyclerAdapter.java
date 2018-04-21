package com.example.emall_core.ui.recycler;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_core.R;
import com.example.emall_core.app.Emall;
import com.example.emall_core.ui.recycler.data.GuessLikeBean;
import com.example.emall_core.ui.recycler.data.TheThreeBean;
import com.example.emall_core.ui.recycler.data.UnitBean;
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.GridSpacingItemDecoration;
import com.example.emall_core.util.view.TextSwitcherView;
import com.example.emall_core.util.view.TextSwitcherView2;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiang on 18/02/2018.
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup,
        OnItemClickListener,
        OnBannerListener {

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;
    ArrayList<TheThreeBean> theThreeList = new ArrayList<>();
    ArrayList<String> dailyPicTitleList = new ArrayList<>();

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

//    public static MultipleRecyclerAdapter create(DataConverter converter) {
//        return new MultipleRecyclerAdapter(converter.convert());
//    }

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
        addItemType(ItemType.GUESS_LIKE, R.layout.item_multiple_image_guess_like);
        addItemType(ItemType.BLANK, R.layout.item_multiple_blank);

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
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        final String imageUrl1;
        Banner banner = holder.getView(R.id.banner);
        RecyclerView horiziontalRecyclerView = holder.getView(R.id.scroll_horiziontal_recyclerview);
        RecyclerView guessLikeRecyclerView = holder.getView(R.id.guess_like_rv);
        switch (holder.getItemViewType()) {
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    banner.setImageLoader(new GlideImageLoader());
                    banner.setImages((List<?>) entity.getField(MultipleFields.BANNERS));
                    banner.start();
                    mIsInitBanner = true;
                    holder.addOnClickListener(R.id.banner);
                    banner.setOnBannerListener(this);
                }
                break;
            case ItemType.EVERYDAY_PIC:
                dailyPicTitleList = entity.getField(MultipleFields.EVERY_DAY_PIC_TITLE);
                TextSwitcherView2 tsv = holder.getView(R.id.mep_detail_tv);
                tsv.getResource(dailyPicTitleList);
//                TextView textView = holder.getView(R.id.mep_detail_tv);
//                textView.setText("df");
                break;
            case ItemType.SCROLL_HORIZIONTAL:
                horiziontalRecyclerView.setLayoutManager(new LinearLayoutManager(horiziontalRecyclerView.getContext(), LinearLayout.HORIZONTAL, false));
                SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
                snapHelperStart.attachToRecyclerView(horiziontalRecyclerView);
                horiziontalRecyclerView.setAdapter(new ItemUnitAdapter((List<UnitBean>) entity.getField(MultipleFields.HORIZONTAL_SCROLL), mContext));
                break;
            case ItemType.THE_THREE:
                theThreeList = entity.getField(MultipleFields.THE_THREE);
                Glide.with(mContext)
                        .load(theThreeList.get(0).getImageUrl())
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.the_three_1));
                Glide.with(mContext)
                        .load(theThreeList.get(1).getImageUrl())
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.the_three_2));
                Glide.with(mContext)
                        .load(theThreeList.get(2).getImageUrl())
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.the_three_3));
                break;
            case ItemType.GUESS_LIKE:
                RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2);
                guessLikeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
                guessLikeRecyclerView.setLayoutManager(manager);
                guessLikeRecyclerView.setAdapter(new GuessLikeAdapter((List<GuessLikeBean>) entity.getField(MultipleFields.GUESS_LIKE), mContext));
                break;
            case ItemType.BLANK:

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
        System.out.println(position);
    }

    @Override
    public void OnBannerClick(int position) {
        System.out.println(position);
    }
}

