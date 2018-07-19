package com.example.emall_ec.main.special.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_ec.R;
import com.example.emall_ec.main.special.SpecialItemEntity;
import com.example.emall_ec.main.special.SpecialMultipleFields;
import com.example.emall_ec.main.special.SpecialMultipleViewHolder;
import com.example.emall_ec.main.special.beans.SpecialHorizontalBean;
import com.example.emall_ec.main.special.beans.SpecialVerticalBean;
import com.example.emall_ec.main.special.view.SpecialDelegate;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

/**
 * Created by lixiang on 2018/3/12.
 */

public class SpecialAdapter extends
        BaseMultiItemQuickAdapter<SpecialItemEntity, SpecialMultipleViewHolder> implements
        BaseQuickAdapter.SpanSizeLookup,
        BaseQuickAdapter.OnItemClickListener {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private static SpecialDelegate delegate;
    public SpecialAdapter(List<SpecialItemEntity> data) {
        super(data);
        init();
    }

    public static SpecialAdapter create(List<SpecialItemEntity> data, SpecialDelegate specialDelegate) {
        delegate = specialDelegate;
        return new SpecialAdapter(data);

    }


    private void init() {
        addItemType(0, R.layout.item_special_horizontal);
        addItemType(1, R.layout.item_special_vertical);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected SpecialMultipleViewHolder createBaseViewHolder(View view) {
        return SpecialMultipleViewHolder.create(view);
    }


    @Override
    protected void convert(SpecialMultipleViewHolder helper, SpecialItemEntity item) {
        RecyclerView horiziontalRecyclerView = helper.getView(R.id.special_scroll_horiziontal_recyclerview);
        RecyclerView verticalRecyclerView = helper.getView(R.id.special_vertical_rv);
        switch (helper.getItemViewType()) {
            case 0:
                horiziontalRecyclerView.setLayoutManager(new LinearLayoutManager(horiziontalRecyclerView.getContext(), LinearLayout.HORIZONTAL, false));
                SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
                snapHelperStart.attachToRecyclerView(horiziontalRecyclerView);
                horiziontalRecyclerView.setAdapter(new ItemSpecialAdapter((List<SpecialHorizontalBean>) item.getField(SpecialMultipleFields.HORIZONTAL), mContext, delegate));
                break;
            case 1:
                RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
//                verticalRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
                verticalRecyclerView.setLayoutManager(manager);
                verticalRecyclerView.setAdapter(new ItemVerticalAdapter((List<SpecialVerticalBean>) item.getField(SpecialMultipleFields.VERTICAL),mContext,delegate));
                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(SpecialMultipleFields.SPAN_SIZE);
    }
}
