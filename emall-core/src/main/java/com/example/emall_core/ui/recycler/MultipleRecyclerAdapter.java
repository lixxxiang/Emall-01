package com.example.emall_core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_core.R;
import com.example.emall_core.app.Emall;
import com.example.emall_core.ui.banner.BannerCreator;
import com.example.emall_core.ui.recycler.test.Adapter;
import com.example.emall_core.ui.recycler.test.App;
import com.example.emall_core.util.log.EmallLogger;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Array;
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
        EmallLogger.INSTANCE.d(data);
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
//        addItemType(ItemType.GUESS_LIKE_TITLE, R.layout.item_multiple_guess_like_title);
//        addItemType(ItemType.GUESS_LIKE, R.layout.item_multiple_image_guess_like);
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
        final String text;
        final String imageUrl1;
        final String imageUrl2;
//        final ArrayList<String> bannerImages = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            bannerImages.add((String) content.get(i).getField(MultipleFields.BANNERS));
//        }
        Banner banner = holder.getView(R.id.banner);
        RecyclerView horiziontalRecyclerView = holder.getView(R.id.scroll_horiziontal_recyclerview);
//        EmallLogger.INSTANCE.d(entity.getField(MultipleFields.BANNERS));
        switch (holder.getItemViewType()) {
            case ItemType.BANNER:
                if (!mIsInitBanner) {

//                    EmallLogger.INSTANCE.d(bannerImages);
//                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycler_item);
//                    BannerCreator.setDefault(convenientBanner, bannerImages, this);

                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages((List<?>) entity.getField(MultipleFields.BANNERS));
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    mIsInitBanner = true;
                    holder.addOnClickListener(R.id.banner);
                    banner.setOnBannerListener(this);
//
//                    holder.setOnItemClickListener(new OnItemChildClickListener() {
//
//                        @Override
//                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                            System.out.println(holder.getLayoutPosition());
//                            Log.e("dfdd","dfdfd");
//
//                        }
//                    });


//                    holder.addOnClickListener(R.id.banner);
//                    banner.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            System.out.println(holder.getLayoutPosition());
//                        }
//                    });
                }
//                else{
//                    banner.setVisibility(View.GONE);
//                }
                break;
            case ItemType.EVERYDAY_PIC:
                holder.setText(R.id.text, "ddadfs");
                break;
            case ItemType.SCROLL_HORIZIONTAL:
                horiziontalRecyclerView.setLayoutManager(new LinearLayoutManager(horiziontalRecyclerView.getContext(), LinearLayout.HORIZONTAL, false));
                SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
                snapHelperStart.attachToRecyclerView(horiziontalRecyclerView);
                horiziontalRecyclerView.setAdapter(new Adapter((List<App>) entity.getField(MultipleFields.HORIZONTAL_SCROLL)));
                break;
            case ItemType.THE_THREE:
//                text = entity.getField(MultipleFields.TEXT);
                imageUrl1 = entity.getField(MultipleFields.THE_THREE_IMAGE_URL);


                Glide.with(mContext)
                        .load(imageUrl1)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) holder.getView(R.id.the_three_1));
                holder.addOnClickListener(R.id.the_three_1);

//                holder.addOnClickListener(R.id.the_three_2);


                break;
//            case ItemType.GUESS_LIKE_TITLE:
//                break;
//            case ItemType.GUESS_LIKE:
//                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//        return content.get(position).getField(MultipleFields.SPAN_SIZE);
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

