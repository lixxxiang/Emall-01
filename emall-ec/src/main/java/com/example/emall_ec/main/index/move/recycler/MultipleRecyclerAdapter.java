package com.example.emall_ec.main.index.move.recycler;

import android.os.Bundle;
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
import com.example.emall_core.util.log.EmallLogger;
import com.example.emall_core.util.view.GridSpacingItemDecoration;
import com.example.emall_core.util.view.TextSwitcherView2;
import com.example.emall_ec.R;
import com.example.emall_ec.main.classify.ClassifyDelegate;
import com.example.emall_ec.main.detail.GoodsDetailDelegate;
import com.example.emall_ec.main.discover.DiscoverDelegate;
import com.example.emall_ec.main.index.IndexDelegate;
import com.example.emall_ec.main.index.move.recycler.data.GuessLikeBean;
import com.example.emall_ec.main.index.move.recycler.data.TheThreeBean;
import com.example.emall_ec.main.index.move.recycler.data.UnitBean;
import com.example.emall_ec.main.program.ProgramDelegate;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.greendao.generator.Index;

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
    List<String> bannerDataType = new ArrayList<>();
    List<String> bannerProductId = new ArrayList<>();
    List<String> bannerLink = new ArrayList<>();

    private static IndexDelegate delegate;
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

    public static MultipleRecyclerAdapter create(IndexDelegate d, List<MultipleItemEntity> data) {
        delegate = d;
        return new MultipleRecyclerAdapter(data);
    }


    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }

    private void init() {
        //设置不同的item布局
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.BANNER, R.layout.item_multiple_banner);
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.EVERYDAY_PIC, R.layout.item_multiple_everyday_pic);
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.SCROLL_HORIZIONTAL, R.layout.item_multiple_scroll_horiziontal);
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.THE_THREE, R.layout.item_multiple_the_three);
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.GUESS_LIKE, R.layout.item_multiple_image_guess_like);
        addItemType(com.example.emall_ec.main.index.move.recycler.ItemType.BLANK, R.layout.item_multiple_blank);

        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected com.example.emall_ec.main.index.move.recycler.MultipleViewHolder createBaseViewHolder(View view) {
        return com.example.emall_ec.main.index.move.recycler.MultipleViewHolder.create(view);
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
                    banner.setImages((List<?>) entity.getField(MultipleFields.BANNERS_IMAGEURL));
                    bannerDataType = (List<String>) entity.getField(MultipleFields.BANNERS_DATA_TYPE);
                    bannerProductId = (List<String>) entity.getField(MultipleFields.BANNERS_PRODUCT_ID);
                    bannerLink = (List<String>) entity.getField(MultipleFields.BANNERS_LINK);

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
//                System.out.println(entity.getField(MultipleFields.HORIZONTAL_SCROLL).);
                horiziontalRecyclerView.setLayoutManager(new LinearLayoutManager(horiziontalRecyclerView.getContext(), LinearLayout.HORIZONTAL, false));
                horiziontalRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 20, true));
                SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
                snapHelperStart.attachToRecyclerView(horiziontalRecyclerView);
                horiziontalRecyclerView.setAdapter(new ItemUnitAdapter((List<UnitBean>) entity.getField(MultipleFields.HORIZONTAL_SCROLL), mContext, delegate));
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
                holder.getView(R.id.the_three_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        theThreeClick(theThreeList, 0);
                    }
                });
                holder.getView(R.id.the_three_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        theThreeClick(theThreeList, 1);
                    }
                });
                holder.getView(R.id.the_three_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        theThreeClick(theThreeList, 2);
                    }
                });
                break;
            case ItemType.GUESS_LIKE:
                RecyclerView.LayoutManager manager = new GridLayoutManager(mContext, 2);
                guessLikeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
                guessLikeRecyclerView.setLayoutManager(manager);
                guessLikeRecyclerView.setAdapter(new GuessLikeAdapter((List<GuessLikeBean>) entity.getField(MultipleFields.GUESS_LIKE), mContext, delegate));
                break;
            case ItemType.BLANK:

            default:
                break;
        }
    }

    private void theThreeClick(ArrayList<TheThreeBean> theThreeList, int position) {
        EmallLogger.INSTANCE.d(theThreeList.get(position).getType());
        switch (theThreeList.get(position).getType()) {
            case "scene": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", theThreeList.get(position).getProductId());
                bundle.putString("type", "1");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "night": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", theThreeList.get(position).getProductId());
                bundle.putString("type", "5");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "video": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", theThreeList.get(position).getProductId());
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
                bundle.putString("URL", theThreeList.get(position).getLink());
                discoverDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(discoverDelegate);
                break;

            default:
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
        EmallLogger.INSTANCE.d(bannerDataType.get(position));
        EmallLogger.INSTANCE.d(bannerProductId);
        switch (bannerDataType.get(position)) {
            case "scene": {
                EmallLogger.INSTANCE.d("DDDDDD");
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", bannerProductId.get(position));
                bundle.putString("type", "1");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "night": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", bannerProductId.get(position));
                bundle.putString("type", "5");
                goodsDetailDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(goodsDetailDelegate);
                break;
            }
            case "video": {
                GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate().create();
                Bundle bundle = new Bundle();
                bundle.putString("productId", bannerProductId.get(position));
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
                bundle.putString("URL", bannerLink.get(position));
                discoverDelegate.setArguments(bundle);
                delegate.getParentDelegate().start(discoverDelegate);
                break;

            default:
        }
    }
}

