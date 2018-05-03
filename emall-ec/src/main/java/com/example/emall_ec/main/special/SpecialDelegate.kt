package com.example.emall_ec.main.special

import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.index.IndexItemClickListener
import com.example.emall_ec.main.special.beans.SpecialHorizontalBean
import kotlinx.android.synthetic.main.delegate_index.*
import kotlinx.android.synthetic.main.delegate_special.*

/**
 * Created by lixiang on 2018/3/12.
 */
class SpecialDelegate : BottomItemDelegate() {

    private var mAdapter: SpecialAdapter? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_special
    }

    override fun initial() {
        setSwipeBackEnable(false)

        initRecyclerView()
        getData()

        special_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            special_srl.isRefreshing = true
            Handler().postDelayed({
//                getData()
                special_srl.isRefreshing = false
            }, 1200)
        })
    }

    private fun getData() {
        val data: MutableList<SpecialItemEntity>? = mutableListOf()

        RestClient().builder()
                .url("http://59.110.162.194:5201/global/homePageUnits")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        data!!.add(SpecialDataConverter().setJsonData(response).horizontalConvert()[0])
                        data.add(SpecialDataConverter().setJsonData(response).verticalConvert()[0])
                        mAdapter = SpecialAdapter.create(data)
                        special_rv.adapter = mAdapter
                    }
                })
                .build()
                .get()
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        special_rv.layoutManager = manager
    }
}