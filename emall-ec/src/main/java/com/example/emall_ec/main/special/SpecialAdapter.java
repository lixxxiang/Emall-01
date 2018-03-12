package com.example.emall_ec.main.special;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.emall_core.ui.recycler.DataConverter;
import com.example.emall_core.ui.recycler.MultipleFields;
import com.example.emall_core.ui.recycler.MultipleItemEntity;
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter;
import com.example.emall_core.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by lixiang on 2018/3/12.
 */

public class SpecialAdapter  extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>implements
        BaseQuickAdapter.SpanSizeLookup,
        BaseQuickAdapter.OnItemClickListener {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SpecialAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static SpecialAdapter create(List<MultipleItemEntity> data) {
        return new SpecialAdapter(data);
    }

    public static SpecialAdapter create(SpecialDataConverter converter) {
        return new SpecialAdapter(converter.convert());
    }

    private void init(){
        addItemType(SpecialItemType.HORIZONTAL, com.example.emall_core.R.layout.item_special_horizontal);
        addItemType(SpecialItemType.VERTICAL, com.example.emall_core.R.layout.item_special_vertical);
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
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(SpecialMultipleFields.SPAN_SIZE);
    }
}
