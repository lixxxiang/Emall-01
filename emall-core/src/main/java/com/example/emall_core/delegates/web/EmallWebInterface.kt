package com.example.emall_core.delegates.web

import android.media.MediaSyncEvent.createEvent
import com.alibaba.fastjson.JSON
import android.webkit.JavascriptInterface
import com.example.emall_core.delegates.web.event.Event
import com.example.emall_core.delegates.web.event.EventManager
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 2018/2/26.
 */
internal class EmallWebInterface private constructor(private val DELEGATE: WebDelegate) {

    @JavascriptInterface
    fun event(params: String): String? {
        val action = JSON.parseObject(params).getString("action")
        val event : Event?= EventManager.instance.createEvent(action)
//        EmallLogger.d("WEB_EVENT", params)
        event!!.action = action
        event.setDelegate(DELEGATE)
        event.context = DELEGATE.context
        event.url = DELEGATE.url
        return event.execute(params)
    }

    companion object {

        fun create(delegate: WebDelegate): EmallWebInterface {
            return EmallWebInterface(delegate)
        }
    }
}