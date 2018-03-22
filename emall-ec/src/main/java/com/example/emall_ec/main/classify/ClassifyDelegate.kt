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


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

    var sceneSearchParams: WeakHashMap<String, Any>? = WeakHashMap()
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    private var data: MutableList<Model>? = mutableListOf()
    private var mAdapter: ClassifyAdapter? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    fun create(): ClassifyDelegate? {
        return ClassifyDelegate()
    }

    override fun initial() {
//        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        (activity.window.decorView.findViewById(R.id.content) as CoordinatorLayout).setPadding(0, 0, 0, 25)
//        DELEGATE = getParentDelegate()
        classify_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(classify_toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        classify_ctl.isTitleEnabled = false
        classify_down_btn.typeface = Typeface.createFromAsset(activity.assets, "iconfont/down.ttf")
        classify_down_btn.setOnClickListener {
            classify_appbar.setExpanded(false)
            classify_sv.scrollTo(0, 440)
        }
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
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                    classify_toolbar.title = ""
                    //展开状态
                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small)
                    classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search)


                } else if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    //折叠状态
                    classify_toolbar.title = "光学1级"
                    classify_toolbar.setTitleTextColor(Color.parseColor("#5C5C5C"))
                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
                    classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search_small_dark)

                } else {
//                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)

                    //中间状态
                    classify_toolbar.title = ""
                }
            }
        })


    }

    private fun getIntroductionHeight() {
//        val vto2 = classify_introduction_ll.viewTreeObserver
//        vto2.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                classify_introduction_tv.viewTreeObserver.removeGlobalOnLayoutListener(this)
//                EmallLogger.d(classify_introduction_tv.height)
//            }
//        })
        classify_introduction_ll.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                // TODO Auto-generated method stub
                classify_introduction_ll.viewTreeObserver.removeGlobalOnLayoutListener(this)

                EmallLogger.d("测试：", classify_introduction_ll.measuredHeight.toString())
            }
        })
    }

    fun getViewHeight(view: View?, isHeight: Boolean): Int {
        val result: Int
        if (view == null) return 0
        if (isHeight) {
            val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(h, 0)
            result = view.measuredHeight
        } else {
            val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(0, w)
            result = view.measuredWidth
        }
        return result
    }

    fun getTextViewHeight(): Int? {
        var height: Int? = 0
        val observer = classify_introduction_tv.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //避免重复监听
                classify_introduction_tv.viewTreeObserver.removeGlobalOnLayoutListener(this)

                height = classify_introduction_tv.measuredHeight//获文本高度
                //获取高度后要进行的操作就在这里执行，
                //在外面可能onGlobalLayout还没有执行而获取不到height

                //设置点击监听（其中用到了height值）
//                 des_layout.setOnClickListener(MyClickListner())
            }
        })
        return height
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        getData()
//                getFakeData()

    }

    private fun getFakeData() {
        RestClient().builder()
                .url("http://192.168.1.36:3036/data")//EMULATOR
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
//                        EmallLogger.d(response)
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        val size = sceneSearch.data.searchReturnDtoList.size
                        for (i in 0 until size) {
                            val model = Model()
                            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                            model.price = sceneSearch.data.searchReturnDtoList[i].price
                            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                            data!!.add(model)
                        }
//                        data!!.add(sceneSearch)
                        initRecyclerView()

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
                .get()
    }

    private fun initRecyclerView() {
        val glm = GridLayoutManager(context, 2)
        glm.isSmoothScrollbarEnabled = true
        glm.isAutoMeasureEnabled = true
        classify_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        classify_rv.layoutManager = glm
        classify_rv.setHasFixedSize(true)
        classify_rv.isNestedScrollingEnabled = false
        mAdapter = ClassifyAdapter(R.layout.item_classify, data, glm)
        classify_rv.adapter = mAdapter

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
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        val size = sceneSearch.data.searchReturnDtoList.size
                        for (i in 0 until size) {
                            val model = Model()
                            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                            model.price = sceneSearch.data.searchReturnDtoList[i].price
                            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                            data!!.add(model)
                        }
//                        data!!.add(sceneSearch)
                        initRecyclerView()
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