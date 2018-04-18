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
import android.support.v7.widget.AppCompatTextView
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.emall_core.util.dimen.DimenUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.classify.data.*
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.collect.data.MyAllCollectionBean
import com.google.gson.Gson
import retrofit2.Retrofit


/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : EmallDelegate() {

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

    var param_scopeGeo = String()
    var param_productType = String()
    var param_resolution = String()
    var param_satelliteId = String()
    var param_startTime = String()
    var param_endTime = String()
    var param_cloud = String()
    var param_type = String()
    var param_pageSize = String()
    var param_pageNum = String()
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

        initRecommendCities()
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

        if (sceneAdapter != null) {
            sceneAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", productId!![position])
                bundle.putString("type", "1")
                delegate!!.arguments = bundle
                start(delegate)
            }
        }

        if (videoAdapter != null) {
            videoAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", productId!![position])
                bundle.putString("type", "3")
                delegate!!.arguments = bundle
                start(delegate)
            }
        }

        classify_appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    classify_toolbar.title = ""
                    classify_toolbar.setNavigationIcon(R.drawable.ic_back_small)
                    classify_toolbar_search_iv.setBackgroundResource(R.drawable.ic_search)
                } else if (state == State.COLLAPSED) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    if (arguments.getString("TYPE") == "SCENE") {
                        classify_toolbar.title = "光学1级"
                    } else if (arguments.getString("TYPE") == "VIDEO") {
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

    private fun getData() {
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.sceneSearch("",
                "","",
                "","",
                "","",
                "0","10","1")
        call.enqueue(object : retrofit2.Callback<SceneSearch> {

            override fun onResponse(call: retrofit2.Call<SceneSearch>, response: retrofit2.Response<SceneSearch>) {
                if (response.body() != null) {
                    EmallLogger.d(response.body()!!.data.searchReturnDtoList[0].thumbnailUrl)
                    sceneSearch = response.body()!!
//                    bundle!!.putString("TYPE","SCENE")
//                    bundle.putSerializable("SCENE_DATA", sceneSearch)
//                    delegate.arguments = bundle
//                    (DELEGATE as EcBottomDelegate).start(delegate)
                    if (arguments.getString("TYPE") == "SCENE") {
                        getSceneData()
                        classify_title_tv.text = resources.getString(R.string.optics_1)
                    } else if (arguments.getString("TYPE") == "VIDEO") {
                        getVideoData()
                        classify_title_tv.text = resources.getString(R.string.video1A_1B)
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<SceneSearch>, throwable: Throwable) {}
        })
    }

    private fun initRecommendCities() {
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/getRecommendCities")
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        getRecommendCitiesBean = Gson().fromJson(response, GetRecommendCitiesBean::class.java)
                        if (getRecommendCitiesBean.message == "success") {
                            EmallLogger.d(response)
                            val size = getRecommendCitiesBean.data.size
                            for (i in 0 until size) {
//                                cityName!!.add(getRecommendCitiesBean.data[i].cityName)
                                val topRl = RelativeLayout(activity)
                                topRl.id = i
                                val topRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                                topRl.setBackgroundColor(Color.parseColor("#FFFFFF"))
                                topRl.layoutParams = topRlParams
                                topRl.isClickable = true
                                topRl.isFocusable = true
                                topRl.setOnClickListener {
                                    classify_recommand_tv.text = getRecommendCitiesBean.data[i].cityName
                                    getData()
                                }
                                classify_ll.addView(topRl, topRlParams)
                                val tv = AppCompatTextView(activity)
                                val tvParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                                tvParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                                tvParams.setMargins(DimenUtil().dip2px(activity, 10F), 0, DimenUtil().dip2px(activity, 10F), 0)
                                tv.layoutParams = topRlParams
                                tv.text = getRecommendCitiesBean.data[i].cityName
                                tv.setTextColor(Color.parseColor("#5C5C5C"))
                                tv.textSize = 14F
                                topRl.addView(tv, tvParams)
                            }
                        } else {
                            Toast.makeText(activity, "getRecommendCities wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .get()


    }

    private fun getSceneData() {
        EmallLogger.d(sceneSearch.data.searchReturnDtoList[0].thumbnailUrl)
        val size = sceneSearch.data.searchReturnDtoList.size
//        ssp!!.clear()
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
        videoHomeBean = arguments.getSerializable("VIDEO_DATA") as VideoHomeBean
        EmallLogger.d(videoHomeBean.data[0].detailPath)

        val size = videoHomeBean.data.size
        for (i in 0 until size) {
            val model = Model()
            model.imageUrl = videoHomeBean.data[i].detailPath
            model.price = videoHomeBean.data[i].price
            model.time = videoHomeBean.data[i].startTime
            model.title = videoHomeBean.data[i].title
            model.productType = "3"
            productId!!.add(videoHomeBean.data[i].productId)
            data!!.add(model)
        }
        initRecyclerView("VIDEO")
    }

    private fun initRecyclerView(type: String) {
        if (type == "VIDEO") {
            val glm = GridLayoutManager(context, 1)
            glm.isSmoothScrollbarEnabled = true
            glm.isAutoMeasureEnabled = true
            classify_rv.addItemDecoration(GridSpacingItemDecoration(1, DimenUtil().dip2px(context, 14F), true))
            classify_rv.layoutManager = glm
            classify_rv.setHasFixedSize(true)
            classify_rv.isNestedScrollingEnabled = false
            videoAdapter = VideoClassifyAdapter(R.layout.item_classify_video, data, glm)
            classify_rv.adapter = videoAdapter
        } else if (type == "SCENE") {
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


    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}