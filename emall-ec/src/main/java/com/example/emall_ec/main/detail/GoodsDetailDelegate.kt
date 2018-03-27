package com.example.emall_ec.main.detail

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
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
import kotlinx.android.synthetic.main.delegate_goods_detail.*
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
import com.example.emall_ec.main.demand.FillOrderDelegate
import com.example.emall_ec.main.demand.data.CommoditySubmitDemandBean
import com.example.emall_ec.main.detail.data.SceneDetailBean
import com.example.emall_ec.main.index.dailypic.adapter.HomePageListViewAdapter
import com.example.emall_ec.main.index.dailypic.data.BannerBean
import com.example.emall_ec.main.index.dailypic.data.HomePageBean
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.delegate_daily_pic.*


/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate(), OnTabSelectListener {


    var sceneDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var sceneDetail = SceneDetailBean()
    var lati = "S"
    var longi = "W"
    var mMapView: MapView? = null
    var mBaiduMap: BaiduMap? = null
    var commoditySubmitDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var commoditySubmitDemandBean = CommoditySubmitDemandBean()
    var sceneData = SceneDetailBean().data
    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initViews()
        resolveConflict()
        sceneDetailParams!!["productId"] = arguments.getString("productId")
        sceneDetailParams!!["type"] = arguments.getString("type")
        EmallLogger.d(sceneDetailParams!!["productId"]!!)
        getData(sceneDetailParams!!)
        video_goods_buy_now_btn.setOnClickListener {
            commoditySubmitDemand()

        }

        goods_detail_scrollview.viewTreeObserver.addOnScrollChangedListener {
            if (goods_detail_scrollview != null) {
                val scrollY = ScreenUtil.px2dip(context, goods_detail_scrollview.scrollY.toFloat())
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
            supportDelegate.pop()
        }
    }

    private fun commoditySubmitDemand() {
        commoditySubmitDemandParams!!["productId"] = arguments.getString("productId")
        commoditySubmitDemandParams!!["geo"] = ""
        commoditySubmitDemandParams!!["status"] = "0"
        commoditySubmitDemandParams!!["type"] = "1"
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/commoditySubmitDemand")
                .params(commoditySubmitDemandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commoditySubmitDemandBean = Gson().fromJson(response, CommoditySubmitDemandBean::class.java)
                        val delegate: FillOrderDelegate = FillOrderDelegate().create()!!
                        val bundle: Bundle? = Bundle()
                        bundle!!.putString("demandId", commoditySubmitDemandBean.data)
                        bundle.putString("imageUrl",sceneData.imageDetailUrl)
                        bundle.putString("title", sceneData.productId)
                        bundle.putString("time",sceneData.centerTime)
                        delegate.arguments = bundle
                        start(delegate)
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


    override fun onTabSelect(position: Int) {
        when (position) {
            0 -> goods_detail_scrollview.scrollTo(0, 0)
            1 -> goods_detail_scrollview.scrollTo(0, ScreenUtil.dip2px(context, 495.0F))
            2 -> goods_detail_scrollview.scrollTo(0, ScreenUtil.dip2px(context, 850.0F))
        }
    }

    override fun onTabReselect(position: Int) {

    }

    private fun setSceneData(sceneDetail: SceneDetailBean) {
        sceneData = sceneDetail.data

        EmallLogger.d(sceneData)
        detail_gather_time_tv.text = String.format(resources.getString(R.string.video_detail_gather_time), sceneData.centerTime)
        detail_angle_tv.text = String.format(resources.getString(R.string.video_detail_angle), sceneData.swingSatelliteAngle)

        Glide.with(context)
                .load(sceneData.imageDetailUrl)
                .into(video_goods_detail_title_image)
        drawMap(getGeo(sceneData.geo))
        scene_detail_promotion_description_tv.text = sceneData.promotionDescription
        scene_detail_sale_price_tv.text = String.format(resources.getString(R.string.video_detail_sale_price), sceneData.salePrice)
        scene_detail_original_price_tv.text = String.format(resources.getString(R.string.video_detail_original_price), sceneData.originalPrice)
        changeColor(sceneData.serviceDescription)
        detail_product_id_tv.text = sceneData.productId
        detail_satellite_tv.text = sceneData.satelliteId
        detail_ratio_tv.text = sceneData.resolution
        detail_area_tv.text = String.format(resources.getString(R.string.video_detail_area), sceneData.size)
        detail_cloud_tv.text = String.format(resources.getString(R.string.video_detail_cloud), sceneData.cloud)
        judgeLati_longi(sceneData.latitude, sceneData.longitude)
        detail_location_tv.text = String.format(resources.getString(R.string.video_detail_location), sceneData.longitude, longi, sceneData.latitude, lati)
        detail_coordinate_tv.text = sceneData.sensor
    }

    private fun setVideoData(videoDetail: VideoDetailBean) {
        val data = videoDetail.data
        EmallLogger.d(data)
        video_detail_title_tv.text = data.title
        detail_gather_time_tv.text = String.format(resources.getString(R.string.video_detail_gather_time), data.startTime)
        detail_angle_tv.text = String.format(resources.getString(R.string.video_detail_angle), data.rollSatelliteAngleMajor)

        Glide.with(context)
                .load(data.imageDetailUrl)
                .into(video_goods_detail_title_image)
        drawMap(getGeo(data.geo))
        video_detail_promotion_description_tv.text = data.promotionDescription
        video_detail_sale_price_tv.text = String.format(resources.getString(R.string.video_detail_sale_price), data.salePrice)
        video_detail_original_price_tv.text = String.format(resources.getString(R.string.video_detail_original_price), data.originalPrice)
        changeColor(data.serviceDescription)
        detail_product_id_tv.text = data.productId
        detail_satellite_tv.text = data.satelliteId
        detail_ratio_tv.text = data.resolution
        detail_area_tv.text = String.format(resources.getString(R.string.video_detail_area), data.size)
        detail_cloud_tv.text = String.format(resources.getString(R.string.video_detail_cloud), data.cloud)
        judgeLati_longi(data.latitude, data.longitude)
        detail_location_tv.text = String.format(resources.getString(R.string.video_detail_location), data.longitude, longi, data.latitude, lati)
        detail_coordinate_tv.text = data.sensor
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

    private fun judgeLati_longi(latitude: String, longitude: String) {
        if (java.lang.Double.parseDouble(longitude) > 0) {
            longi = "E"
        } else if (java.lang.Double.parseDouble(longitude) == 0.0) {
            longi = ""
        }

        if (java.lang.Double.parseDouble(latitude) > 0) {
            lati = "N"
        } else if (java.lang.Double.parseDouble(latitude) == 0.0) {
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
        val mTitles = arrayOf("预览图", " 参数", "位置 ")
        val mIconUnselectIds = intArrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect)
        val mIconSelectIds = intArrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select)
        val mTabEntities: ArrayList<CustomTabEntity>? = ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            video_detail_tablayout_ctl.setTabData(mTabEntities)
        }
    }

    private fun getData(sceneDetailParams: WeakHashMap<String, Any>) {
        EmallLogger.d(sceneDetailParams["type"]!!)
        if (sceneDetailParams["type"] == "1") {
            RestClient().builder()
                    .url(String.format("http://59.110.164.214:8024/global/sceneDetail?productId=%s&type=%s",
                            arguments.getString("productId"),
                            arguments.getString("type")))
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.d(response)
                            sceneDetail = Gson().fromJson(response, SceneDetailBean::class.java)
                            EmallLogger.d(sceneDetail)
                            setSceneData(sceneDetail)
                        }
                    })
                    .failure(object : IFailure {
                        override fun onFailure() {
                            EmallLogger.d("fail")
                        }
                    })
                    .error(object : IError {
                        override fun onError(code: Int, msg: String) {
                            EmallLogger.d(code)
                        }
                    })
                    .build()
                    .get()
            video_mark.visibility = View.INVISIBLE
            video_goods_detail_mask_iv.visibility = View.INVISIBLE
            play_btn.visibility = View.INVISIBLE
            video_rl.visibility = View.GONE
        }

    }

    private fun resolveConflict() {
        mMapView = activity.findViewById<MapView>(R.id.video_detail_map) as MapView
        mBaiduMap = mMapView!!.map
        val v = mMapView!!.getChildAt(0)
        v.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                goods_detail_scrollview.requestDisallowInterceptTouchEvent(false)
            } else {
                goods_detail_scrollview.requestDisallowInterceptTouchEvent(true)
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

    override fun onResume() {
        super.onResume()

    }
}
