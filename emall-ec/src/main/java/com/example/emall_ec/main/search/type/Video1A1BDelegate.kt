package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.CustomLoadMoreView
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.VideoClassifyAdapter
import com.example.emall_ec.main.classify.data.VideoSearch
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.order.state.adapter.ObligationListAdapter
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.main.search.SearchResultDelegate
import com.example.emall_ec.main.search.data.VideoSearchBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_obligation.*
import kotlinx.android.synthetic.main.delegate_video1a1b.*
import retrofit2.Retrofit
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class Video1A1BDelegate : EmallDelegate() {
    private var videoSearchParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var videoSearchBean = VideoSearchBean()
    var screenIsShow = false
    var mAdapter: VideoClassifyAdapter? = null
    var glm: GridLayoutManager? = null
    var gatherTimeFlag = false
    var priceFlag = false
    var itemSize = 0
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_video1a1b
    }

    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        setSwipeBackEnable(false)
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        videoSearchParams!!["geo"] = Optics1Delegate().geoFormat(sp.getString("GEO", ""))
        videoSearchParams!!["type"] = "0"

        video1a1b_gather_time_rl.setOnClickListener {

            video1a1b_price_tv.setTextColor(Color.parseColor("#9B9B9B"))
            video1a1b_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
            video1a1b_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)

            gatherTimeFlag = if (!gatherTimeFlag) {
                video1a1b_gather_time_tv.setTextColor(Color.parseColor("#B80017"))
                video1a1b_gather_time_iv.setBackgroundResource(R.drawable.ic_up_red)
                true
            } else {
                video1a1b_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
                video1a1b_gather_time_iv.setBackgroundResource(R.drawable.ic_down_gray)
                false
            }
        }

        video1a1b_price_rl.setOnClickListener {
            video1a1b_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
            video1a1b_gather_time_iv.setBackgroundResource(R.drawable.ic_down_gray)


            priceFlag = if (!priceFlag) {
                video1a1b_price_tv.setTextColor(Color.parseColor("#B80017"))
                video1a1b_price_up_iv.setBackgroundResource(R.drawable.ic_up_red)
                video1a1b_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)
                true
            } else {
                video1a1b_price_tv.setTextColor(Color.parseColor("#B80017"))
                video1a1b_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
                video1a1b_price_down_iv.setBackgroundResource(R.drawable.ic_down_red)
                false
            }
        }

        video_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mAdapter = null
            video_srl.isRefreshing = true
            Handler().postDelayed({
                getData()
                video_srl.isRefreshing = false
            }, 1200)
        })

        initVideoGlm()
        getData()
    }

    private fun initVideoGlm() {
        glm = GridLayoutManager(context, 1)
        glm!!.isSmoothScrollbarEnabled = true
        glm!!.isAutoMeasureEnabled = true
        video1a1b_rv.addItemDecoration(GridSpacingItemDecoration(1, 30, true))
        video1a1b_rv.layoutManager = glm
        video1a1b_rv.setHasFixedSize(true)
        video1a1b_rv.isNestedScrollingEnabled = false
    }

    private fun getData() {
//        EmallLogger.d(videoSearchParams!!)
//        RestClient().builder()
//                .url("http://59.110.164.214:8024/global/videoSearch")
//                .params(videoSearchParams!!)
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        EmallLogger.d(response)
//                        videoSearchBean = Gson().fromJson(response, VideoSearchBean::class.java)
//                        if(videoSearchBean.status != 103) {
//                            if (video_rv_rl.visibility == View.GONE){
//                                video_rv_rl.visibility = View.VISIBLE
//                                video_no_result.visibility = View.GONE
//                                video_top_bar.visibility = View.VISIBLE
//                            }
//                            val size = videoSearchBean.data.size
//                            val data: MutableList<Model> ?= mutableListOf()
//                            for (i in 0 until size) {
//                                val model = Model()
//                                model.imageUrl = videoSearchBean.data[i].detailPath
//                                model.price = videoSearchBean.data[i].price
//                                model.time = videoSearchBean.data[i].startTime
//                                model.title = videoSearchBean.data[i].title
//                                model.productType = "3"
//                                data!!.add(model)
//                            }
//                            initRecyclerView(data!!, video1a1b_rv, videoSearchBean.data.size)
//                        }else{
//                            video_rv_rl.visibility = View.GONE
//                            video_no_result.visibility = View.VISIBLE
//                            video_top_bar.visibility = View.GONE
//                        }
//                    }
//                })
//                .failure(object : IFailure {
//                    override fun onFailure() {
//
//                    }
//                })
//                .error(object : IError {
//                    override fun onError(code: Int, msg: String) {
//
//                    }
//                })
//                .build()
//                .post()

        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.videoSearch(videoSearchParams!!["geo"].toString(), videoSearchParams!!["type"].toString(), "10", "1")
        println("~~~~~~~~" + videoSearchParams!!["geo"].toString())
        call.enqueue(object : retrofit2.Callback<VideoSearchBean> {
            override fun onResponse(call: retrofit2.Call<VideoSearchBean>, response: retrofit2.Response<VideoSearchBean>) {
                if (response.body() != null) {
                    videoSearchBean = response.body()!!
                    EmallLogger.d(response)
                    if (videoSearchBean.status != 103) {
                        if (video_rv_rl.visibility == View.GONE) {
                            video_rv_rl.visibility = View.VISIBLE
                            video_no_result.visibility = View.GONE
                            video_top_bar.visibility = View.VISIBLE
                        }
                        val size = videoSearchBean.data.searchReturnDtoList.size
                        val data: MutableList<Model>? = mutableListOf()
                        for (i in 0 until size) {
                            val model = Model()
                            model.imageUrl = videoSearchBean.data.searchReturnDtoList[i].detailPath
                            model.price = videoSearchBean.data.searchReturnDtoList[i].price
                            model.time = videoSearchBean.data.searchReturnDtoList[i].startTime
                            model.title = videoSearchBean.data.searchReturnDtoList[i].title
                            model.productId = videoSearchBean.data.searchReturnDtoList[i].productId
                            model.productType = "3"
                            data!!.add(model)
                        }
                        initRecyclerView(data!!, video1a1b_rv, videoSearchBean.data.searchReturnDtoList.size)
                    } else {
                        video_rv_rl.visibility = View.GONE
                        video_no_result.visibility = View.VISIBLE
                        video_top_bar.visibility = View.GONE
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<VideoSearchBean>, throwable: Throwable) {}
        })
    }

    private fun initRecyclerView(data: MutableList<Model>, recyclerView: RecyclerView, size: Int) {
        val mAdapter: VideoClassifyAdapter? = VideoClassifyAdapter(R.layout.item_classify_video, data, glm)
        mAdapter!!.setOnLoadMoreListener {
            itemSize += 10
            EmallLogger.d(size)
            if (size > itemSize) {
                EmallLogger.d("In le me ")
                loadMoreData()
            } else {
//                mAdapter!!.loadMoreFail()
            }
        }
        mAdapter!!.setLoadMoreView(CustomLoadMoreView())

        recyclerView.adapter = mAdapter
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", data[position].productId)
            bundle.putString("type", "3")
            delegate!!.arguments = bundle
            getParentDelegate<SearchResultDelegate>().start(delegate)
        }
    }

    private fun loadMoreData() {
        EmallLogger.d("暂无")
    }
}
