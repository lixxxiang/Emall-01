package com.example.emall_ec.main.classify

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
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
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatTextView
import android.util.TypedValue
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.emall_core.util.dimen.DimenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_ec.main.classify.adapter.GridViewAdapter
import com.example.emall_ec.main.classify.data.*
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.search.SearchDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import retrofit2.Retrofit


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : EmallDelegate() {
    var OPTICS = "1"
    var NOCTILUCENCE = "5"
    var VIDEO = "3"
    var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    var getRecommendCitiesBean = GetRecommendCitiesBean()
    var videoSearch = VideoSearch()
    var videoHomeBean = VideoHomeBean()
    private var data: MutableList<Model>? = mutableListOf()
    private var sceneAdapter: SceneClassifyAdapter? = null
    private var videoAdapter: VideoClassifyAdapter? = null
    private var viewHeight = 0
    var productId: MutableList<String>? = mutableListOf()
    var cityName: MutableList<String>? = mutableListOf()
    var handler = Handler()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    private var adapter: GridViewAdapter? = null
    var sceneGlm: GridLayoutManager? = null
    var videoGlm: GridLayoutManager? = null
    var isExpanded = false
    private var pages = 1
    private var pagesAmount = -1
    private var SCROLL_TIMES = 0
    var cityNameToShow = String()
    var geo = String()

    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    fun create(): ClassifyDelegate? {
        return ClassifyDelegate()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        if (arguments.getString("TYPE") == "SCENE") {
            initSceneGlm()
            initRecommendCities()


        } else if (arguments.getString("TYPE") == "NOCTILUCENCE") {
            initSceneGlm()
            getData("",
                    "", "",
                    "", "",
                    "", "",
                    "2", "10", pages.toString())//2是夜光
        } else if (arguments.getString("TYPE") == "VIDEO") {
            initVideoGlm()
            getVData("0")
        }
    }

    override fun initial() {
        when {
            arguments.getString("TYPE") == "SCENE" -> {
                classify_title_tv.text = resources.getString(R.string.optics_1)
                if (classify_horizontal_scrollview_ll.visibility == View.GONE) {
                    classify_horizontal_scrollview_ll.visibility = View.VISIBLE
                }
            }

            arguments.getString("TYPE") == "NOCTILUCENCE" -> {
                classify_title_tv.text = resources.getString(R.string.noctilucence)
                classify_horizontal_scrollview_ll.visibility = View.GONE
            }
            arguments.getString("TYPE") == "VIDEO" -> {
                classify_title_tv.text = resources.getString(R.string.video1A_1B)
                classify_horizontal_scrollview_ll.visibility = View.GONE
            }
        }

        classify_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(classify_toolbar)
        classify_ctl.isTitleEnabled = false

        val observer = classify_introduction_rl.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (observer.isAlive) {
                    observer.removeGlobalOnLayoutListener(this)
                    viewHeight = classify_introduction_rl.measuredHeight
                }
            }
        })

        classify_down_btn_rl.setOnClickListener {
            if (!isExpanded) {
                classify_appbar.setExpanded(false)
                classify_sv.scrollTo(0, viewHeight)
                classify_down_btn.setBackgroundResource(R.drawable.up)

                adapter = GridViewAdapter(activity, cityName)
                classify_gv.adapter = adapter
                classify_mask_rl.visibility = View.VISIBLE
                classify_recommand_tv.text = getString(R.string.all)
                classify_hsv.visibility = View.INVISIBLE
                isScroll(false)
                isExpanded = true
                classify_recommand_tv.isClickable = false
            } else {
                closeScreen()
                classify_recommand_tv.isClickable = true
            }

        }

        /**
         * xialabufen
         */
        classify_gv.setOnItemClickListener { parent, view, position, id ->
            classify_progressBar.visibility = View.VISIBLE
            if (position != 0) {
                if (!data!!.isEmpty())
                    data!!.clear()
                closeScreen()
                classify_recommand_tv.text = getRecommendCitiesBean.data[position - 1].cityName
                cityNameToShow = getRecommendCitiesBean.data[position - 1].cityName
                SCROLL_TIMES = 0
                pages = 1
                productId!!.clear()
                EmallLogger.d(getRecommendCitiesBean.data[position - 1].cityName)
                geo = getRecommendCitiesBean.data[position - 1].geo
                getData(getRecommendCitiesBean.data[position - 1].geo,
                        "", "",
                        "", "",
                        "", "",
                        "0", "10", "1")
            } else {
                if (!data!!.isEmpty())
                    data!!.clear()
                closeScreen()
                SCROLL_TIMES = 0
                pages = 1
                productId!!.clear()

                classify_recommand_tv.text = getString(R.string.recommand)
                geo = ""
                getData("",
                        "", "",
                        "", "",
                        "", "",
                        "0", "10", "1")
            }
        }

        classify_toolbar.setNavigationOnClickListener {
            val sp = activity.getSharedPreferences("TO_VIDEO", Context.MODE_PRIVATE)
            EmallLogger.d(sp.getString("to_video", ""))
            if (sp.getString("to_video", "") == "true") {
                val intent = activity!!.intent
                activity!!.overridePendingTransition(0, 0)
                activity!!.finish()
                activity!!.overridePendingTransition(0, 0)
                startActivity(intent)
            }
            _mActivity.onBackPressed()
        }

        classify_appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        classify_toolbar.title = ""
                        classify_toolbar.setNavigationIcon(R.drawable.ic_back_small)
                        classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search)
                    }
                    State.COLLAPSED -> {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        if (arguments.getString("TYPE") == "SCENE") {
                            classify_toolbar.title = getString(R.string.optics_1)
                        } else if (arguments.getString("TYPE") == "VIDEO") {
                            classify_toolbar.title = getString(R.string.video1A_1B)
                        } else if (arguments.getString("TYPE") == "NOCTILUCENCE") {
                            classify_toolbar.title = getString(R.string.noctilucence)
                        }
                        classify_toolbar.setTitleTextColor(Color.parseColor("#5C5C5C"))
                        classify_toolbar.setNavigationIcon(R.drawable.ic_back_small_dark)
                        classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search_small_dark)

                    }
                    else -> classify_toolbar.title = ""
                }
            }
        })

        classify_recommand_rl.setOnClickListener {
            if (!isExpanded) {
                classify_recommand_tv.text = getString(R.string.recommand)
                if (!data!!.isEmpty())
                    data!!.clear()
                classify_progressBar.visibility = View.VISIBLE
                SCROLL_TIMES = 0
                pages = 1
                productId!!.clear()
                geo = ""
                getData("",
                        "", "",
                        "", "",
                        "", "",
                        "0", "10", "1")
            }
        }

        classify_sv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == (v!!.getChildAt(0).measuredHeight - v.measuredHeight)) {
                SCROLL_TIMES++
                EmallLogger.d(SCROLL_TIMES)
                EmallLogger.d(pagesAmount)
                if (SCROLL_TIMES < pagesAmount) {
                    if (arguments.getString("TYPE") == "SCENE") {
                        bottom_rl.visibility = View.VISIBLE
                        getData(geo,
                                "", "",
                                "", "",
                                "", "",
                                "0", "10", pages.toString())
                    }
                }
            }
        })

        classify_toolbar_search_iv.setOnClickListener {
            when {
                arguments.getString("TYPE") == "SCENE" -> {
                    val delegate: SearchDelegate = SearchDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putInt("PRODUCT_TYPE", 0)
                    delegate.arguments = bundle
                    start(delegate)
                }
                arguments.getString("TYPE") == "NOCTILUCENCE" -> {
                    val delegate: SearchDelegate = SearchDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putInt("PRODUCT_TYPE", 2)
                    delegate.arguments = bundle
                    start(delegate)
                }
                arguments.getString("TYPE") == "VIDEO" -> {
                    val delegate: SearchDelegate = SearchDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putInt("PRODUCT_TYPE", 1)
                    delegate.arguments = bundle
                    start(delegate)
                }
            }
        }

    }

    private fun closeScreen() {
        classify_down_btn.setBackgroundResource(R.drawable.down)
        classify_mask_rl.visibility = View.INVISIBLE
        if (cityNameToShow != "")
            classify_recommand_tv.text = cityNameToShow
        else
            classify_recommand_tv.text = getString(R.string.recommand)
        classify_hsv.visibility = View.VISIBLE
        isScroll(true)
        isExpanded = false
    }

    fun isScroll(bool: Boolean) {
        classify_sv.setOnTouchListener { v, event ->
            !bool
        }
    }

    private fun initVideoGlm() {
        videoGlm = GridLayoutManager(context, 1)
        videoGlm!!.isSmoothScrollbarEnabled = true
        videoGlm!!.isAutoMeasureEnabled = true
        classify_rv.addItemDecoration(GridSpacingItemDecoration(1, DimenUtil().dip2px(context, 14F), true))
        classify_rv.layoutManager = videoGlm
        classify_rv.setHasFixedSize(true)
        classify_rv.isNestedScrollingEnabled = false
    }

    private fun initSceneGlm() {
        sceneGlm = GridLayoutManager(context, 2)
        sceneGlm!!.isSmoothScrollbarEnabled = true
        sceneGlm!!.isAutoMeasureEnabled = true
        classify_rv.addItemDecoration(GridSpacingItemDecoration(2, DimenUtil().dip2px(context, 14F), true))
        classify_rv.layoutManager = sceneGlm
        classify_rv.setHasFixedSize(true)
        classify_rv.isNestedScrollingEnabled = false
    }

    private fun getData(param_scopeGeo: String,
                        param_productType: String, param_resolution: String,
                        param_satelliteId: String, param_startTime: String,
                        param_endTime: String, param_cloud: String,
                        param_type: String, param_pageSize: String, param_pageNum: String) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.sceneSearch(param_scopeGeo,
                param_productType, param_resolution,
                param_satelliteId, param_startTime,
                param_endTime, param_cloud,
                param_type, param_pageSize, param_pageNum, "android")
        EmallLogger.d(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", param_productType, param_resolution,
                param_satelliteId, param_startTime,
                param_endTime, param_cloud,
                param_type, param_pageSize, param_pageNum))
        call.enqueue(object : retrofit2.Callback<SceneSearch> {
            override fun onResponse(call: retrofit2.Call<SceneSearch>, response: retrofit2.Response<SceneSearch>) {
                if (response.body() != null) {
                    if (bottom_rl != null)
                        bottom_rl.visibility = View.INVISIBLE
                    sceneSearch = response.body()!!
                    pagesAmount = sceneSearch.data.pages
//                    productId!!.clear()
                    getSceneData()

                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<SceneSearch>, throwable: Throwable) {}
        })
    }

    private fun getVData(param_type: String) {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.videoHome(param_type, "android")
        call.enqueue(object : retrofit2.Callback<VideoHomeBean> {
            override fun onResponse(call: retrofit2.Call<VideoHomeBean>, response: retrofit2.Response<VideoHomeBean>) {
                if (response.body() != null) {
                    videoHomeBean = response.body()!!
                    getVideoData()
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<VideoHomeBean>, throwable: Throwable) {}
        })
    }

    private fun initRecommendCities() {
        cityName!!.add(getString(R.string.recommand))
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)

        val call = apiService!!.getRecommandCities("android")
        call.enqueue(object : retrofit2.Callback<GetRecommendCitiesBean> {
            override fun onResponse(call: retrofit2.Call<GetRecommendCitiesBean>, response: retrofit2.Response<GetRecommendCitiesBean>) {
                if (response.body() != null) {
                    getRecommendCitiesBean = response.body()!!
                    if (getRecommendCitiesBean.message == "success") {
                        EmallLogger.d(response)
                        val size = getRecommendCitiesBean.data.size
                        if (classify_progressBar != null)
                            classify_progressBar.visibility = View.VISIBLE
                        getData("",
                                "", "",
                                "", "",
                                "", "",
                                "0", "10", pages.toString())//0是标准景
                        for (i in 0 until size) {
                            cityName!!.add(getRecommendCitiesBean.data[i].cityName)
                            val topRl = RelativeLayout(activity)
                            topRl.id = i
                            val topRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                            topRl.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            topRl.layoutParams = topRlParams
                            topRl.isClickable = true
                            topRl.isFocusable = true
                            topRl.setOnClickListener {
                                classify_recommand_tv.text = getRecommendCitiesBean.data[i].cityName
                                if (!data!!.isEmpty())
                                    data!!.clear()
                                productId!!.clear()
                                EmallLogger.d(cityNameToShow)
                                cityNameToShow = getRecommendCitiesBean.data[i].cityName
                                classify_progressBar.visibility = View.VISIBLE
                                geo = getRecommendCitiesBean.data[i].geo
                                SCROLL_TIMES = 0
                                pages = 1
                                getData(getRecommendCitiesBean.data[i].geo,
                                        "", "",
                                        "", "",
                                        "", "",
                                        "0", "10", "1")

                            }
                            classify_ll.addView(topRl, topRlParams)
                            val tv = AppCompatTextView(activity)
                            val tvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                            tvParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                            tvParams.setMargins(DimenUtil().dip2px(activity, 10F), 0, DimenUtil().dip2px(activity, 10F), 0)
                            tv.layoutParams = topRlParams
                            tv.text = getRecommendCitiesBean.data[i].cityName
                            tv.setTextColor(Color.parseColor("#5C5C5C"))
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12F)
                            topRl.addView(tv, tvParams)
                        }
                    } else {
                        Toast.makeText(activity, "getRecommendCities wrong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<GetRecommendCitiesBean>, throwable: Throwable) {}
        })
    }

    private fun getSceneData() {
        val size = sceneSearch.data.searchReturnDtoList.size
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
            model.price = sceneSearch.data.searchReturnDtoList[i].price
            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
            model.productType = "1"
            productId!!.add(sceneSearch.data.searchReturnDtoList[i].productId)
            data!!.add(model)
        }
        initRecyclerView("SCENE")
    }


    private fun getVideoData() {
        EmallLogger.d(videoHomeBean.data[0].detailPath)

        val size = videoHomeBean.data.size
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = videoHomeBean.data[i].detailPath
            model.price = videoHomeBean.data[i].price
            model.time = videoHomeBean.data[i].startTime
            model.title = videoHomeBean.data[i].title
            model.duration = videoHomeBean.data[i].duration
            model.productType = "3"
            productId!!.add(videoHomeBean.data[i].productId)
            data!!.add(model)
        }
        initRecyclerView("VIDEO")
    }

    private fun initRecyclerView(type: String) {
        if (type == "VIDEO") {
            videoAdapter = VideoClassifyAdapter(R.layout.item_classify_video, data, videoGlm)
            if (classify_rv != null)
                classify_rv.adapter = videoAdapter
            if (classify_progressBar != null)
                classify_progressBar.visibility = View.GONE
            if (recommand_mask != null)
                recommand_mask.visibility = View.GONE
            videoAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", productId!![position])
                bundle.putString("type", VIDEO)
                bundle.putString("PAGE_FROM_TEST", "videoClassify")
//                bundle.putString("PAGE_FROM","CLASSIFY")
                delegate!!.arguments = bundle
                start(delegate)
            }
        } else if (type == "SCENE") {
            sceneAdapter = SceneClassifyAdapter(R.layout.item_classify_scene, data, sceneGlm)
            if (classify_rv != null)
                classify_rv.adapter = sceneAdapter
            if (pages < pagesAmount)
                pages += 1
            if (classify_progressBar != null)
                classify_progressBar.visibility = View.GONE
            if (recommand_mask != null)
                recommand_mask.visibility = View.GONE
            sceneAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                EmallLogger.d(position)
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", productId!![position])

                if (arguments.getString("TYPE") == "NOCTILUCENCE") {
                    bundle.putString("type", NOCTILUCENCE)
                } else
                    bundle.putString("type", OPTICS)
//                bundle.putString("PAGE_FROM","CLASSIFY")

                delegate!!.arguments = bundle
                start(delegate)
            }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}