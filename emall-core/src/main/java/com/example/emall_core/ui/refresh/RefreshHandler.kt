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
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter


/**
 * Created by lixiang on 17/02/2018. 刷新助手
 */
class RefreshHandler private constructor(private val REFRESH_LAYOUT: SwipeRefreshLayout,
                                         private val RECYCLERVIEW: RecyclerView,
                                         private val BANNER_CONVERTER: DataConverter,
                                         private val EVERY_DAY_PIC_CONVERTER: DataConverter,
                                         private val HORIZONTAL_SCROLL_CONVERTER: DataConverter,
                                         private val THE_THREE_CONVERTER: DataConverter,
                                         private val CONVERTER: DataConverter,
                                         private val BEAN: PagingBean) : SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var mAdapter: MultipleRecyclerAdapter? = null
    private var bannerResponse = String()
    //    private var converter = DataConverter()
    private var data: MutableList<MultipleItemEntity>? = mutableListOf()

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

    fun firstPage(bannerUrl: String, url: String) {
//        BEAN.setDelayed(1000)
        /**
         * get banner
         */
        RestClient().builder()
                .url(bannerUrl)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val bannerSize = BANNER_CONVERTER.setJsonData(response).bannerConvert().size
                        for (i in 0 until bannerSize) {
                            data!!.add(BANNER_CONVERTER.setJsonData(response).bannerConvert()[i])
//                            EmallLogger.d( BANNER_CONVERTER.setJsonData(response).bannerConvert()[i].getField(MultipleFields.BANNERS_LINK))

                        }
//                        EmallLogger.d("BANNERDATA", data!!)
                        data!!.add(EVERY_DAY_PIC_CONVERTER.everyDayPicConvert()[0])
                        EmallLogger.d(HORIZONTAL_SCROLL_CONVERTER.horizontalScrollConvert()[0].getField(MultipleFields.HORIZONTAL_SCROLL))
                        data!!.add(HORIZONTAL_SCROLL_CONVERTER.horizontalScrollConvert()[0])
                        data!!.add(THE_THREE_CONVERTER.theThreeConvert()[0])
//                        RestClient().builder()
//                                .url(url)
//                                .success(object : ISuccess {
//                                    override fun onSuccess(response: String) {
////                        BEAN.setTotal(100).setPageSize(6)
//                                        //设置Adapter
//                                        val content = CONVERTER.setJsonData(response).convert()
//                                        EmallLogger.d("CONTENT", content[0].getField(MultipleFields.CONTENT_DATE))
//
//                                        val size = content.size
//                                        EmallLogger.d(size)
//                                        for (i in 0 until size) {
//                                            data!!.add(content[i])
//                                        }
//                                        EmallLogger.d(data!!.size)

                                        mAdapter = MultipleRecyclerAdapter.create(data)
                                        mAdapter!!.setOnLoadMoreListener(this@RefreshHandler, RECYCLERVIEW)
                                        RECYCLERVIEW.adapter = mAdapter
                                        BEAN.addIndex()
//                                    }
//                                })
//                                .build()
//                                .get()

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
//        paging("refresh.php?index=")
    }

    companion object {

        fun create(swipeRefreshLayout: SwipeRefreshLayout,
                   recyclerView: RecyclerView, banner_converter: DataConverter, every_day_pic_converter: DataConverter, horizontal_scroll_converter: DataConverter, the_three_converter: DataConverter, converter: DataConverter): RefreshHandler {
            return RefreshHandler(swipeRefreshLayout, recyclerView, banner_converter, every_day_pic_converter, converter,horizontal_scroll_converter, the_three_converter, PagingBean())
        }
    }
}
