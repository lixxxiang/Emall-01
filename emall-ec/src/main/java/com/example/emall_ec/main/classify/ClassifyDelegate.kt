package com.example.emall_ec.main.classify
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_classify.*
import android.support.design.widget.AppBarLayout
import com.example.emall_core.util.view.AppBarStateChangeListener


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

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
    }
}