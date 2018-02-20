package com.example.emall_core.ui.refresh

import android.support.v4.widget.SwipeRefreshLayout
import com.example.emall_core.app.Emall
import com.alibaba.fastjson.JSON
import com.example.emall_core.util.log.EmallLogger
import android.support.v7.widget.RecyclerView
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.net.RestClient
import com.example.emall_core.ui.recycler.DataConverter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter


/**
 * Created by lixiang on 17/02/2018.
 */
class RefreshHandler private constructor(private val REFRESH_LAYOUT: SwipeRefreshLayout,
                                         private val RECYCLERVIEW: RecyclerView,
                                         private val CONVERTER: DataConverter,
                                         private val BEAN: PagingBean) : SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var mAdapter: MultipleRecyclerAdapter ?= null
    private var bannerResponse = String()
//    private var converter = DataConverter()
    init {
        REFRESH_LAYOUT.setOnRefreshListener(this)
    }

    private fun refresh() {
        REFRESH_LAYOUT.isRefreshing = true
        Emall().getHandler()!!.postDelayed(Runnable {
            //进行一些网络请求
            REFRESH_LAYOUT.isRefreshing = false
        }, 1000)
    }

    fun firstPage(bannerUrl: String,url: String) {
        BEAN.setDelayed(1000)
        /**
         * get banner
         */
        RestClient().builder()
                .url(bannerUrl)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d("FIRSTPAGE")

//                        BEAN.setTotal(100).setPageSize(6)
                        //设置Adapter
//                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response))
//                        EmallLogger.d(CONVERTER.setJsonData(response))
//                        mAdapter!!.setOnLoadMoreListener(this@RefreshHandler, RECYCLERVIEW)
//                        RECYCLERVIEW.adapter = mAdapter
//                        BEAN.addIndex()
                        EmallLogger.d(CONVERTER.setJsonData(response).convert())

                    }
                })
                .build()
                .get()

        RestClient().builder()
                .url(url)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val `object` = JSON.parseObject(response)
//                        BEAN.setTotal(`object`.getInteger("total"))
//                                .setPageSize(`object`.getInteger("page_size"))
                        BEAN.setTotal(100).setPageSize(6)
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response))
                        EmallLogger.d(CONVERTER.setJsonData(response).ENTITIES[0])
                        mAdapter!!.setOnLoadMoreListener(this@RefreshHandler, RECYCLERVIEW)
                        RECYCLERVIEW.adapter = mAdapter
                        BEAN.addIndex()
                    }
                })
                .build()
                .get()
    }

    private fun paging(url: String) {
        val pageSize = BEAN.getPageSize()
        val currentCount = BEAN.getCurrentCount()
        val total = BEAN.getTotal()
        val index = BEAN.getPageIndex()

        if (mAdapter!!.data.size < pageSize || currentCount >= total) {
            mAdapter!!.loadMoreEnd(true)
        } else {
            Emall().getHandler()!!.postDelayed(Runnable {
                RestClient().builder()
                        .url(url + index)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                EmallLogger.json("paging", response)
                                CONVERTER.clearData()
                                mAdapter!!.addData(CONVERTER.setJsonData(response).convert())
                                //累加数量
                                BEAN.setCurrentCount(mAdapter!!.data.size)
                                mAdapter!!.loadMoreComplete()
                                BEAN.addIndex()
                            }
                        })
                        .build()
                        .get()
            }, 1000)
        }
    }

    override fun onRefresh() {
        refresh()
    }


    override fun onLoadMoreRequested() {
        paging("refresh.php?index=")
    }

    companion object {

        fun create(swipeRefreshLayout: SwipeRefreshLayout,
                   recyclerView: RecyclerView, converter: DataConverter): RefreshHandler {
            return RefreshHandler(swipeRefreshLayout, recyclerView, converter, PagingBean())
        }
    }
}
