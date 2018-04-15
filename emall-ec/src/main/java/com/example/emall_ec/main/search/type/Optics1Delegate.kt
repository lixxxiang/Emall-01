package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.AdapterView
import com.blankj.utilcode.util.TimeUtils
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.net.CommonUrls
import com.example.emall_ec.main.search.SearchResultDelegate
import kotlinx.android.synthetic.main.delegate_optics1.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class Optics1Delegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var ssp2: WeakHashMap<String, Any>? = WeakHashMap()
    var screenIsShow = false
    override fun setLayout(): Any? {
        return R.layout.delegate_optics1
    }

    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        ssp2!!["scopeGeo"] = geoFormat(sp.getString("GEO", ""))
        ssp2!!["productType"] = ""
        ssp2!!["resolution"] = ""
        ssp2!!["satelliteId"] = ""
        ssp2!!["startTime"] = "2015-04-30"
        ssp2!!["endTime"] = SimpleDateFormat("yyyy-MM-dd").format(Date())
        ssp2!!["cloud"] = ""
        ssp2!!["type"] = ""
        ssp2!!["pageSize"] = "10"
        ssp2!!["pageNum"] = "1"

        CommonUrls().sceneSearch(context, ssp2, optics_rv)

        optics_screen_tv.setOnClickListener {
            if (!screenIsShow) {
                optics_screen_rl.visibility = View.VISIBLE
                screenIsShow = true
            } else {
                optics_screen_rl.visibility = View.INVISIBLE
                screenIsShow = false
            }
        }
    }

    private fun geoFormat(geo: String): String {
        val prefix = "{\"type\":\"Polygon\",\"coordinates\":[["
        val suffix = "]]}"
        val geoArray = geo.split(" ".toRegex())
        val data = "[" + geoArray[0] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[1] +
                "]"
        return String.format("%s%s%s", prefix, data, suffix)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }
}