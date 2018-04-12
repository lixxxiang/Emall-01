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
import android.support.v7.widget.GridLayoutManager
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.AppBarStateChangeListener
import java.util.*
import android.view.View
import android.view.ViewTreeObserver
import com.example.emall_core.util.view.GridSpacingItemDecoration
import android.os.Handler
import com.example.emall_core.util.dimen.DimenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_ec.main.classify.data.*
import com.example.emall_ec.main.detail.GoodsDetailDelegate


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : EmallDelegate() {

    var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    var videoSearch = VideoSearch()
    private var data: MutableList<Model>? = mutableListOf()
    private var sceneAdapter: SceneClassifyAdapter? = null
    private var videoAdapter: VideoClassifyAdapter? = null
    private var viewHeight = 0
    var productId: MutableList<String>? = mutableListOf()
    var handler = Handler()
    var type = "scene"

    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    fun create(): ClassifyDelegate? {
        return ClassifyDelegate()
    }

    override fun initial() {
        if (arguments.getString("type") == "1") {
            getSceneData()
            type = "scene"
            classify_title_tv.text = resources.getString(R.string.optics_1)
        } else if (arguments.getString("type") == "0") {
            getVideoData()
            type = "video"
            classify_title_tv.text = resources.getString(R.string.video1A_1B)

        }
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
            _mActivity.onBackPressed()
        }

        if (sceneAdapter != null){
            sceneAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", productId!![position])
                bundle.putString("type", "1")
                delegate!!.arguments = bundle
                start(delegate)
            }
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
                    if (type == "scene"){
                        classify_toolbar.title = "光学1级"
                    }else if (type == "video"){
                        classify_toolbar.title = "视频1A+1B"
                    }
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


    private fun initRecyclerView(type: String) {
        if (type == "0") {
            val glm = GridLayoutManager(context, 1)
            glm.isSmoothScrollbarEnabled = true
            glm.isAutoMeasureEnabled = true
            classify_rv.addItemDecoration(GridSpacingItemDecoration(1, DimenUtil().dip2px(context, 14F), true))
            classify_rv.layoutManager = glm
            classify_rv.setHasFixedSize(true)
            classify_rv.isNestedScrollingEnabled = false
            videoAdapter = VideoClassifyAdapter(R.layout.item_classify_video, data, glm)
            classify_rv.adapter = videoAdapter
        } else if (type == "1") {
            val glm = GridLayoutManager(context, 2)
            glm.isSmoothScrollbarEnabled = true
            glm.isAutoMeasureEnabled = true
            classify_rv.addItemDecoration(GridSpacingItemDecoration(2, DimenUtil().dip2px(context, 14F), true))
            classify_rv.layoutManager = glm
            classify_rv.setHasFixedSize(true)
            classify_rv.isNestedScrollingEnabled = false
            sceneAdapter = SceneClassifyAdapter(R.layout.item_classify_scene, data, glm)
            classify_rv.adapter = sceneAdapter
        }

//        classify_rv.addOnItemTouchListener(ClassifyItemClickListener(this))
    }


    private fun getSceneData() {
        sceneSearch = arguments.getSerializable("sceneData") as SceneSearch
        EmallLogger.d(sceneSearch.data.searchReturnDtoList[0].thumbnailUrl)

        val size = sceneSearch.data.searchReturnDtoList.size
//        ssp!!.clear()
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
            model.price = sceneSearch.data.searchReturnDtoList[i].price
            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
            productId!!.add(sceneSearch.data.searchReturnDtoList[i].productId)
            data!!.add(model)
        }
        initRecyclerView("1")
    }


    private fun getVideoData() {
        videoSearch = arguments.getSerializable("videoData") as VideoSearch
        EmallLogger.d(videoSearch.data[0].detailPath)

        val size = videoSearch.data.size
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = videoSearch.data[i].detailPath
            model.price = videoSearch.data[i].price
            model.time = videoSearch.data[i].startTime
            productId!!.add(videoSearch.data[i].productId)
            data!!.add(model)
        }
        initRecyclerView("0")
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}