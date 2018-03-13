package com.example.emall_ec.main.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.index.VideoDetailFields
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.delegate_video_goods_detail.*
import android.view.MotionEvent
import com.baidu.mapapi.map.MapView
import com.example.emall_core.net.RestCreator.params
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.data.VideoDetailBean
import com.example.emall_ec.main.order.OrderDelegate
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_classify.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate() {
    var DATA: MutableList<MultipleItemEntity>? = mutableListOf()
    var sceneDetailParams : WeakHashMap<String, Any>? = WeakHashMap()
    var videoDetail = VideoDetailBean()
    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_goods_detail
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

    }

    override fun initial() {
        video_goods_detail_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(video_goods_detail_toolbar)
        video_goods_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
        val mTitles = arrayOf("预览图", "参数", "位置")
        val mIconUnselectIds = intArrayOf(
                R.mipmap.tab_home_unselect,
                R.mipmap.tab_speech_unselect,
                R.mipmap.tab_contact_unselect)
        val mIconSelectIds = intArrayOf(
                R.mipmap.tab_home_select,
                R.mipmap.tab_speech_select,
                R.mipmap.tab_contact_select)
        var mTabEntities: ArrayList<CustomTabEntity>? = ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            tl_6.setTabData(mTabEntities)
        }

//        val url: String = if (FileUtil.checkEmulator()) {
//            "http://10.0.2.2:3033/data"
//        } else {
//            "http://10.10.90.11:8086/global/sceneDetail?productId=JL101A_PMS_20160820102530_000012697_101_0021_001_L1_PAN&type=1"
//        }

        sceneDetailParams!!["productId"] = "JL101A_PMS_20160820102530_000012697_101_0021_001_L1_PAN"
        sceneDetailParams!!["type"] = "1"

        RestClient().builder()
//                .url("http://10.10.90.11:8086/global/sceneDetail")//EMULATOR
                .url("http://192.168.1.36:3005/data")//EMULATOR
//                .params(sceneDetailParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        videoDetail = Gson().fromJson(response, VideoDetailBean::class.java)
                        Glide.with(context)
                                .load(videoDetail.data.imageDetailUrl)
                                .into(video_goods_detail_title_image)
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

        EmallLogger.d(DATA!!)


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

        video_goods_buy_now_btn.setOnClickListener{
            val delegate: OrderDelegate = OrderDelegate().create()!!
            val bundle : Bundle ?= Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            start(delegate)
        }

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
