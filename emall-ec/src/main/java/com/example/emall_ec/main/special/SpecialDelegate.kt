package com.example.emall_ec.main.special

import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blankj.utilcode.util.NetworkUtils
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_special.*

/**
 * Created by lixiang on 2018/3/12.
 */
class SpecialDelegate : BottomItemDelegate() {

    private var mAdapter: SpecialAdapter? = null
    private var delegate : SpecialDelegate ?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_special
    }

    override fun initial() {
        setSwipeBackEnable(false)
        delegate = this
        initRecyclerView()
        getData()

        if(!NetworkUtils.isConnected()){
            special_no_network_rl.visibility = View.VISIBLE
        }

        special_no_network_rl.setOnClickListener {
            if (NetworkUtils.isConnected()){
                special_no_network_rl.visibility = View.GONE
            }
            special_srl.isRefreshing = true
            Handler().postDelayed({
                //                getData()
                special_srl.isRefreshing = false
                getData()

            }, 1200)
        }

        special_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            special_srl.isRefreshing = true
            Handler().postDelayed({
//                getData()
                special_srl.isRefreshing = false
                getData()

            }, 1200)
        })
    }

    private fun getData() {
        val data: MutableList<SpecialItemEntity>? = mutableListOf()

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/topic")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        data!!.add(SpecialDataConverter().setJsonData(response).horizontalConvert()[0])
                        data.add(SpecialDataConverter().setJsonData(response).verticalConvert()[0])
                        mAdapter = SpecialAdapter.create(data, delegate)
                        if(special_rv != null){
                            special_rv.adapter = mAdapter

                        }
                    }
                })
                .build()
                .get()
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        special_rv.layoutManager = manager as RecyclerView.LayoutManager?
    }
}