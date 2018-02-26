package com.example.emall_core.delegates.web

import android.media.MediaSyncEvent.createEvent
import com.alibaba.fastjson.JSON
import android.webkit.JavascriptInterface



/**
 * Created by lixiang on 2018/2/26.
 */
internal class LatteWebInterface private constructor(private val DELEGATE: WebDelegate) {

    @JavascriptInterface
    fun event(params: String): String? {
        val action = JSON.parseObject(params).getString("action")
//        val event = EventManager.getInstance().createEvent(action)
//        LatteLogger.d("WEB_EVENT", params)
//        if (event != null) {
//            event!!.setAction(action)
//            event!!.setDelegate(DELEGATE)
//            event!!.setContext(DELEGATE.context)
//            event!!.setUrl(DELEGATE.getUrl())
//            return event!!.execute(params)
//        }
        return null
    }

    companion object {

        fun create(delegate: WebDelegate): LatteWebInterface {
            return LatteWebInterface(delegate)
        }
    }
}