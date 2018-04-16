package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.net.CommonUrls
import kotlinx.android.synthetic.main.delegate_noctilucence.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class NoctilucenceDelegate : EmallDelegate(){
    private var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    var screenIsShow = false
    override fun setLayout(): Any? {
        return R.layout.delegate_noctilucence
    }

    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        ssp!!["scopeGeo"] = Optics1Delegate().geoFormat(sp.getString("GEO", ""))
        EmallLogger.d(Optics1Delegate().geoFormat(sp.getString("GEO", "")))
        ssp!!["productType"] = ""
        ssp!!["resolution"] = ""
        ssp!!["satelliteId"] = ""
        ssp!!["startTime"] = "2015-04-30"
        ssp!!["endTime"] = SimpleDateFormat("yyyy-MM-dd").format(Date())
        ssp!!["cloud"] = ""
        ssp!!["type"] = "2"
        ssp!!["pageSize"] = "10"
        ssp!!["pageNum"] = "1"

//        CommonUrls().sceneSearch(context, ssp, noctilucence_rv, "NOCTILUCENCE")

        noctilucence_screen_tv.setOnClickListener {
            if (!screenIsShow) {
                noctilucence_screen_rl.visibility = View.VISIBLE
                screenIsShow = true
            } else {
                noctilucence_screen_rl.visibility = View.INVISIBLE
                screenIsShow = false
            }
        }
    }
}