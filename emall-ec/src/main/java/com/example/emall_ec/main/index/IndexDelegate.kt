package com.example.emall_ec.main.index

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.ui.refresh.RefreshHandler
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_index.*

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
        refreshHandler!!.firstPage("http://10.0.2.2:3003/MixedContentList")
    }

    override fun initial() {
        refreshHandler = RefreshHandler(swipe_refresh_layout_index)
    }
}