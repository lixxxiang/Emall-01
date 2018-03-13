package com.example.emall_ec.main.special;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_core.ui.recycler.MultipleViewHolder;

/**
 * Created by lixiang on 2018/3/13.
 */

public class SpecialMultipleViewHolder extends BaseViewHolder {

    private SpecialMultipleViewHolder(View view) {
        super(view);
    }

    public static SpecialMultipleViewHolder create(View view) {
        return new SpecialMultipleViewHolder(view);
    }

    @Override
    public BaseViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        return super.setOnItemClickListener(viewId, listener);
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
    }
}
