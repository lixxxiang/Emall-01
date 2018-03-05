package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.index.IndexItemClickListener
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_index.*

/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : EmallDelegate() {

    private var adapter: OrderAdapter? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    override fun initial() {
        initRefreshLayout()
        initRecyclerView()
    }

    fun initRefreshLayout() {
        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
        all_srl.setProgressViewOffset(true, 120, 300)
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recycler_view_index.layoutManager = manager

        adapter = OrderAdapter(R.layout.item_order, datas)
        recycler_view_index.adapter = adapter
    }
}