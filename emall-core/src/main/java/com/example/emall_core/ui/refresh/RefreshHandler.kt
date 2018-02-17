package com.example.emall_core.ui.refresh

import android.support.v4.widget.SwipeRefreshLayout
import com.example.emall_core.app.Emall
import com.alibaba.fastjson.JSON
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.util.log.EmallLogger
import okhttp3.RequestBody


/**
 * Created by lixiang on 17/02/2018.
 */
class RefreshHandler (private val REFRESH_LAYOUT: SwipeRefreshLayout) : SwipeRefreshLayout.OnRefreshListener {

    var BODY: RequestBody? = null

    init {
        REFRESH_LAYOUT.setOnRefreshListener(this)
    }

    private fun refresh() {
        REFRESH_LAYOUT.isRefreshing = true
        Emall().getHandler()!!.postDelayed({
            //进行一些网络请求
            REFRESH_LAYOUT.isRefreshing = false
        }, 1000)
    }

    fun firstPage(url: String) {
        RestClient().builder()
                .url(url)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                    }
                })
                .build()
                .get()
    }

    override fun onRefresh() {
        refresh()
    }
}