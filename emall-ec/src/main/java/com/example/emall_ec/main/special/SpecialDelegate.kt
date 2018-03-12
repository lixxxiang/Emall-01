package com.example.emall_ec.main.special

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_special.*

/**
 * Created by lixiang on 2018/3/12.
 */
class SpecialDelegate : BottomItemDelegate() {

    private var data: MutableList<MultipleItemEntity>? = mutableListOf()
    private var mAdapter: SpecialAdapter? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_special
    }

    override fun initial() {
        RestClient().builder()
                .url("http://192.168.1.36:3004/data")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        data!!.add(SpecialDataConverter().setJsonData(response).horizontalConvert()[0])
                        data!!.add(SpecialDataConverter().setJsonData(response).verticalConvert()[0])
                    }
                })
                .build()
                .get()
        mAdapter = SpecialAdapter.create(data)
        special_rv.adapter = mAdapter
    }
}