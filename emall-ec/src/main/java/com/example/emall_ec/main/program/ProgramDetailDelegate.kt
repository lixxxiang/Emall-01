package com.example.emall_ec.main.program

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.demand.FillOrderDelegate
import com.example.emall_ec.main.program.data.DemandBean
import com.example.emall_ec.main.program.data.DetailBean
import com.example.emall_ec.main.sign.SignInByTelDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_program_detail.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.lang.Double
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap



class ProgramDetailDelegate : EmallDelegate() {
    private var demandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var demandBean = DemandBean()
    var mMapView: MapView? = null
    var mBaiduMap: BaiduMap? = null
    var geoString = String()
    private var detailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var detailBean = DetailBean()
    private var isClicked = false
    var originalPrice = String()
    var salePrice = String()
    fun create(): ProgramDetailDelegate? {
        return ProgramDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_program_detail
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        program_detail_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(program_detail_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        program_detail_head_set_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/headset.ttf")
        program_detail_toolbar.setNavigationOnClickListener {
            pop()
        }
        val bis = arguments.getByteArray("image")
        val bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.size)
        program_detail_map.setImageBitmap(bitmap)
        val sp = activity.getSharedPreferences("PROGRAMMING", Context.MODE_PRIVATE)
//        initMap(sp)
//        resolveConflict()

        EmallLogger.d(sp.getString("scopeGeo", ""))
        getData(sp)

        program_goods_buy_now_btn.setOnClickListener {
            isClicked = true
            if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "PROGRAM_DETAIL")
                delegate.arguments = bundle
                start(delegate)
            } else {
                getDemandId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, sp)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(data: DetailBean.DataBean, sp: SharedPreferences) {
        program_detail_original_price_tv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        program_detail_title_tv.text = data.promotionName
        program_detail_promotion_description_tv.text = data.promotionDescription
        program_detail_sale_price_tv.text = String.format("¥%s", data.salePrice)
        program_detail_original_price_tv.text = String.format("¥%s", data.originalPrice)
        program_detail_service_1_tv.text = data.serviceDescription
        when {
            sp.getString("productType", "") == "1" -> program_detail_type_tv.text = getString(R.string.optics_1)
            sp.getString("productType", "") == "2" -> program_detail_type_tv.text = getString(R.string.noctilucence)
            sp.getString("productType", "") == "3" -> program_detail_type_tv.text = getString(R.string.video1A_1B)
        }
        program_detail_ratio_tv.text = getString(R.string.default_ratio)
        program_detail_area_tv.text = String.format("%s 平方公里", sp.getString("area", "").substring(0, sp.getString("area", "").indexOf(".") + 3))
        program_detail_gather_time_tv.text = String.format("%s - %s", sp.getString("startTime", "").toString().replace("-", "."), sp.getString("endTime", "").toString().replace("-", "."))
        program_detail_cloud_tv.text = String.format("%s%%", sp.getString("cloud", ""))
        program_detail_angle_tv.text = String.format("%s°", sp.getString("angle", ""))
    }

    private fun initMap(sp: SharedPreferences) {
        mMapView = activity.findViewById(R.id.program_detail_map) as MapView
        mBaiduMap = mMapView!!.map
        geoString = sp.getString("geoString", "")
        val geos: MutableList<Array<String>> = mutableListOf()
        val array = geoString.split(',')
        EmallLogger.d(array)
        geos.add(arrayOf(array[0], array[1]))
        geos.add(arrayOf(array[2], array[1]))
        geos.add(arrayOf(array[2], array[3]))
        geos.add(arrayOf(array[0], array[3]))

        val child = mMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mMapView!!.showScaleControl(false)
        mMapView!!.showZoomControls(false)
        drawMap(geos)

    }

    private fun drawMap(geo: MutableList<Array<String>>) {
        val pt1 = LatLng(Double.parseDouble(geo[0][1]), Double.parseDouble(geo[0][0]))
        val pt2 = LatLng(Double.parseDouble(geo[1][1]), Double.parseDouble(geo[1][0]))
        val pt3 = LatLng(Double.parseDouble(geo[2][1]), Double.parseDouble(geo[2][0]))
        val pt4 = LatLng(Double.parseDouble(geo[3][1]), Double.parseDouble(geo[3][0]))
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


        val latlng = LatLng((Double.parseDouble(geo[1][1]) + Double.parseDouble(geo[2][1])) / 2, (Double.parseDouble(geo[3][0]) + Double.parseDouble(geo[2][0])) / 2)
        val mMapStatus: MapStatus = MapStatus.Builder().target(latlng).zoom(12F).build()
        val mapStatusUpdate: MapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        mBaiduMap!!.setMapStatus(mapStatusUpdate)
    }

    private fun getData(sp: SharedPreferences) {


        detailParams!!["area"] = sp.getString("area", "")
        detailParams!!["productType"] = sp.getString("productType", "")
        EmallLogger.d(String.format("%s %s", detailParams!!["area"], detailParams!!["protuctType"]))
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/programming/detail")
                .params(detailParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        detailBean = Gson().fromJson(response, DetailBean::class.java)
                        if (detailBean.message == "success") {
                            originalPrice = detailBean.data.originalPrice
                            salePrice = detailBean.data.salePrice
                            initViews(detailBean.data, sp)
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {
                        EmallLogger.d("f")

                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {
                        EmallLogger.d("e")

                    }
                })
                .build()
                .post()

    }

    private fun resolveConflict() {

        val v = mMapView!!.getChildAt(0)
        v.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                program_detail_scrollview.requestDisallowInterceptTouchEvent(false)
            } else {
                program_detail_scrollview.requestDisallowInterceptTouchEvent(true)
            }
            false
        })
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty() && isClicked) {
            getDemandId(DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId, activity.getSharedPreferences("PROGRAMMING", Context.MODE_PRIVATE))
            isClicked = false

        }
    }

    private fun getDemandId(userId: String, sp: SharedPreferences) {
        demandParams!!["productType"] = sp.getString("productType", "")
        demandParams!!["scopeType"] = "2"
        demandParams!!["scopeDetail"] = ""
        demandParams!!["scopeGeo"] = sp.getString("scopeGeo", "")
        demandParams!!["resolution"] = "0.72"
        demandParams!!["userId"] = userId
        demandParams!!["startTime"] = sp.getString("startTime", "")
        demandParams!!["endTime"] = sp.getString("endTime", "")
        demandParams!!["cloud"] = sp.getString("cloud", "")
        demandParams!!["angle"] = sp.getString("angle", "")
        demandParams!!["duration"] = ""
        demandParams!!["area"] = sp.getString("area", "")
        demandParams!!["pointNum"] = ""
        demandParams!!["center"] = sp.getString("center", "")
        demandParams!!["originalPrice"] = originalPrice
        demandParams!!["salePrice"] = salePrice
        demandParams!!["finalPrice"] = salePrice

        RestClient().builder()
                .url("http://59.110.164.214:8025/global/order/create/demand")
                .params(demandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        demandBean = Gson().fromJson(response, DemandBean::class.java)
                        if (demandBean.msg == "成功") {
                            val delegate: FillOrderDelegate = FillOrderDelegate().create()!!
                            val bundle: Bundle? = Bundle()
                            bundle!!.putString("demandId", demandBean.data.orderIdArray)
                            bundle.putString("imageUrl", "program")
                            bundle.putString("type", "2")
                            when {
                                sp.getString("productType", "") == "1" -> bundle.putString("title", String.format("类型：%s", getString(R.string.optics_1)))
                                sp.getString("productType", "") == "2" -> bundle.putString("title", String.format("类型：%s", getString(R.string.noctilucence)))
                                sp.getString("productType", "") == "3" -> bundle.putString("title", String.format("类型：%s", getString(R.string.video1A_1B)))
                            }
                            bundle.putString("time", String.format("%s - %s", sp.getString("startTime", "").toString().replace("-", "."), sp.getString("endTime", "").toString().replace("-", ".")))
                            delegate.arguments = bundle
                            start(delegate)
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

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}