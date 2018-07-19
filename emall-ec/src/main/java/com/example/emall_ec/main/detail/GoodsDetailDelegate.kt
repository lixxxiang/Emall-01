package com.example.emall_ec.main.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.R
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.delegate_goods_detail.*
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.ZoomControls
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.baidu.mapapi.map.*
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.data.VideoDetailBean
import com.google.gson.Gson
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*
import com.example.emall_core.util.view.ScreenUtil
import com.example.emall_core.util.view.SpannableBuilder
import com.baidu.mapapi.model.LatLng
import com.blankj.utilcode.util.NetworkUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R.string.call
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.coupon.GoodsDetailCouponDelegate
import com.example.emall_ec.main.coupon.data.GetCouponTypeByProductIdBean
import com.example.emall_ec.main.demand.FillOrderDelegate
import com.example.emall_ec.main.demand.data.CommoditySubmitDemandBean
import com.example.emall_ec.main.detail.data.GetCollectionMarkBean
import com.example.emall_ec.main.detail.data.SceneDetailBean
import com.example.emall_ec.main.detail.example.NoctilucenceExampleDelegate
import com.example.emall_ec.main.detail.example.Optics1ExampleDelegate
import com.example.emall_ec.main.detail.example.VideoExampleActivity
import com.example.emall_ec.main.detail.example.VideoExampleDelegate
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.example.emall_ec.main.index.dailypic.video.VitamioPlayerActivity
import com.example.emall_ec.main.me.ContactDelegate
import com.example.emall_ec.main.sign.SignInByTelDelegate
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.me_function_item.*
import kotlinx.android.synthetic.main.pic_detail_1.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : EmallDelegate(), OnTabSelectListener {
    var OPTICS = "1"
    var NOCTILUCENCE = "5"
    var VIDEO = "3"
    var sceneDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var videoDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var videoDetail = VideoDetailBean()
    var lati = "S"
    var longi = "W"
    var mMapView: TextureMapView? = null
    var mBaiduMap: BaiduMap? = null
    var commoditySubmitDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var commoditySubmitDemandBean = CommoditySubmitDemandBean()
    var sceneData = SceneDetailBean().data
    var videoData = VideoDetailBean().data
    var latitude = Double
    var longitude = Double
    var productId = String()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    var getCollectionMarkParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getCollectionMarkBean = GetCollectionMarkBean()
    var flag = false
    var addCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var cancelCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var commonBean = CommonBean()
    var type = String()
    var mSharedPreferences: SharedPreferences? = null
    var type135 = String()
    var pageNum = 0
    var TAG = false
    val couponList: MutableList<String> = mutableListOf()
    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        setSwipeBackEnable(false)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mSharedPreferences = activity.getSharedPreferences("COLLECTION", Context.MODE_PRIVATE)
        goods_detail_loading_rl.visibility = View.VISIBLE
        mMapView = activity.findViewById(R.id.goods_detail_map) as TextureMapView
        mBaiduMap = mMapView!!.map
        val child = mMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mMapView!!.showScaleControl(false)
        mMapView!!.showZoomControls(false)
        initViews()
        resolveConflict()
        type = arguments.getString("type")
        productId = arguments.getString("productId")
        EmallLogger.d(arguments.getString("productId"))

//        Handler().postDelayed({

            if (type == "1" || type == "5") {
                if (detail_videoview != null)
                    detail_videoview.visibility = View.GONE
                sceneDetailParams!!["productId"] = productId
                if (type == "1") {
                    sceneDetailParams!!["type"] = OPTICS
                    type135 = OPTICS
                } else {
                    sceneDetailParams!!["type"] = "2"
                    type135 = NOCTILUCENCE
                }
                getData(sceneDetailParams!!)
            } else if (type == "3") {
                if (detail_videoview != null)
                    detail_videoview.visibility = View.VISIBLE
                videoDetailParams!!["productId"] = productId
                videoDetailParams!!["type"] = VIDEO
                type135 = VIDEO
                getVideoData(videoDetailParams!!)
            }
//        }, 100)


        goods_buy_now_btn.setOnClickListener {
            if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                commoditySubmitDemand(type135)
            } else {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "GOODS_DETAIL")
                delegate.arguments = bundle
                start(delegate)
            }
        }

        goods_detail_scrollview.viewTreeObserver.addOnScrollChangedListener {
            if (goods_detail_scrollview != null) {
                goods_detail_scrollview.setOnTouchListener { p0, p1 ->
                    if (goods_detail_scrollview.getChildAt(0).height - goods_detail_scrollview.height != goods_detail_scrollview.scrollY) {
                        val scrollY = ScreenUtil.px2dip(context, goods_detail_scrollview.scrollY.toFloat())
                        when {
                            scrollY < 491 -> video_detail_tablayout_ctl.currentTab = 0
                            scrollY in 491..848 -> {
                                video_detail_tablayout_ctl.currentTab = 1
                            }
                            scrollY > 844 -> video_detail_tablayout_ctl.currentTab = 2
                        }
                    }
                    false
                }
            }
        }

        video_detail_star_iv.setOnClickListener {
            if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "GOODS_DETAIL")
                delegate.arguments = bundle
                start(delegate)
            } else {
                if (flag) {
                    /**
                     * signed in
                     */
                    cancelCollection(productId, DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
                    video_detail_star_iv.setBackgroundResource(R.drawable.collection)
                } else {
                    /**
                     * no signed in
                     */
                    addCollection(productId, DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, "1")
                    video_detail_star_iv.setBackgroundResource(R.drawable.collection_highlight)
                }
            }
        }

        video_detail_tablayout_ctl.setOnTabSelectListener(this)
        video_goods_detail_toolbar.setNavigationOnClickListener {

            if (arguments.getString("PAGE_FROM") == "COLLECTION") {
                val editor = mSharedPreferences!!.edit()
                editor.putString("collection", "true")
                editor.putString("collection_type", arguments.getString("COLLECTION_TYPE"))
                editor.commit()
            }
            val sp = activity.getSharedPreferences("TO_VIDEO", Context.MODE_PRIVATE)
            EmallLogger.d(sp.getString("to_video", ""))
            if (arguments.getString("PAGE_FROM_TEST") != "videoClassify") {
                if (sp.getString("to_video", "") == "true") {
                    val intent = activity!!.intent
                    activity!!.overridePendingTransition(0, 0)
                    activity!!.finish()
                    activity!!.overridePendingTransition(0, 0)
                    startActivity(intent)
                }
            }


            supportDelegate.pop()
        }

        video_detail_head_set_rl.setOnClickListener {
            val delegate: ContactDelegate = ContactDelegate().create()!!
            val bundle = Bundle()
            delegate.arguments = bundle
            start(delegate)
        }

        video_goods_detail_title_image.setOnClickListener {
            if (type == "3") {
                if (!NetworkUtils.isWifiAvailable() && !NetworkUtils.isWifiConnected()) {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("当前为非WiFi网络，播放将消耗流量")
                    builder.setPositiveButton("确定播放") { dialog, _ ->
                        dialog.dismiss()
                        detail_videoview.visibility = View.VISIBLE
                        video_goods_detail_title_image.visibility = View.GONE
                        video_mark.visibility = View.GONE
                        video_goods_detail_mask_iv.visibility = View.GONE
//                        detail_videoview.startButton.performClick()
                    }

                    builder.setNegativeButton("取消播放") { dialog, _ ->
                        dialog.dismiss()
                    }

                    builder.create().show()
                } else {
                    detail_videoview.visibility = View.VISIBLE
                    video_goods_detail_title_image.visibility = View.GONE
                    video_mark.visibility = View.GONE
                    video_goods_detail_mask_iv.visibility = View.GONE
                    detail_videoview.setUp(videoDetail.data.videoPath, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "")
                }

            }
        }

        video_enlarge.setOnClickListener {
            var intent = Intent(activity, JiaoZiActivity::class.java)
            intent.putExtra("title", videoDetail.data.title)
            intent.putExtra("url", videoDetail.data.videoPath)
            TAG = true
            startActivity(intent)

//            val delegate: JiaoZiDelegate = JiaoZiDelegate().create()!!
//            val bundle: Bundle? = Bundle()
//            bundle!!.putString("title", videoDetail.data.title)
//            bundle!!.putString("url", videoDetail.data.videoPath)
//            delegate.arguments = bundle
//            start(delegate)
        }

        video_detail_get_ticket_rl.setOnClickListener {
            if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                val delegate: GoodsDetailCouponDelegate = GoodsDetailCouponDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", arguments.getString("productId"))
                delegate.arguments = bundle
                start(delegate)
            } else {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "GOODS_DETAIL")
                delegate.arguments = bundle
                start(delegate)
            }
        }

        scene_goods_detail_mask_iv.setOnClickListener {
            if (type == "1") {
                val delegate: Optics1ExampleDelegate = Optics1ExampleDelegate().create()!!
                val bundle: Bundle? = Bundle()
                delegate.arguments = bundle
                start(delegate)
            } else if (type == "5") {
                val delegate: NoctilucenceExampleDelegate = NoctilucenceExampleDelegate().create()!!
                val bundle: Bundle? = Bundle()
                delegate.arguments = bundle
                start(delegate)
            }
        }

        video_goods_detail_mask_iv.setOnClickListener {
            var intent = Intent(activity, VideoExampleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCoupon() {
        retrofit = Retrofit.Builder()
                .baseUrl("http://59.110.164.214:8024")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.getCouponTypeByProductId(arguments.getString("productId"), "android")

        call.enqueue(object : retrofit2.Callback<GetCouponTypeByProductIdBean> {
            override fun onResponse(call: retrofit2.Call<GetCouponTypeByProductIdBean>, response: retrofit2.Response<GetCouponTypeByProductIdBean>) {
                val getCouponTypeByProductIdBean: GetCouponTypeByProductIdBean
                if (response.body() != null) {
                    getCouponTypeByProductIdBean = response.body()!!
                    EmallLogger.d(getCouponTypeByProductIdBean.toString())

                    if (getCouponTypeByProductIdBean.message == "success") {
                        val size = getCouponTypeByProductIdBean.data.productCoupon.size
                        EmallLogger.d(size)
                        if (size in 1..3) {
                            for (i in 0 until size) {
                                couponList.add(getCouponTypeByProductIdBean.data.productCoupon[i].toString())
                            }
                            when (size) {
                                1 -> {
                                    coupon1.text = couponList[0]
                                    coupon1.visibility = View.VISIBLE
                                }
                                2 -> {
                                    coupon1.text = couponList[0]
                                    coupon1.visibility = View.VISIBLE
                                    coupon2.text = couponList[1]
                                    coupon2.visibility = View.VISIBLE
                                }
                                3 -> {
                                    coupon1.text = couponList[0]
                                    coupon1.visibility = View.VISIBLE
                                    coupon2.text = couponList[1]
                                    coupon2.visibility = View.VISIBLE
                                    coupon3.text = couponList[2]
                                    coupon3.visibility = View.VISIBLE
                                }
                            }
                            if (goods_detail_loading_rl != null)

                                goods_detail_loading_rl.visibility = View.GONE

                        } else {

                        }

                    } else {
                        if (line != null)
                            line.visibility = View.GONE
                        if (video_detail_get_ticket_rl != null)
                            video_detail_get_ticket_rl.visibility = View.GONE
                        if (goods_detail_loading_rl != null)
                            goods_detail_loading_rl.visibility = View.GONE

                    }
                } else {
                    if (line != null)
                        line.visibility = View.GONE
                    if (video_detail_get_ticket_rl != null)
                        video_detail_get_ticket_rl.visibility = View.GONE
                    if (goods_detail_loading_rl != null)

                        goods_detail_loading_rl.visibility = View.GONE

                }
            }

            override fun onFailure(call: retrofit2.Call<GetCouponTypeByProductIdBean>, throwable: Throwable) {}
        })
    }

    private fun getData(sceneDetailParams: WeakHashMap<String, Any>) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.sceneDetail(arguments.getString("productId"), sceneDetailParams["type"]!!.toString(), "android")
        call.enqueue(object : retrofit2.Callback<SceneDetailBean> {
            override fun onResponse(call: retrofit2.Call<SceneDetailBean>, response: retrofit2.Response<SceneDetailBean>) {
                var sceneDetail = SceneDetailBean()
                EmallLogger.d(response.body().toString())
                if (response.body() != null) {

                    sceneDetail = response.body()!!
                    getCoupon()

                    setSceneData(sceneDetail)
                } else {
                }
            }

            override fun onFailure(call: retrofit2.Call<SceneDetailBean>, throwable: Throwable) {}
        })
        if (scene_mark_rl.visibility == View.INVISIBLE)
            scene_mark_rl.visibility = View.VISIBLE
        if (scene_goods_detail_mask_iv.visibility == View.INVISIBLE)
            scene_goods_detail_mask_iv.visibility = View.VISIBLE
        if (scene_rl.visibility == View.GONE)
            scene_rl.visibility = View.VISIBLE
        video_mark.visibility = View.INVISIBLE
        video_goods_detail_mask_iv.visibility = View.INVISIBLE
//        play_btn.visibility = View.INVISIBLE
        video_rl.visibility = View.GONE


    }


    private fun getVideoData(videoDetailParams: WeakHashMap<String, Any>) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.videoDetail(arguments.getString("productId"), "android")
        call.enqueue(object : retrofit2.Callback<VideoDetailBean> {
            override fun onResponse(call: retrofit2.Call<VideoDetailBean>, response: retrofit2.Response<VideoDetailBean>) {
                if (response.body() != null) {
                    EmallLogger.d(response.body()!!.data.imageDetailUrl)
                    videoDetail = response.body()!!
                    getCoupon()
                    setVideoData(videoDetail)
//                        videoSearch = response.body()!!
//                        bundle!!.putString("type","0")
//                        bundle.putSerializable("videoData", videoSearch)
//                        delegate.arguments = bundle
//                        (DELEGATE as EcBottomDelegate).start(delegate)
                } else {
                    EmallLogger.d("errpr")
                }
            }

            override fun onFailure(call: retrofit2.Call<VideoDetailBean>, throwable: Throwable) {}
        })
//        if(video_mark != null){
        if (video_mark?.visibility == View.INVISIBLE)
            video_mark?.visibility = View.VISIBLE
//        }
        if (video_goods_detail_mask_iv != null) {
            if (video_goods_detail_mask_iv.visibility == View.INVISIBLE)
                video_goods_detail_mask_iv.visibility = View.VISIBLE
        }
        if (video_rl != null) {
            if (video_rl.visibility == View.GONE)
                video_rl.visibility = View.VISIBLE
        }

        scene_mark_rl?.visibility = View.INVISIBLE
        scene_goods_detail_mask_iv?.visibility = View.INVISIBLE
        scene_rl?.visibility = View.GONE

    }

    private fun cancelCollection(pid: String?, uid: String?) {
        cancelCollectionParams!!["productId"] = pid
        cancelCollectionParams!!["userId"] = uid
        cancelCollectionParams!!["client"] = "android"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/cancelCollection")
                .params(cancelCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            flag = false
                        }
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

    private fun addCollection(pId: String, uId: String, t: String) {
        addCollectionParams!!["productId"] = pId
        addCollectionParams!!["userId"] = uId
        addCollectionParams!!["productType"] = t
        addCollectionParams!!["client"] = "android"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/addCollection")
                .params(addCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            flag = true
                        }
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

    private fun commoditySubmitDemand(type135: String) {
        commoditySubmitDemandParams!!["productId"] = arguments.getString("productId")
        commoditySubmitDemandParams!!["geo"] = ""
        commoditySubmitDemandParams!!["status"] = "0"
        commoditySubmitDemandParams!!["type"] = type135//1 3 5
        commoditySubmitDemandParams!!["client"] = "android"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/commoditySubmitDemand")
                .params(commoditySubmitDemandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commoditySubmitDemandBean = Gson().fromJson(response, CommoditySubmitDemandBean::class.java)
                        val delegate: FillOrderDelegate = FillOrderDelegate().create()!!
                        val bundle: Bundle? = Bundle()
                        bundle!!.putString("demandId", commoditySubmitDemandBean.data)

                        if (type == "1") {
                            bundle.putString("type", "1")
                            bundle.putString("imageUrl", sceneData.imageDetailUrl)
                            bundle.putString("title", "光学一级")
                            bundle.putString("time", sceneData.centerTime)


                        } else if (type == "3") {
                            bundle.putString("type", "3")
                            bundle.putString("imageUrl", videoData.imageDetailUrl)
                            bundle.putString("title", "视频1A+1B")
                            bundle.putString("time", videoData.startTime)

                        } else if (type == "5") {
                            bundle.putString("type", "5")
                            bundle.putString("imageUrl", sceneData.imageDetailUrl)
                            bundle.putString("title", "夜光增强")
                            bundle.putString("time", sceneData.centerTime)

                        }
                        bundle.putString("PAGE_FROM", "GOODS_DETAIL")
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

    @SuppressLint("SetTextI18n")
    private fun setSceneData(sceneDetail: SceneDetailBean) {
        sceneData = sceneDetail.data
        if (type == "1") {
            scene_mark.text = getString(R.string.optics_1)
        } else if (type == "5") {
            scene_mark.text = getString(R.string.noctilucence)
        }
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
//        detail_satellite_tv.text = sceneData.satelliteId + "(" + sceneData.sensor + " " + sceneData.resolution + ")"
        detail_satellite_tv.text = String.format("%s (%s %s)", sceneData.satelliteId, sceneData.sensor, sceneData.resolution)

        detail_ratio_tv.text = sceneData.resolution
        detail_area_tv.text = String.format(resources.getString(R.string.video_detail_area), sceneData.size)
        detail_cloud_tv.text = String.format(resources.getString(R.string.video_detail_cloud), sceneData.cloud)
        judgeLati_longi(sceneData.latitude, sceneData.longitude)
        detail_location_tv.text = String.format(resources.getString(R.string.video_detail_location), sceneData.longitude, longi, sceneData.latitude, lati)
        detail_coordinate_tv.text = "WGS-84"

    }

    private fun setVideoData(videoDetail: VideoDetailBean) {
        videoData = videoDetail.data
        video_detail_title_tv.text = videoData.title
        detail_gather_time_tv.text = String.format(resources.getString(R.string.video_detail_gather_time), videoData.startTime)
        detail_angle_tv.text = String.format(resources.getString(R.string.video_detail_angle), videoData.rollSatelliteAngleMajor)

        Glide.with(context)
                .load(videoData.imageDetailUrl)
                .into(video_goods_detail_title_image)
        drawMap(getGeo(videoData.geo))
        video_detail_promotion_description_tv.text = videoData.promotionDescription
        video_detail_sale_price_tv.text = String.format(resources.getString(R.string.video_detail_sale_price), videoData.salePrice)
        video_detail_original_price_tv.text = String.format(resources.getString(R.string.video_detail_original_price), videoData.originalPrice)
        changeColor(videoData.serviceDescription)
        detail_product_id_tv.text = videoData.productId
        detail_satellite_tv.text = String.format("%s (%s %sm)", videoData.satelliteId, videoData.sensor, videoData.resolution)

//        detail_satellite_tv.text = videoData.satelliteId
        detail_ratio_tv.text = videoData.resolution + "m"
        detail_area_tv.text = String.format(resources.getString(R.string.video_detail_area), videoData.size)
        detail_cloud_tv.text = String.format(resources.getString(R.string.video_detail_cloud), videoData.cloud)
        judgeLati_longi(videoData.latitude, videoData.longitude)
        detail_location_tv.text = String.format(resources.getString(R.string.video_detail_location), videoData.longitude, longi, videoData.latitude, lati)
        detail_coordinate_tv.text = "WGS-84"
        Glide.with(context)
                .load(videoData.imageDetailUrl)
                .into(detail_videoview.thumbImageView)
        detail_videoview.thumbImageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        detail_videoview.thumbImageView.setImage(Uri.parse(videoData.imageDetailUrl))
        detail_videoview.setUp(videoDetail.data.videoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        video_goods_detail_title_image.visibility = View.GONE
        video_goods_detail_title_image_mask.visibility = View.GONE
//        Handler().postDelayed({
//            if(detail_videoview != null){
//                detail_videoview.startButton.performClick()
//            }
//        }, 1000)
        goods_detail_loading_rl.visibility = View.GONE


    }

    private fun drawMap(geo: MutableList<Array<String>>) {
        val pt1 = LatLng(java.lang.Double.parseDouble(geo[0][1]), java.lang.Double.parseDouble(geo[0][0]))
        val pt2 = LatLng(java.lang.Double.parseDouble(geo[1][1]), java.lang.Double.parseDouble(geo[1][0]))
        val pt3 = LatLng(java.lang.Double.parseDouble(geo[2][1]), java.lang.Double.parseDouble(geo[2][0]))
        val pt4 = LatLng(java.lang.Double.parseDouble(geo[3][1]), java.lang.Double.parseDouble(geo[3][0]))
        val pts = ArrayList<LatLng>()
        pts.add(pt1)
        pts.add(pt2)
        pts.add(pt3)
        pts.add(pt4)
        val polygonOption = PolygonOptions()
                .points(pts)
                .stroke(Stroke(1, Color.parseColor("#F56161")))
                .fillColor(Color.parseColor("#BFF56161"))

        mBaiduMap!!.addOverlay(polygonOption)


        val latlng = LatLng((java.lang.Double.parseDouble(geo[1][1]) + java.lang.Double.parseDouble(geo[2][1])) / 2, (java.lang.Double.parseDouble(geo[3][0]) + java.lang.Double.parseDouble(geo[2][0])) / 2)
        val mMapStatus: MapStatus = MapStatus.Builder().target(latlng).zoom(12F).build()
        val mapStatusUpdate: MapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        mBaiduMap!!.setMapStatus(mapStatusUpdate)
    }

    private fun getGeo(geo: String): MutableList<Array<String>> {
        val geos: MutableList<Array<String>> = mutableListOf()
        val temp3 = geo.substring(geo.indexOf("[[") + 1, geo.indexOf("]}"))
        val temp2 = temp3.replace("[", "")
        val temp = temp2.replace("]", "")
        val array = temp.split(',')
        geos.add(arrayOf(array[0], array[1]))
        geos.add(arrayOf(array[2], array[3]))
        geos.add(arrayOf(array[4], array[5]))
        geos.add(arrayOf(array[6], array[7]))
        return geos
    }

    private fun changeColor(serviceDescription: String) {
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
        video_detail_head_set_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/headset.ttf")
        video_detail_original_price_tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        (activity as AppCompatActivity).setSupportActionBar(video_goods_detail_toolbar)
        video_goods_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
        val mTitles = arrayOf("预览图", " 参数", "位置 ")
        video_detail_original_price_tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        scene_detail_original_price_tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        val mIconUnselectIds = intArrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect)
        val mIconSelectIds = intArrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select)
        val mTabEntities: ArrayList<CustomTabEntity>? = ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            video_detail_tablayout_ctl.setTabData(mTabEntities)
        }
    }


    private fun resolveConflict() {

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

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
            getCollectionMark(productId, DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId)
        }
        video_goods_detail_title_image.isClickable = true
    }

    private fun getCollectionMark(pid: String, uid: String?) {
        getCollectionMarkParams!!["productId"] = pid
        getCollectionMarkParams!!["userId"] = uid
        getCollectionMarkParams!!["client"] = "android"

        EmallLogger.d(getCollectionMarkParams!!)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/getCollectionMark")
                .params(getCollectionMarkParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        getCollectionMarkBean = Gson().fromJson(response, GetCollectionMarkBean::class.java)
                        if (getCollectionMarkBean.message == "success") {
                            if (getCollectionMarkBean.data.collectionMark == 1) {
                                if (video_detail_star_iv != null) {
                                    video_detail_star_iv.setBackgroundResource(R.drawable.collection_highlight)
                                    flag = true
                                }
                            } else {
                                if (video_detail_star_iv != null) {
                                    video_detail_star_iv.setBackgroundResource(R.drawable.collection)
                                    flag = false
                                }
                            }
                        } else {
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()
    }

    override fun onResume() {
        super.onResume()
        if (mMapView != null)
            mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (mMapView != null)
            mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMapView != null)
            mMapView!!.onDestroy()


    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        JZVideoPlayer.releaseAllVideos()
    }

    override fun onBackPressedSupport(): Boolean {
        if (arguments.getString("PAGE_FROM") == "COLLECTION") {
            val editor = mSharedPreferences!!.edit()
            editor.putString("collection", "true")
            editor.putString("collection_type", arguments.getString("COLLECTION_TYPE"))
            editor.commit()
        }
        val sp = activity.getSharedPreferences("TO_VIDEO", Context.MODE_PRIVATE)
        EmallLogger.d(sp.getString("to_video", ""))
        if (arguments.getString("PAGE_FROM_TEST") != "videoClassify") {
            if (sp.getString("to_video", "") == "true") {
                val intent = activity!!.intent
                activity!!.overridePendingTransition(0, 0)
                activity!!.finish()
                activity!!.overridePendingTransition(0, 0)
                startActivity(intent)
            }
        }


        supportDelegate.pop()
        return true
    }
}
