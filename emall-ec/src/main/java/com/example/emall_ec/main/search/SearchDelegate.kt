package com.example.emall_ec.main.search

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_search.*
import com.baidu.platform.comapi.map.y
import com.baidu.platform.comapi.map.x
import android.util.DisplayMetrics
import com.baidu.mapapi.BMapManager
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.GoodsDetailDelegate


/**
 * Created by lixiang on 2018/3/20.
 */
class SearchDelegate : BottomItemDelegate(), SensorEventListener {
    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[SensorManager.DATA_X].toDouble()
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

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


    var mMapView: MapView? = null
    private var lastX: Double? = 0.0
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()
    var myListener = MyLocationListenner()
    private var mLocClient: LocationClient? = null
    private var mBaiduMap: BaiduMap? = null
    private var isFirstLoc = true
    private var locData: MyLocationData? = null
    private var mSensorManager: SensorManager? = null
    private var lati_lt: Double? = 0.0
    private var longi_lt: Double? = 0.0
    private var lati_rb = 0.0
    private var longi_rb = 0.0
    var mSharedPreferences: SharedPreferences? = null

    fun create(): SearchDelegate? {
        return SearchDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_search
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun initial() {
        mSensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSharedPreferences = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        handlePermisson()
        initMap()
        val listener: BaiduMap.OnMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {

            }

            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {
            }

            override fun onMapStatusChange(p0: MapStatus?) {

            }

            override fun onMapStatusChangeFinish(p0: MapStatus?) {
                val pt = Point()
                pt.x = 0
                pt.y = 0
                val ll = mBaiduMap!!.projection.fromScreenLocation(pt)

                lati_lt = ll.latitude
                longi_lt = ll.longitude

                //右下角经纬度
                val dm = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(dm)
                val pty = Point()
                pty.x = dm.widthPixels
                pty.y = dm.heightPixels
                val lly = mBaiduMap!!.projection.fromScreenLocation(pty)
                lati_rb = lly.latitude
                longi_rb = lly.longitude
            }

        }

        mMapView!!.map.setOnMapStatusChangeListener(listener)
        search_searchbar_rl.setOnClickListener {
            start(SearchPoiDelegate().create())
        }

        search_btn.setOnClickListener {
            val geo = String.format("%s %s %s %s",longi_lt!!.toString(), lati_lt!!.toString(), longi_rb.toString(), lati_rb.toString())
            val editor = mSharedPreferences!!.edit()
            editor.putString("GEO", geo)
            editor.commit()
            val delegate = SearchResultDelegate().create()
            val bundle = Bundle()
            bundle.putString("GEO", geo)
            delegate!!.arguments = bundle
            start(delegate)


        }

        search_back_iv.setOnClickListener {
            pop()
        }
    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mMapView = activity.findViewById<MapView>(R.id.mMapView)




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
                builder.target(ll).zoom(1.0f)
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