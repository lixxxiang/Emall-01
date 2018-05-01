package com.example.emall_ec.main.index

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.SwipeRefreshLayout
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_index.*
import com.example.emall_core.ui.refresh.RefreshHandler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.scanner.ScannerDelegate
import com.example.emall_ec.main.search.SearchDelegate
import pub.devrel.easypermissions.EasyPermissions


/**
 * Created by lixiang on 15/02/2018.
 */
class IndexDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var refreshHandler: RefreshHandler? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_index
    }

    fun initRefreshLayout() {
        swipe_refresh_layout_index.setColorSchemeColors(Color.parseColor("#b4a078"))
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        recycler_view_index.layoutManager = manager as RecyclerView.LayoutManager?
        val ecBottomDelegate: EcBottomDelegate = getParentDelegate()
        recycler_view_index.addOnItemTouchListener(IndexItemClickListener(ecBottomDelegate))
    }

    override fun initial() {
        getPermission()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        index_scan_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/scan.ttf")
        DELEGATE = getParentDelegate()

        refreshHandler = RefreshHandler.create(swipe_refresh_layout_index,
                recycler_view_index,
                IndexDataConverter(),
                IndexDataConverter(),
                IndexDataConverter(),
                IndexDataConverter(),
                IndexDataConverter(),
                IndexDataConverter())
        index_scan_tv.setOnClickListener {
            val delegate: ScannerDelegate = ScannerDelegate().create()!!
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
        initRefreshLayout()
        initRecyclerView()
        refreshHandler!!.firstPage(
                "http://59.110.164.214:8024/global/homePageSlide",
                "http://192.168.1.36:3030/data",
                "http://59.110.162.194:5201/global/homePageUnits",
                "http://202.111.178.10:28085/mobile/homePage")
        index_search_rl.setOnClickListener {
            val delegate: SearchDelegate = SearchDelegate().create()!!
            (DELEGATE as EcBottomDelegate).start(delegate)
        }

        swipe_refresh_layout_index.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            swipe_refresh_layout_index.isRefreshing = true
            Handler().postDelayed({
                refreshHandler = RefreshHandler.create(swipe_refresh_layout_index,
                        recycler_view_index,
                        IndexDataConverter(),
                        IndexDataConverter(),
                        IndexDataConverter(),
                        IndexDataConverter(),
                        IndexDataConverter(),
                        IndexDataConverter())
                refreshHandler!!.firstPage(
                        "http://59.110.164.214:8024/global/homePageSlide",
                        "http://192.168.1.36:3030/data",
                        "http://59.110.162.194:5201/global/homePageUnits",
                        "http://202.111.178.10:28085/mobile/homePage")
                swipe_refresh_layout_index.isRefreshing = false
            }, 1200)
        })
    }

    private fun getPermission() {
        requestPermission()
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            if (EasyPermissions.hasPermissions(activity, *mPermissionList)) {
            } else {
                EasyPermissions.requestPermissions(this, "保存图片需要读取sd卡的权限", 10, *mPermissionList)
            }
        } else {
        }
    }

    //    fun handlePermisson(){
//        val permission = Manifest.permission.CAMERA
//        val checkSelfPermission = ActivityCompat.checkSelfPermission(context,permission)
//        if (checkSelfPermission  == PackageManager.PERMISSION_GRANTED) {
//        }else{
//            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
//            }else{
//                myRequestPermission()
//            }
//        }
//    }
//
//    private fun myRequestPermission() {
//        val permissions = arrayOf(Manifest.permission.CAMERA)
//        requestPermissions(permissions,1)
//    }
//
//    /***
//     * 权限请求结果  在Activity 重新这个方法 得到获取权限的结果  可以返回多个结果
//     */
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//        }
//    }
    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}