package com.example.emall_ec.main.index.move.refresh

import android.support.v4.widget.SwipeRefreshLayout
import com.example.emall_core.app.Emall
import com.example.emall_core.util.log.EmallLogger
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_core.app.ApiService
import com.example.emall_core.ui.NetUtils
import com.example.emall_ec.main.index.IndexDelegate
import com.example.emall_ec.main.index.move.recycler.DataConverter
import com.example.emall_ec.main.index.move.recycler.MultipleItemEntity
import com.example.emall_ec.main.index.move.recycler.MultipleRecyclerAdapter
import org.greenrobot.greendao.generator.Index
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*


/**
 * Created by lixiang on 17/02/2018. 刷新助手
 */
class RefreshHandler private constructor(private val REFRESH_LAYOUT: SwipeRefreshLayout,
                                         private val RECYCLERVIEW: RecyclerView,
                                         private var BANNER_CONVERTER: DataConverter,
                                         private val EVERY_DAY_PIC_CONVERTER: DataConverter,
                                         private val HORIZONTAL_SCROLL_CONVERTER: DataConverter,
                                         private val THE_THREE_CONVERTER: DataConverter,
                                         private val GUESS_LIKE_CONVERTER: DataConverter,
                                         private val CONVERTER: DataConverter,
                                         private val BEAN: PagingBean) : SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    var homePageParams: WeakHashMap<String, Any>? = WeakHashMap()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null

    var delegate : IndexDelegate ?= null
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

    fun getData(d: IndexDelegate) {
        delegate = d
        retrofit = Retrofit.Builder()
                .baseUrl("http://59.110.164.214:8024/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.homePageSlide()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response!!.body() != null) {
                    val data: MutableList<MultipleItemEntity>? = mutableListOf()
                    EmallLogger.d(response)
                    if (!data!!.isEmpty()) {
                        data.clear()
                    }
                    BANNER_CONVERTER.clearData()
                    EVERY_DAY_PIC_CONVERTER.clearData()
                    THE_THREE_CONVERTER.clearData()
                    HORIZONTAL_SCROLL_CONVERTER.clearData()
                    GUESS_LIKE_CONVERTER.clearData()
                    val bannerSize = BANNER_CONVERTER.setJsonData(response.body().toString()).bannerConvert().size
                    for (i in 0 until bannerSize) {
                        data.add(BANNER_CONVERTER.setJsonData(response.body().toString()).bannerConvert()[i])
                    }

                    getDailyPicTitle(data)
                    println("1 " + data.size)
                } else {
                    EmallLogger.d("error")
                }
            }
        })
    }

    fun getDailyPicTitle(data: MutableList<MultipleItemEntity>?) {
        homePageParams!!["pageSize"] = "10"
        homePageParams!!["pageNum"] = "1"
        retrofit = Retrofit.Builder()
                .baseUrl("http://202.111.178.10:28085/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.homePage(homePageParams!!["pageSize"].toString(), homePageParams!!["pageNum"].toString())
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response!!.body() != null) {
                    data!!.add(EVERY_DAY_PIC_CONVERTER.setJsonData(response.body().toString()).everyDayPicConvert()[0])
                    println("2 " + data.size)
                    getUnit2(data)
                } else {
                    EmallLogger.d("error")
                }
            }
        })
    }

    fun getUnit2(data: MutableList<MultipleItemEntity>?) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.homePageUnits()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response!!.body() != null) {
                    HORIZONTAL_SCROLL_CONVERTER.clearData()

                    data!!.add(THE_THREE_CONVERTER.setJsonData(response.body().toString()).theThreeConvert()[0])
                    data.add(HORIZONTAL_SCROLL_CONVERTER.setJsonData(response.body().toString()).horizontalScrollConvert()[0])
                    data.add(GUESS_LIKE_CONVERTER.setJsonData(response.body().toString()).guessLikeConvert()[0])
                    var mAdapter: MultipleRecyclerAdapter? = MultipleRecyclerAdapter.create(delegate, data)
                    RECYCLERVIEW.adapter = mAdapter
                    println("3 " + data.size)
                } else {
                    EmallLogger.d("error")
                }
            }
        })
    }

    override fun onRefresh() {
        refresh()
    }


    override fun onLoadMoreRequested() {
//        paging("refresh.php?index=")
    }

    companion object {

        fun create(swipeRefreshLayout: SwipeRefreshLayout,
                   recyclerView: RecyclerView,
                   banner_converter: DataConverter,
                   every_day_pic_converter: DataConverter,
                   horizontal_scroll_converter: DataConverter,
                   the_three_converter: DataConverter,
                   guess_like_converter: DataConverter,
                   converter: DataConverter): RefreshHandler {
            return RefreshHandler(swipeRefreshLayout,
                    recyclerView,
                    banner_converter,
                    every_day_pic_converter,
                    horizontal_scroll_converter,
                    the_three_converter,
                    guess_like_converter,
                    converter,
                    PagingBean())
        }
    }
}
