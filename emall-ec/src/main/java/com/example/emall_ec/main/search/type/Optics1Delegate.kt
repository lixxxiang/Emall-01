package com.example.emall_ec.main.search.type

import android.view.View
import android.widget.AdapterView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.net.CommonUrls
import kotlinx.android.synthetic.main.delegate_optics1.*
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

    override fun initial() {
        ssp2!!["scopeGeo"] = "{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}"
        ssp2!!["productType"] = ""
        ssp2!!["resolution"] = ""
        ssp2!!["satelliteId"] = ""
        ssp2!!["startTime"] = "2015-04-30"
        ssp2!!["endTime"] = "2017-12-01"
        ssp2!!["cloud"] = ""
        ssp2!!["type"] = ""
        ssp2!!["pageSize"] = "10"
        ssp2!!["pageNum"] = "1"

        CommonUrls().sceneSearch(context, ssp2, optics_rv)

        optics_screen_tv.setOnClickListener {
            if (!screenIsShow){
                optics_screen_rl.visibility = View.VISIBLE
                screenIsShow = true
            }else{
                optics_screen_rl.visibility = View.INVISIBLE
                screenIsShow = false
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

}