package com.example.emall_ec.main.program

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_program.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.TextSwitcherView
import java.util.ArrayList
import android.animation.ObjectAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.util.DisplayMetrics
import android.widget.ZoomControls
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.view.RulerView
import com.example.emall_core.util.view.ScreenUtil.dip2px
import com.example.emall_ec.R.id.mMapView
import vi.com.gdi.bgl.android.java.EnvDrawText.pt


/**
 * Created by lixiang on 2018/3/16.
 */
class ProgramDelegate : EmallDelegate(), SensorEventListener {

    val handler = Handler()
    var task: Runnable? = null
    var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    private var lati_lt_screen: Double? = 0.0
    private var longi_lt_screen: Double? = 0.0
    private var lati_rb_screen: Double? = 0.0
    private var longi_rb_screen: Double? = 0.0
    private var area: Double? = 0.0
    var areaTv: TextView? = null
    private var lastX: Double? = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()
    var myListener = MyLocationListenner()
    private var mLocClient: LocationClient? = null
    private var isFirstLoc = true
    private var locData: MyLocationData? = null
    private var mSensorManager: SensorManager? = null
    private var lati_lt: Double? = 0.0
    private var longi_lt: Double? = 0.0
    private var lati_rb = 0.0
    private var longi_rb = 0.0
    private var level = 1
    private var topRl: RelativeLayout? = null
    private var leftRl: RelativeLayout? = null
    private var rightRl: RelativeLayout? = null
    private var bottomRl: RelativeLayout? = null
    private var satelliteImageView: ImageView? = null
    private var zoomImageView: ImageView? = null
    private var scrollTextView: TextSwitcherView? = null
    private var title: TextView?= null
    private var nextStep: TextView?= null
    private var rulerRl: RelativeLayout? = null
    private var rular: RulerView? = null
    private var rular2: RulerView? = null
    private var r1Tv: TextView?= null
    private var r2Tv: TextView?= null
    private var zoomIn: AppCompatButton? = null
    private var zoomOut: AppCompatButton? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val x = p0!!.values[SensorManager.DATA_X].toDouble()
        if (Math.abs(x - lastX!!) > 1.0) {
            mCurrentDirection = x.toInt()
            locData = MyLocationData.Builder()
                    .accuracy(0F)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build()
            mBaiduMap!!.setMyLocationData(locData)
        }
        lastX = x
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_program
    }

    fun create(): ProgramDelegate? {
        return ProgramDelegate()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initial() {
        program_back_btn_rl.setOnClickListener {
            if (level == 1) {
                pop()
            } else if (level == 2) {
                level = 1
                program_toolbar.setBackgroundColor(Color.parseColor("#BF000000"))
                topRl!!.setBackgroundColor(Color.parseColor("#99000000"))
                leftRl!!.setBackgroundColor(Color.parseColor("#99000000"))
                rightRl!!.setBackgroundColor(Color.parseColor("#99000000"))
                bottomRl!!.setBackgroundColor(Color.parseColor("#99000000"))
                program_bottom_rl.setBackgroundColor(Color.parseColor("#BF000000"))
                ll_bar.setBackgroundColor(Color.parseColor("#BF000000"))
                program_camera.visibility = View.VISIBLE
                satelliteImageView!!.visibility = View.VISIBLE
                scrollTextView!!.visibility = View.VISIBLE
                title!!.visibility = View.GONE
                program_toolbar_searchbar.visibility = View.VISIBLE
                nextStep!!.visibility = View.GONE
                rulerRl!!.visibility = View.INVISIBLE
                rular!!.visibility = View.INVISIBLE
                rular2!!.visibility = View.INVISIBLE
                r1Tv!!.visibility = View.INVISIBLE
                r2Tv!!.visibility = View.INVISIBLE
                val mUiSettings = mBaiduMap!!.uiSettings
                mUiSettings.isScrollGesturesEnabled = true
                mUiSettings.isOverlookingGesturesEnabled = true
                mUiSettings.isZoomGesturesEnabled = true
            }
        }
        mSensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        initViews()
        handlePermisson()
        initMap()
        mBaiduMap = program_mapview.map
        val listener: BaiduMap.OnMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {}
            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {}
            override fun onMapStatusChange(p0: MapStatus?) {}
            override fun onMapStatusChangeFinish(p0: MapStatus?) {
                val pt = Point()
                pt.x = ((DimenUtil().px2dip(context, DimenUtil().getScreenWidth().toFloat()) - 250) * 0.5).toInt()
                pt.y = (((DimenUtil().px2dip(context, DimenUtil().getScreenHeight().toFloat()) - 72 - 92 - 250) * 0.4 + 72).toInt())
                val ll = mBaiduMap!!.projection.fromScreenLocation(pt)
                lati_lt_screen = ll.latitude
                longi_lt_screen = ll.longitude

                val pt3 = Point()
                pt3.x = ((DimenUtil().px2dip(context, DimenUtil().getScreenWidth().toFloat()) - 250) * 0.5).toInt() + DimenUtil().dip2px(context, 250F)
                pt3.y = (((DimenUtil().px2dip(context, DimenUtil().getScreenHeight().toFloat()) - 72 - 92 - 250) * 0.4 + 72).toInt()) + DimenUtil().dip2px(context, 250F)
                val ll3 = mBaiduMap!!.projection.fromScreenLocation(pt3)
                lati_rb_screen = ll3.latitude
                longi_rb_screen = ll3.longitude

                var leftTop = LatLng(lati_lt_screen!!, longi_lt_screen!!)
                var rightBottom = LatLng(lati_rb_screen!!, longi_rb_screen!!)
                EmallLogger.d(DistanceUtil.getDistance(leftTop, rightBottom) * DistanceUtil.getDistance(leftTop, rightBottom) / 1000000)
                area = DistanceUtil.getDistance(leftTop, rightBottom) * DistanceUtil.getDistance(leftTop, rightBottom) / 1000000
                var areaString = area.toString()
                var temp = areaString.substring(0, areaString.indexOf(".") + 3)
                if (areaString.contains("E")) {
                    areaTv!!.text = String.format("当前面积：%s 亿平方公里", temp)
                } else {
                    areaTv!!.text = String.format("当前面积：%s 平方公里", temp)
                }
            }
        }
        mMapView!!.map.setOnMapStatusChangeListener(listener)
    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mMapView = activity.findViewById<MapView>(R.id.program_mapview)
        mLocClient = LocationClient(activity)
        mLocClient!!.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        option.setAddrType("all")
        option.setIsNeedLocationPoiList(true)
        mLocClient!!.locOption = option
        mLocClient!!.start()

        mBaiduMap = mMapView!!.map
        mBaiduMap!!.isMyLocationEnabled = true
        mBaiduMap!!.setMyLocationConfigeration(MyLocationConfiguration(mCurrentMode, true, null))
        val builder = MapStatus.Builder()
        builder.overlook(0f)
//        mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        val child = mMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mMapView!!.showScaleControl(false)
        mMapView!!.showZoomControls(false)
        val mUiSettings = mBaiduMap!!.uiSettings
        mUiSettings.isScrollGesturesEnabled = true
        mUiSettings.isOverlookingGesturesEnabled = true
        mUiSettings.isZoomGesturesEnabled = true
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        topRl = RelativeLayout(activity)
        topRl!!.id = 1
        val topRlHeight = (DimenUtil().px2dip(context, DimenUtil().getScreenHeight().toFloat()) - 72 - 92 - 250) * 0.4
        val topRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtil().dip2px(context, topRlHeight.toFloat()))
        topRlParams.addRule(RelativeLayout.BELOW, R.id.program_toolbar)
        topRl!!.setBackgroundColor(Color.parseColor("#99000000"))
        topRl!!.layoutParams = topRlParams
        program_root_rl.addView(topRl, topRlParams)

        leftRl = RelativeLayout(activity)
        leftRl!!.id = 2
        val RlWidth = (DimenUtil().px2dip(context, DimenUtil().getScreenWidth().toFloat()) - 250) * 0.5
        val leftRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()), DimenUtil().dip2px(context, 250F))
        leftRlParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        leftRlParams.setMargins(DimenUtil().dip2px(context, 0F), DimenUtil().dip2px(context, 0F), 0, 0)
        leftRl!!.setBackgroundColor(Color.parseColor("#99000000"))
        leftRl!!.layoutParams = leftRlParams
        program_root_rl.addView(leftRl, leftRlParams)

        rightRl = RelativeLayout(activity)
        rightRl!!.id = 3
        val rightRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()), DimenUtil().dip2px(context, 250F))
        rightRlParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        rightRlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightRlParams.setMargins(0, DimenUtil().dip2px(context, 0F), 0, DimenUtil().dip2px(context, 0F))
        rightRl!!.setBackgroundColor(Color.parseColor("#99000000"))
        rightRl!!.layoutParams = rightRlParams
        program_root_rl.addView(rightRl, rightRlParams)

        zoomImageView = ImageView(activity)
        zoomImageView!!.id = 10
        val zoomImageViewParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 28F), DimenUtil().dip2px(context, 59F))
        zoomImageViewParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        zoomImageViewParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        zoomImageView!!.setImageResource(R.drawable.zoom)
        zoomImageView!!.layoutParams = zoomImageViewParams
        rightRl!!.addView(zoomImageView, zoomImageViewParams)

        zoomIn = AppCompatButton(activity)
        val zoomInParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 28F), DimenUtil().dip2px(context, 30F))
        zoomInParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        zoomInParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        zoomInParams.addRule(RelativeLayout.ALIGN_TOP, zoomImageView!!.id)
        zoomInParams.addRule(RelativeLayout.ALIGN_LEFT, zoomImageView!!.id)
        zoomIn!!.setBackgroundColor(Color.parseColor("#00000000"))
        zoomIn!!.layoutParams = zoomInParams
        rightRl!!.addView(zoomIn, zoomInParams)
        zoomIn!!.setOnClickListener {
            EmallLogger.d("zoomin")
        }

        bottomRl = RelativeLayout(activity)
        bottomRl!!.id = 4
        val bottomRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        bottomRlParams.addRule(RelativeLayout.BELOW, leftRl!!.id)
        bottomRlParams.addRule(RelativeLayout.ABOVE, R.id.program_bottom_rl)
        bottomRl!!.setBackgroundColor(Color.parseColor("#99000000"))
        bottomRl!!.layoutParams = bottomRlParams
        program_root_rl.addView(bottomRl, bottomRlParams)

        satelliteImageView = ImageView(activity)
        val satelliteImageViewParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 23F), DimenUtil().dip2px(context, 23F))
        satelliteImageViewParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        satelliteImageViewParams.setMargins(0, DimenUtil().dip2px(context, 280F), 0, 0)
        satelliteImageViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        satelliteImageView!!.setImageResource(R.drawable.program_satellite)
        satelliteImageView!!.layoutParams = satelliteImageViewParams
        program_root_rl.addView(satelliteImageView, satelliteImageViewParams)

        scrollTextView = TextSwitcherView(activity)
        val scrollTextViewParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, DimenUtil().dip2px(context, 17F))
        scrollTextViewParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        scrollTextViewParams.setMargins(0, DimenUtil().dip2px(context, 316F), 0, 0)
        scrollTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        scrollTextView!!.layoutParams = scrollTextViewParams
        val textArray: MutableList<String> = mutableListOf(getString(R.string.fake_satellite1), getString(R.string.fake_satellite1), getString(R.string.fake_satellite1))
        scrollTextView!!.getResource(textArray as ArrayList<String>?)
        program_root_rl.addView(scrollTextView, scrollTextViewParams)

        areaTv = TextView(activity)
        val areaTvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        areaTv!!.layoutParams = areaTvParams
        areaTvParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        areaTvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        areaTvParams.setMargins(0, 0, 0, DimenUtil().dip2px(context, 16F))

        areaTv!!.setTextColor(Color.parseColor("#FFFFFF"))
        areaTv!!.textSize = 18F
        topRl!!.addView(areaTv, areaTvParams)


        val tl = ImageView(activity)
        val tlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        tlParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        tlParams.addRule(RelativeLayout.RIGHT_OF, leftRl!!.id)
        tlParams.setMargins(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F), 0, 0)
        tl.setImageResource(R.drawable.purple_border)
        tl.pivotX = (tl.width / 2).toFloat()
        tl.pivotY = (tl.height / 2).toFloat()
        tl.rotation = 180F
        tl.layoutParams = tlParams
        program_root_rl.addView(tl, tlParams)

        val tr = ImageView(activity)
        val trParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        trParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        trParams.addRule(RelativeLayout.LEFT_OF, rightRl!!.id)
        trParams.setMargins(0, DimenUtil().dip2px(context, 16F), 0, 0)
        tr.setImageResource(R.drawable.purple_border)
        tr.pivotX = (tl.width / 2).toFloat()
        tr.pivotY = (tl.height / 2).toFloat()
        tr.rotation = 270F
        tr.layoutParams = trParams
        program_root_rl.addView(tr, trParams)

        val bl = ImageView(activity)
        val blParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        blParams.addRule(RelativeLayout.ABOVE, bottomRl!!.id)
        blParams.addRule(RelativeLayout.RIGHT_OF, leftRl!!.id)
        blParams.setMargins(DimenUtil().dip2px(context, 16F), 0, 0, 0)
        bl.setImageResource(R.drawable.purple_border)
        bl.pivotX = (tl.width / 2).toFloat()
        bl.pivotY = (tl.height / 2).toFloat()
        bl.rotation = 90F
        bl.layoutParams = tlParams
        program_root_rl.addView(bl, blParams)

        val br = ImageView(activity)
        val brParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        brParams.addRule(RelativeLayout.ABOVE, bottomRl!!.id)
        brParams.addRule(RelativeLayout.LEFT_OF, rightRl!!.id)
        brParams.setMargins(DimenUtil().dip2px(context, 0F), DimenUtil().dip2px(context, 0F), 0, 0)
        br.setImageResource(R.drawable.purple_border)
        br.layoutParams = tlParams
        program_root_rl.addView(br, brParams)

        val move = ImageView(activity)
        val moveParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 230F), DimenUtil().dip2px(context, 2F))
        moveParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        moveParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        moveParams.setMargins(DimenUtil().dip2px(context, 2F), DimenUtil().dip2px(context, 0F), 0, 0)
        move.setImageResource(R.drawable.move)
        move.layoutParams = moveParams
        program_root_rl.addView(move, moveParams)

        val fakeToolbarRl = RelativeLayout(activity)
        fakeToolbarRl.id = 5
        val fakeToolbarParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtil().dip2px(context, 54F))
        fakeToolbarParams.addRule(RelativeLayout.BELOW, R.id.ll_bar)
        fakeToolbarRl.layoutParams = fakeToolbarParams
        program_root_rl.addView(fakeToolbarRl, fakeToolbarParams)

        title = TextView(activity)
        val titleParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        title!!.layoutParams = titleParams
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        titleParams.addRule(RelativeLayout.CENTER_VERTICAL)
        title!!.text = resources.getString(R.string.program_toolbar)
        title!!.setTextColor(Color.parseColor("#FFFFFF"))
        title!!.textSize = 18F
        title!!.visibility = View.GONE
        fakeToolbarRl.addView(title, titleParams)

        nextStep = TextView(activity)
        val nextStepParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        nextStepParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        nextStepParams.setMargins(0, 0, DimenUtil().dip2px(context, 18F), 0)
        nextStepParams.addRule(RelativeLayout.CENTER_VERTICAL)
        nextStep!!.layoutParams = titleParams
        nextStep!!.text = resources.getString(R.string.next_step)
        nextStep!!.setTextColor(Color.parseColor("#FFFFFF"))
        nextStep!!.textSize = 18F
        nextStep!!.visibility = View.GONE
        fakeToolbarRl.addView(nextStep, nextStepParams)



        task = object : Runnable {
            override fun run() {
                // TODO Auto-generated method stub
                handler.postDelayed(this, 3 * 1000)
                val curTranslationY = move.translationY
                val animator: ObjectAnimator = ObjectAnimator.ofFloat(move, "translationY", curTranslationY, DimenUtil().dip2px(context, 236F).toFloat(), curTranslationY)
                animator.duration = 3000
                animator.start()
            }
        }

        handler.post(task)


        rulerRl = RelativeLayout(activity)
        rulerRl!!.id = 9
        val rulerRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        rulerRlParams.addRule(RelativeLayout.BELOW, topRl!!.id)
        rulerRlParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        rulerRlParams.setMargins(0, DimenUtil().dip2px(context, 300F), 0, 0)
        rulerRl!!.layoutParams = rulerRlParams
        rulerRl!!.visibility = View.INVISIBLE
        program_root_rl.addView(rulerRl, rulerRlParams)


        rular = RulerView(activity)
        val rularParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        rularParams.setMargins(0, DimenUtil().dip2px(context, 40F), 0, 0)
        rular!!.mMaxValue = 5000
        rular!!.mMinValue = 1000
        rular!!.mScaleBase = 100
        rular!!.mMaxScaleColor = Color.parseColor("#ffffff")
        rular!!.mMidScaleColor = Color.parseColor("#666666")
        rular!!.mMinScaleColor = Color.parseColor("#666666")
        rular!!.mMaxScaleHeightRatio = 0.2F
        rular!!.mMidScaleHeightRatio = 0.2F
        rular!!.mMinScaleHeightRatio = 0.2F
        rular!!.mMaxScaleWidth = DimenUtil().dip2px(activity, 2.5F).toFloat()
        rular!!.mMidScaleWidth = DimenUtil().dip2px(activity, 2F).toFloat()
        rular!!.mMinScaleWidth = DimenUtil().dip2px(activity, 2F).toFloat()
        rular!!.mCurrentValue = 10
        rular!!.mScaleValueColor = Color.parseColor("#666666")
        rular!!.isScaleGradient = false
        rular!!.isShowScaleValue = false
        rular!!.isFocusable = true
        rular!!.layoutParams = rularParams
        rular!!.isClickable = true
        rular!!.isFocusable = true
        rular!!.visibility = View.INVISIBLE
        rulerRl!!.addView(rular!!, rularParams)


        r1Tv = TextView(activity)
        val r1TvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        r1Tv!!.layoutParams = r1TvParams
        r1TvParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        r1TvParams.setMargins(0, DimenUtil().dip2px(context, 10F), 0, 0)
        r1Tv!!.text = String.format("侧摆角 < %s°", (rular!!.currentValue / 100).toString())
        r1Tv!!.setTextColor(Color.parseColor("#FFFFFF"))
        r1Tv!!.textSize = 18F
        r1Tv!!.visibility = View.INVISIBLE
        rulerRl!!.addView(r1Tv, r1TvParams)

        rular!!.setScrollingListener(object : RulerView.OnRulerViewScrollListener<String> {
            override fun onChanged(rulerView: RulerView?, oldValue: String?, newValue: String?) {
                r1Tv!!.text = String.format("侧摆角 < %s°", (rular!!.currentValue / 100).toString())
            }

            override fun onScrollingStarted(rulerView: RulerView?) {
            }

            override fun onScrollingFinished(rulerView: RulerView?) {
            }

        })


        rular2 = RulerView(activity)
        val rular2Params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        rular2Params.setMargins(0, DimenUtil().dip2px(context, 120F), 0, 0)
        rular2!!.mMaxValue = 5000
        rular2!!.mMinValue = 1000
        rular2!!.mScaleBase = 100
        rular2!!.mMaxScaleColor = Color.parseColor("#ffffff")
        rular2!!.mMidScaleColor = Color.parseColor("#666666")
        rular2!!.mMinScaleColor = Color.parseColor("#666666")
        rular2!!.mMaxScaleHeightRatio = 0.2F
        rular2!!.mMidScaleHeightRatio = 0.2F
        rular2!!.mMinScaleHeightRatio = 0.2F
        rular2!!.mMaxScaleWidth = DimenUtil().dip2px(activity, 2.5F).toFloat()
        rular2!!.mMidScaleWidth = DimenUtil().dip2px(activity, 2F).toFloat()
        rular2!!.mMinScaleWidth = DimenUtil().dip2px(activity, 2F).toFloat()
        rular2!!.mCurrentValue = 10
        rular2!!.mScaleValueColor = Color.parseColor("#666666")
        rular2!!.isScaleGradient = false
        rular2!!.isShowScaleValue = false
        rular2!!.layoutParams = rular2Params
        rular2!!.isFocusable = true
        rular2!!.isClickable = true
        rular2!!.visibility = View.INVISIBLE
        rulerRl!!.addView(rular2, rular2Params)

        r2Tv = TextView(activity)
        val r2TvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        r2Tv!!.layoutParams = r2TvParams
        r2TvParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        r2TvParams.setMargins(0, DimenUtil().dip2px(context, 90F), 0, 0)
        r2Tv!!.text = String.format("云量 < %s%%", (rular2!!.currentValue / 100).toString())
        r2Tv!!.setTextColor(Color.parseColor("#FFFFFF"))
        r2Tv!!.textSize = 18F
        r2Tv!!.visibility = View.INVISIBLE
        rulerRl!!.addView(r2Tv, r2TvParams)

        rular2!!.setScrollingListener(object : RulerView.OnRulerViewScrollListener<String> {
            override fun onChanged(rulerView: RulerView?, oldValue: String?, newValue: String?) {
                r2Tv!!.text = String.format("云量 < %s°", (rular2!!.currentValue / 100).toString())
            }

            override fun onScrollingStarted(rulerView: RulerView?) {
            }

            override fun onScrollingFinished(rulerView: RulerView?) {
            }

        })

        program_camera.setOnClickListener {
            level = 2
            program_toolbar.setBackgroundColor(Color.parseColor("#333333"))
            topRl!!.setBackgroundColor(Color.parseColor("#333333"))
            leftRl!!.setBackgroundColor(Color.parseColor("#333333"))
            rightRl!!.setBackgroundColor(Color.parseColor("#333333"))
            bottomRl!!.setBackgroundColor(Color.parseColor("#333333"))
            program_bottom_rl.setBackgroundColor(Color.parseColor("#333333"))
            ll_bar.setBackgroundColor(Color.parseColor("#333333"))
            program_camera.visibility = View.GONE
            satelliteImageView!!.visibility = View.GONE
            scrollTextView!!.visibility = View.GONE
            title!!.visibility = View.VISIBLE
            program_toolbar_searchbar.visibility = View.INVISIBLE
            nextStep!!.visibility = View.VISIBLE
            rulerRl!!.visibility = View.VISIBLE
            rular!!.visibility = View.VISIBLE
            rular2!!.visibility = View.VISIBLE
            r1Tv!!.visibility = View.VISIBLE
            r2Tv!!.visibility = View.VISIBLE
            val mUiSettings = mBaiduMap!!.uiSettings
            mUiSettings.isScrollGesturesEnabled = false
            mUiSettings.isOverlookingGesturesEnabled = false
            mUiSettings.isZoomGesturesEnabled = false
        }

        nextStep!!.setOnClickListener {
            val delegate: ProgramParamsDelegate = ProgramParamsDelegate().create()!!
            start(delegate)
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        handler.removeCallbacks(task)
    }

    inner class MyLocationListenner : BDLocationListener {
        var lati: Double = 0.toDouble()
        var longi: Double = 0.toDouble()
        var address: String = ""
        internal lateinit var poi: List<Poi>

        override fun onReceiveLocation(location: BDLocation?) {
            if (location == null || mMapView == null) {
                return
            }

            val locData = MyLocationData.Builder()
                    .accuracy(0F)
                    .direction(mCurrentDirection.toFloat())
                    .latitude(location.latitude)
                    .longitude(location.longitude).build()
            lati = location.latitude
            longi = location.longitude
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            address = location.addrStr
            mCurrentAccracy = location.radius
            poi = location.poiList
            mBaiduMap!!.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(8.0f)
                mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

        fun onConnectHotSpotMessage(s: String, i: Int) {

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun handlePermisson() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            } else {
                myRequestPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    override fun onPause() {
//        mMapView!!.onPause()
        super.onPause()
    }

    override fun onResume() {
        mMapView!!.onResume()
        super.onResume()
        mSensorManager!!.registerListener(this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI)
    }

    override fun onDestroy() {
//        mLocClient!!.stop()
//        mBaiduMap!!.isMyLocationEnabled = false
//        mMapView!!.onDestroy()
//        mMapView = null
//        mSensorManager!!.unregisterListener(this)
        super.onDestroy()
    }
}