package com.example.emall_ec.main.index

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_index.*
import com.example.emall_core.ui.refresh.RefreshHandler
import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.util.file.FileUtil
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.sign.SignUpDelegate






/**
 * Created by lixiang on 15/02/2018.
 */
class IndexDelegate : BottomItemDelegate() {

    var refreshHandler: RefreshHandler? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_index
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    fun initRefreshLayout() {
        swipe_refresh_layout_index.setColorSchemeColors(Color.parseColor("#b4a078"))
//        swipe_refresh_layout_index.setProgressViewOffset(true, 120, 300)
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        recycler_view_index.layoutManager = manager
        val ecBottomDelegate : EcBottomDelegate = getParentDelegate()
        recycler_view_index.addOnItemTouchListener(IndexItemClickListener(ecBottomDelegate))
    }

    override fun initial() {
        index_scan_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/scan.ttf")
        index_noti_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/scan.ttf")
//        initState()
        refreshHandler = RefreshHandler.create(swipe_refresh_layout_index, recycler_view_index, IndexDataConverter(), IndexDataConverter(), IndexDataConverter(), IndexDataConverter(),IndexDataConverter(), IndexDataConverter())
        index_scan_tv.setOnClickListener {
            val delegate: SignUpDelegate = SignUpDelegate().create()!!
            val bundle : Bundle ?= Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            start(delegate)
        }
        initRefreshLayout()
        initRecyclerView()
//        if (FileUtil.checkEmulator()) {
//            refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide", "http://10.0.2.2:3030/data", "http://10.10.90.11:8086/global/homePageUnits")
//        } else {
            refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide", "http://192.168.1.36:3030/data","http://59.110.162.194:5201/global/homePageUnits")
//            refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide", "http://192.168.1.36:3030/data","http://192.168.1.36:3038/data")

//        }

    }


}