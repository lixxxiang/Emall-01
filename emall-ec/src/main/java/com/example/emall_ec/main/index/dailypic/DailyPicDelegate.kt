package com.example.emall_ec.main.index.dailypic

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.index.dailypic.adapter.HomePageListViewAdapter
import com.example.emall_ec.main.index.dailypic.data.BannerBean
import com.example.emall_ec.main.index.dailypic.data.HomePageBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_daily_pic.*
import java.util.*

/**
 * Created by lixiang on 2018/3/21.
 */
class DailyPicDelegate : BottomItemDelegate() {
    var bannerBean = BannerBean()
    var homePageBean = HomePageBean()
    var homePageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var adapter = HomePageListViewAdapter()
    var homePageData : MutableList<HomePageBean.DataBean.MixedContentListBean> ?= mutableListOf()

    override fun setLayout(): Any? {
        return R.layout.delegate_daily_pic
    }

    override fun initial() {
        homePageParams!!["pageSize"] = "10"
        homePageParams!!["pageNum"] = "1"

        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/homePageSlide")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        bannerBean = Gson().fromJson(response, BannerBean::class.java)
                        RestClient().builder()
                                .url("http://202.111.178.10:28085/mobile/homePage")
                                .params(homePageParams!!)
                                .success(object : ISuccess {
                                    override fun onSuccess(response: String) {
                                        EmallLogger.d(response)
                                        homePageBean = Gson().fromJson(response, HomePageBean::class.java)
                                        for (i in 0 until homePageBean.data.mixedContentList.size){
                                            homePageData!!.add(homePageBean.data.mixedContentList[i])
                                        }
                                        adapter = HomePageListViewAdapter(context, homePageData)

                                        adapter.notifyDataSetChanged()
                                        daily_pic_lv.adapter = adapter
                                    }
                                })
                                .failure(object : IFailure {
                                    override fun onFailure() {

                                    }
                                })
                                .error(object : IError {
                                    override fun onError(code: Int, msg: String) {

                                    }
                                })
                                .build()
                                .post()
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {

                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {

                    }
                })
                .build()
                .get()

    }
}