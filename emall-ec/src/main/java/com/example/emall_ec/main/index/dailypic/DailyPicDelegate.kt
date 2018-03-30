package com.example.emall_ec.main.index.dailypic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.GlideImageLoader
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.index.dailypic.adapter.HomePageListViewAdapter
import com.example.emall_ec.main.index.dailypic.data.BannerBean
import com.example.emall_ec.main.index.dailypic.data.HomePageBean
import com.example.emall_ec.main.index.dailypic.pic.Page1Delegate
import com.example.emall_ec.main.index.dailypic.pic.PicDetailDelegate
import com.google.gson.Gson
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.delegate_daily_pic.*
import java.util.*
import android.R.id.edit
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences





/**
 * Created by lixiang on 2018/3/21.
 */
class DailyPicDelegate : BottomItemDelegate(), OnBannerListener {
    override fun OnBannerClick(position: Int) {

    }

    var bannerBean = BannerBean()
    var homePageBean = HomePageBean()
    var homePageParams: WeakHashMap<String, Any>? = WeakHashMap()
    var adapter = HomePageListViewAdapter()
    var homePageData : MutableList<HomePageBean.DataBean.MixedContentListBean> ?= mutableListOf()
    var content: MutableList<BannerBean.DataBean> = mutableListOf()
    val images = mutableListOf<String>()
    val titles = mutableListOf<String>()
    fun create():DailyPicDelegate?{
        return DailyPicDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_daily_pic
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {

        daily_pic_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(daily_pic_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        homePageParams!!["pageSize"] = "10"
        homePageParams!!["pageNum"] = "1"

        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/homePageSlide")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        bannerBean = Gson().fromJson(response, BannerBean::class.java)
                        content = bannerBean.data
                        setBanner(content)


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

        daily_pic_lv.setOnItemClickListener { adapterView, view, i, l ->
            if (homePageData!![i - 1].type == "1"){
//                Page1Delegate().create()



                EmallLogger.d(homePageData!![i - 1].contentName)
                val delegate : PicDetailDelegate = PicDetailDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("imageId", homePageData!![i - 1].contentId)
//                bundle.putString("type", "1")
                delegate.arguments = bundle
                start(delegate)
            }

//            else {
//                presenter.getVideoPicDetail(content[i - 1].contentId)
//            }
        }

    }

    fun setBanner(data: MutableList<BannerBean.DataBean>) {
        for (i in data) {
            images.add(i.imageUrl)
            titles.add(i.title)
        }
        val headerView = layoutInflater.inflate(R.layout.item_banner_header, null)
        val banner: Banner = headerView.findViewById(R.id.banner)
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(images)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setBannerTitles(titles)
        banner.start()
        banner.setOnBannerListener {
//            start(PicDetailDelegate().create())
        }
        daily_pic_lv.addHeaderView(headerView)
        banner.startAutoPlay()
    }
}