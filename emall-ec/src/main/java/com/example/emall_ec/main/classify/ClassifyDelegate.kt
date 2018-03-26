package com.example.emall_ec.main.classify

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_classify.*
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.AppBarStateChangeListener
import com.example.emall_ec.main.classify.data.ClassifyAdapter
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneSearch
import com.google.gson.Gson
import java.util.*
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.ViewTreeObserver
import com.example.emall_core.util.view.GridSpacingItemDecoration
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.os.Handler
import com.example.emall_core.util.dimen.DimenUtil
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_ec.R.string.classify
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import okhttp3.*
import retrofit2.Retrofit
import java.io.IOException


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

    var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    private var data: MutableList<Model>? = mutableListOf()
    private var mAdapter: ClassifyAdapter? = null
    private var viewHeight = 0
    var productId: MutableList<String>? = mutableListOf()
    var handler = Handler()
    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    fun create(): ClassifyDelegate? {
        return ClassifyDelegate()
    }


    override fun initial() {
        getData()
        classify_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(classify_toolbar)
        classify_ctl.isTitleEnabled = false
        classify_down_btn.typeface = Typeface.createFromAsset(activity.assets, "iconfont/down.ttf")
        val observer = classify_introduction_rl.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (observer.isAlive) {
                    observer.removeGlobalOnLayoutListener(this)
                    viewHeight = classify_introduction_rl.measuredHeight
                }
            }
        })
        classify_down_btn.setOnClickListener {
            classify_appbar.setExpanded(false)
            classify_sv.scrollTo(0, viewHeight + DimenUtil().dip2px(context, 6F))
        }

        classify_toolbar.setNavigationOnClickListener {
            //            _mActivity.onBackPressed()
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
//        bundle!!.putString("productId", productId!![position])
            bundle!!.putString("type", "1")
            delegate!!.arguments = bundle
            start(delegate)
        }

        mAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", productId!![position])
            bundle.putString("type", "1")
            delegate!!.arguments = bundle
            start(delegate)
        }



        classify_appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                    classify_toolbar.title = ""
                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small)
                    classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search)


                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    classify_toolbar.title = "光学1级"
                    classify_toolbar.setTitleTextColor(Color.parseColor("#5C5C5C"))
                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
                    classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search_small_dark)

                } else {
                    classify_toolbar.title = ""
                }
            }
        })


    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }


    private fun initRecyclerView() {
        val glm = GridLayoutManager(context, 2)
        glm.isSmoothScrollbarEnabled = true
        glm.isAutoMeasureEnabled = true
        classify_rv.addItemDecoration(GridSpacingItemDecoration(2, DimenUtil().dip2px(context, 14F), true))
        classify_rv.layoutManager = glm
        classify_rv.setHasFixedSize(true)
        classify_rv.isNestedScrollingEnabled = false
        mAdapter = ClassifyAdapter(R.layout.item_classify, data, glm)
        classify_rv.adapter = mAdapter
//        classify_rv.addOnItemTouchListener(ClassifyItemClickListener(this))
    }


    private fun getData() {
        sceneSearch = arguments.getSerializable("data") as SceneSearch
        EmallLogger.d(sceneSearch.data.searchReturnDtoList[0].thumbnailUrl)

        val size = sceneSearch.data.searchReturnDtoList.size
        ssp!!.clear()
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
            model.price = sceneSearch.data.searchReturnDtoList[i].price
            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
            productId!!.add(sceneSearch.data.searchReturnDtoList[i].productId)
            data!!.add(model)
        }
        initRecyclerView()
    }
}