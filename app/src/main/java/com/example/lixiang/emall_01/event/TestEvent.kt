package com.example.lixiang.emall_01.event

import android.os.Build
import android.support.annotation.RequiresApi
import android.webkit.WebView
import android.widget.Toast
import com.example.emall_core.delegates.web.event.Event
import com.example.emall_core.util.log.EmallLogger

/**
 * Created by lixiang on 2018/2/27.
 */
class TestEvent : Event(){
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun execute(params: String): String? {
        Toast.makeText(context, action, Toast.LENGTH_LONG).show()
        if (action.equals("test")){
            EmallLogger.d("ddddd")
            val webView : WebView? = webView
            webView!!.post {
                webView.evaluateJavascript("nativeCall();", null)
            }
        }
        return null
    }
}