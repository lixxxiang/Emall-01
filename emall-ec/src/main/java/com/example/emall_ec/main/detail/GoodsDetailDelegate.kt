package com.example.emall_ec.main.detail

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.R
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.delegate_video_goods_detail.*
import android.view.MotionEvent
import com.baidu.mapapi.map.MapView
import com.example.emall_ec.main.detail.data.VideoDetailBean
import com.example.emall_ec.main.order.OrderDelegate
import com.google.gson.Gson
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*
import com.example.emall_core.util.view.ScreenUtil


/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate() {
    var sceneDetailParams : WeakHashMap<String, Any>? = WeakHashMap()
    var videoDetail = VideoDetailBean()
    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_goods_detail
    }


    override fun initial() {
        initViews()

        sceneDetailParams!!["productId"] = "JL101A_PMS_20160820102530_000012697_101_0021_001_L1_PAN"
        sceneDetailParams!!["type"] = "1"
        getData(sceneDetailParams!!)
        resolveConflict()
        setData(videoDetail)
        video_goods_buy_now_btn.setOnClickListener{
            val delegate: OrderDelegate = OrderDelegate().create()!!
            val bundle : Bundle ?= Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            start(delegate)
        }

        video_goods_detail_scrollview.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = ScreenUtil.px2dip(context, video_goods_detail_scrollview.scrollY.toFloat())
            when {
                scrollY < 491 -> video_detail_tablayout_ctl.currentTab = 0
                scrollY in 491..848 -> {
                    video_detail_tablayout_ctl.currentTab = 1
                }
                scrollY > 844 -> video_detail_tablayout_ctl.currentTab = 2
            }
        }
    }

    private fun setData(videoDetail: VideoDetailBean) {
        val data = videoDetail.data
        Glide.with(context)
                .load(data.imageDetailUrl)
                .into(video_goods_detail_title_image)
        video_detail_title_tv.text = data.title
        video_detail_promotion_description_tv.text = data.promotionDescription

    }


    private fun initViews() {
        video_goods_detail_toolbar.title = ""
        video_detail_star_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/star.ttf")
        video_detail_head_set_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/headset.ttf")

        (activity as AppCompatActivity).setSupportActionBar(video_goods_detail_toolbar)
        video_goods_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
        val mTitles = arrayOf("预览图", "参数", "位置")
        val mIconUnselectIds = intArrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect)
        val mIconSelectIds = intArrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select)
        val mTabEntities: ArrayList<CustomTabEntity>? = ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            video_detail_tablayout_ctl.setTabData(mTabEntities)
        }
    }

    private fun getData(sceneDetailParams: WeakHashMap<String, Any>) {
        RestClient().builder()
                .url("http://10.10.90.11:8086/global/sceneDetail")//EMULATOR
//                .url("http://192.168.1.36:3005/data")//EMULATOR
                .params(sceneDetailParams)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        videoDetail = Gson().fromJson(response, VideoDetailBean::class.java)
                        setData(videoDetail)

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

    private fun resolveConflict() {
        val mMapView = activity.findViewById(R.id.video_detail_map) as MapView
        val v = mMapView.getChildAt(0)
        v.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                video_goods_detail_scrollview.requestDisallowInterceptTouchEvent(false)
            } else {
                video_goods_detail_scrollview.requestDisallowInterceptTouchEvent(true)
            }
            false
        })
    }


    class TabEntity(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {

        override fun getTabTitle(): String {
            return title
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
