package com.example.emall_ec.main.detail

import android.graphics.Paint
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
import com.baidu.mapapi.map.*
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.data.VideoDetailBean
import com.example.emall_ec.main.order.OrderDelegate
import com.google.gson.Gson
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*
import com.example.emall_core.util.view.ScreenUtil
import com.example.emall_core.util.view.SpannableBuilder
import com.baidu.mapapi.model.LatLng
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.delegate_classify.*


/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate(), OnTabSelectListener {


    var sceneDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var videoDetail = VideoDetailBean()
    var lati = "S"
    var longi = "W"
    var mMapView: MapView? = null
    var mBaiduMap: BaiduMap? = null
    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_goods_detail
    }


    override fun initial() {
        RestClient().builder()
//                    .url("http://59.110.164.214:8024/global/sceneDetail")
//                    .params(sceneDetailParams)
//                    .url(String.format("http://59.110.164.214:8024/global/sceneDetail?productId=%s&type=1","JL101A_PMS_20180316184644_000021554_204_0011_001_L1_PAN"))
                .url("http://59.110.164.214:8024/global/homePageUnits")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
//                            videoDetail = Gson().fromJson(response, VideoDetailBean::class.java)
//                            EmallLogger.d(videoDetail)
//                            setData(videoDetail)
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {

                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {
                        EmallLogger.d(code)
                    }
                })
                .build()
                .get()
        initViews()
        resolveConflict()

        sceneDetailParams!!["productId"] = arguments["productId"]
        sceneDetailParams!!["type"] = arguments["type"]
        getData(sceneDetailParams!!)
        video_goods_buy_now_btn.setOnClickListener {
            val delegate: OrderDelegate = OrderDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            start(delegate)
        }

        if (video_goods_detail_scrollview != null){
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

        video_detail_tablayout_ctl.setOnTabSelectListener(this)
        video_goods_detail_toolbar.setNavigationOnClickListener {
            _mActivity.onBackPressed()
        }
    }

    override fun onTabSelect(position: Int) {
        when (position) {
            0 -> video_goods_detail_scrollview.scrollTo(0, 0)
            1 -> video_goods_detail_scrollview.scrollTo(0, ScreenUtil.dip2px(context, 495.0F))
            2 -> video_goods_detail_scrollview.scrollTo(0, ScreenUtil.dip2px(context, 850.0F))
        }
    }

    override fun onTabReselect(position: Int) {

    }

    private fun setData(videoDetail: VideoDetailBean) {
        val data = videoDetail.data
        EmallLogger.d(data)
        Glide.with(context)
                .load(data.imageDetailUrl)
                .into(video_goods_detail_title_image)

        drawMap(getGeo(data.geo))
        video_detail_title_tv.text = data.title
        video_detail_promotion_description_tv.text = data.promotionDescription
        video_detail_sale_price_tv.text = String.format(resources.getString(R.string.video_detail_sale_price), data.salePrice)
        video_detail_original_price_tv.text = String.format(resources.getString(R.string.video_detail_original_price), data.originalPrice)
        changeColor(data.serviceDescription)
        video_detail_product_id_tv.text = data.productId
        video_detail_satellite_tv.text = data.satelliteId
        video_detail_ratio_tv.text = data.resolution
        video_detail_gather_time_tv.text = String.format(resources.getString(R.string.video_detail_gather_time), data.startTime)
        video_detail_area_tv.text = String.format(resources.getString(R.string.video_detail_area), data.size)
        video_detail_cloud_tv.text = String.format(resources.getString(R.string.video_detail_cloud), data.cloud)
        video_detail_angle_tv.text = String.format(resources.getString(R.string.video_detail_angle), data.rollSatelliteAngleMajor)
        judgeLati_longi(data)
        video_detail_location_tv.text = String.format(resources.getString(R.string.video_detail_location), data.longitude, longi, data.latitude, lati)
        video_detail_coordinate_tv.text = data.sensor
    }

    private fun drawMap(geo: MutableList<Array<String>>) {
        val pt1 = LatLng(java.lang.Double.parseDouble(geo[0][0]), java.lang.Double.parseDouble(geo[0][1]))
        val pt2 = LatLng(java.lang.Double.parseDouble(geo[1][0]), java.lang.Double.parseDouble(geo[1][1]))
        val pt3 = LatLng(java.lang.Double.parseDouble(geo[2][0]), java.lang.Double.parseDouble(geo[2][1]))
        val pt4 = LatLng(java.lang.Double.parseDouble(geo[3][0]), java.lang.Double.parseDouble(geo[3][1]))
        val pts = ArrayList<LatLng>()
        pts.add(pt1)
        pts.add(pt2)
        pts.add(pt3)
        pts.add(pt4)

        val polygonOption = PolygonOptions()
                .points(pts)
                .stroke(Stroke(5, -0x55ff0100))
                .fillColor(-0x55000100)

        mBaiduMap!!.addOverlay(polygonOption)
    }

    private fun getGeo(geo: String): MutableList<Array<String>> {
        val geos: MutableList<Array<String>> = mutableListOf()
        val temp3 = geo.substring(geo.indexOf("[[") + 1, geo.indexOf("]}"))
        val temp2 = temp3.replace("[", "")
        val temp = temp2.replace("]", "")
        EmallLogger.d(temp)
        val array = temp.split(',')
        geos.add(arrayOf(array[0], array[1]))
        geos.add(arrayOf(array[2], array[3]))
        geos.add(arrayOf(array[4], array[5]))
        geos.add(arrayOf(array[6], array[7]))
        return geos
    }

    private fun changeColor(serviceDescription: String) {
        EmallLogger.d(serviceDescription)
        val prefix = serviceDescription.split("，")[0] + "，"
        val tempSuffix = serviceDescription.split("，")[1]
        val time = tempSuffix.substring(0, tempSuffix.length - 3)
        val suffix = tempSuffix.substring(tempSuffix.length - 3, tempSuffix.length)

        video_detail_service_1_tv.text = SpannableBuilder.create(context)
                .append(prefix, R.dimen.text_size, R.color.gray)
                .append(time, R.dimen.text_size, R.color.orange)
                .append(suffix, R.dimen.text_size, R.color.gray)
                .build()
    }

    private fun judgeLati_longi(data: VideoDetailBean.DataBean) {
        if (java.lang.Double.parseDouble(data.longitude) > 0) {
            longi = "E"
        } else if (java.lang.Double.parseDouble(data.longitude) == 0.0) {
            longi = ""
        }

        if (java.lang.Double.parseDouble(data.latitude) > 0) {
            lati = "N"
        } else if (java.lang.Double.parseDouble(data.latitude) == 0.0) {
            lati = ""
        }
    }


    private fun initViews() {
        video_goods_detail_toolbar.title = ""
        video_detail_star_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/star.ttf")
        video_detail_head_set_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/headset.ttf")
        video_detail_original_price_tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
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
        if (sceneDetailParams["type"] == "1"){
            RestClient().builder()
//                    .url("http://59.110.164.214:8024/global/sceneDetail")
//                    .params(sceneDetailParams)
//                    .url(String.format("http://59.110.164.214:8024/global/sceneDetail?productId=%s&type=1","JL101A_PMS_20180316184644_000021554_204_0011_001_L1_PAN"))
                    .url("http://59.110.164.214:8024/global/homePageUnits")
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.d(response)
//                            videoDetail = Gson().fromJson(response, VideoDetailBean::class.java)
//                            EmallLogger.d(videoDetail)
//                            setData(videoDetail)
                        }
                    })
                    .failure(object : IFailure {
                        override fun onFailure() {

                        }
                    })
                    .error(object : IError {
                        override fun onError(code: Int, msg: String) {
                            EmallLogger.d(code)
                        }
                    })
                    .build()
                    .get()
        }

    }

    private fun resolveConflict() {
        mMapView = activity.findViewById<MapView>(R.id.video_detail_map) as MapView
        mBaiduMap = mMapView!!.map
        val v = mMapView!!.getChildAt(0)
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
