package com.example.emall_ec.main.index.move.recycler;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.emall_core.util.log.EmallLogger;

/**
 * Created by lixiang on 18/02/2018.
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }

    @Override
    public BaseViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        return super.setOnItemClickListener(viewId, listener);
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
    }
}
