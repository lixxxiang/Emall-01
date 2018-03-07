package com.example.emall_ec.main.classify
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_classify.*
import android.support.design.widget.AppBarLayout
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.AppBarStateChangeListener
import java.util.*


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

    var sceneSearchParams: WeakHashMap<String, Any>? = WeakHashMap()
    var DELEGATE : EmallDelegate ?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
        classify_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(classify_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        classify_ctl.isTitleEnabled = false

//        test_classify_btn.setOnClickListener {
//
//            val delegate: GoodsDetailDelegate = GoodsDetailDelegate().create()!!
//            val bundle : Bundle ?= Bundle()
//            bundle!!.putString("KEY", "ID")
//            delegate.arguments = bundle
//            (DELEGATE as EcBottomDelegate).start(delegate)
//        }
//        classify_search.typeface = Typeface.createFromAsset(activity.assets, "iconfont/search.ttf")
        classify_appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    classify_toolbar.title = ""
                    //展开状态

                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {

                    //折叠状态
                    classify_toolbar.title = "光学1级"

                } else {

                    //中间状态
                    classify_toolbar.title = ""
                }
            }
        })

//        getData()
    }

    private fun getData() {
        sceneSearchParams!!["scopeGeo"] = "{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}"
        sceneSearchParams!!["productType"] = ""
        sceneSearchParams!!["resolution"] = ""
        sceneSearchParams!!["satelliteId"] = ""
        sceneSearchParams!!["startTime"] = "2015-04-30"
        sceneSearchParams!!["endTime"] = "2017-12-01"
        sceneSearchParams!!["cloud"] = ""
        sceneSearchParams!!["type"] = "0"
        sceneSearchParams!!["pageSize"] = "10"
        sceneSearchParams!!["pageNum"] = "1"

        RestClient().builder()
                .url("http://10.10.90.11:8086/global/mobile/sceneSearch")//EMULATOR
                .params(sceneSearchParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {

                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {

                    }
                })
                .build()
                .post()
    }
}