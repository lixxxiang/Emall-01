package com.example.emall_ec.main.index

import android.graphics.Color
import android.graphics.Typeface
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
//        swipe_refresh_layout_index.setProgressViewOffset(true, 120, 300)
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        recycler_view_index.layoutManager = manager as RecyclerView.LayoutManager?
        val ecBottomDelegate: EcBottomDelegate = getParentDelegate()
        recycler_view_index.addOnItemTouchListener(IndexItemClickListener(ecBottomDelegate))
    }

    override fun initial() {
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
        refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide",
                "http://192.168.1.36:3030/data",
                "http://59.110.162.194:5201/global/homePageUnits","http://202.111.178.10:28085/mobile/homePage")
        index_search_rl.setOnClickListener {
            val delegate: SearchDelegate = SearchDelegate().create()!!
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }
}