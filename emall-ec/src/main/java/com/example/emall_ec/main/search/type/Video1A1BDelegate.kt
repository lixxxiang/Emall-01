package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.classify.data.VideoClassifyAdapter
import com.example.emall_ec.main.net.CommonUrls
import com.example.emall_ec.main.search.data.VideoSearchBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_video1a1b.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class Video1A1BDelegate : EmallDelegate(){
    private var videoSearchParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var videoSearchBean = VideoSearchBean()
    var screenIsShow = false
    private var data: MutableList<Model> ?= mutableListOf()

    override fun setLayout(): Any? {
        return R.layout.delegate_video1a1b
    }

    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        videoSearchParams!!["geo"] = Optics1Delegate().geoFormat(sp.getString("GEO", ""))
        EmallLogger.d(Optics1Delegate().geoFormat(sp.getString("GEO", "")))
        videoSearchParams!!["type"] = "0"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/videoSearch")
                .params(videoSearchParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        videoSearchBean = Gson().fromJson(response, VideoSearchBean::class.java)
                        if(videoSearchBean.status != 103) {
                            val size = videoSearchBean.data.size
                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = videoSearchBean.data[i].detailPath
                                model.price = videoSearchBean.data[i].price
                                model.time = videoSearchBean.data[i].startTime
                                model.title = videoSearchBean.data[i].title
                                model.productType = "3"
                                data!!.add(model)
                            }
                            initRecyclerView(context, data!!, video1a1b_rv)
                        }
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
//        video1a1b_screen_tv.setOnClickListener {
//            if (!screenIsShow) {
//                video1a1b_screen_rl.visibility = View.VISIBLE
//                screenIsShow = true
//            } else {
//                video1a1b_screen_rl.visibility = View.INVISIBLE
//                screenIsShow = false
//            }
//        }
    }
    private fun initRecyclerView(context: Context, data: MutableList<Model>, recyclerView: RecyclerView) {
        val glm = GridLayoutManager(context, 1)
        glm.isSmoothScrollbarEnabled = true
        glm.isAutoMeasureEnabled = true
        recyclerView.addItemDecoration(GridSpacingItemDecoration(1, 30, true))
        recyclerView.layoutManager = glm
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        val mAdapter: VideoClassifyAdapter? = VideoClassifyAdapter(R.layout.item_classify_video, data, glm)
        recyclerView.adapter = mAdapter
    }
}
