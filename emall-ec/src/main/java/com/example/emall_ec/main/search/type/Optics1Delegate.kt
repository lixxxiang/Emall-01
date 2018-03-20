package com.example.emall_ec.main.search.type

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.ClassifyAdapter
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.net.CommonUrls
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_classify.*
import kotlinx.android.synthetic.main.delegate_optics1.*
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class Optics1Delegate : BottomItemDelegate(), AdapterView.OnItemClickListener {
    private var sceneSearchParams: WeakHashMap<String, Any>? = WeakHashMap()

    override fun setLayout(): Any? {
        return R.layout.delegate_optics1
    }

    override fun initial() {
        sceneSearchParams!!["scopeGeo"] = "{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}"
        sceneSearchParams!!["productType"] = ""
        sceneSearchParams!!["resolution"] = ""
        sceneSearchParams!!["satelliteId"] = ""
        sceneSearchParams!!["startTime"] = "2015-04-30"
        sceneSearchParams!!["endTime"] = "2017-12-01"
        sceneSearchParams!!["cloud"] = ""
        sceneSearchParams!!["type"] = ""
        sceneSearchParams!!["pageSize"] = "10"
        sceneSearchParams!!["pageNum"] = "1"

        CommonUrls().sceneSearch(context, sceneSearchParams, optics_rv)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

}