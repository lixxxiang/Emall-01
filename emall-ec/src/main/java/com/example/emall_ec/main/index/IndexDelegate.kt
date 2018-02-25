package com.example.emall_ec.main.index

import android.graphics.Color
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_index.*
import com.example.emall_core.ui.refresh.RefreshHandler
import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 15/02/2018.
 */
class IndexDelegate : BottomItemDelegate() {

    var refreshHandler: RefreshHandler? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_index
    }

    fun initRefreshLayout() {
        swipe_refresh_layout_index.setColorSchemeColors(Color.parseColor("#b4a078"))
        swipe_refresh_layout_index.setProgressViewOffset(true, 120, 300)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initRefreshLayout()
        initRecyclerView()
        if (FileUtil.checkEmulator()) {
            refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide", "http://10.0.2.2:3030/data")
        } else {
            refreshHandler!!.firstPage("http://59.110.164.214:8024/global/homePageSlide", "http://192.168.2.162:3030/data")}
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 2)
        recycler_view_index.layoutManager = manager
    }

    override fun initial() {
        refreshHandler = RefreshHandler.create(swipe_refresh_layout_index, recycler_view_index, IndexDataConverter(), IndexDataConverter(),IndexDataConverter())
    }
}