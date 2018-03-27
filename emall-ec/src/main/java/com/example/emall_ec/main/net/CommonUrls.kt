package com.example.emall_ec.main.net

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneSearch
import com.google.gson.Gson
import java.util.*


/**
 * Created by lixiang on 2018/3/20.
 */
class CommonUrls {
    var sceneSearch = SceneSearch()
    private var data: MutableList<Model> ?= mutableListOf()
    fun sceneSearch(context: Context, sceneSearchParams: WeakHashMap<String, Any>?, recyclerView: RecyclerView) {
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(sceneSearchParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        val size = sceneSearch.data.searchReturnDtoList.size
                        for (i in 0 until size) {
                            val model = Model()
                            model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                            model.price = sceneSearch.data.searchReturnDtoList[i].price
                            model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                            data!!.add(model)
                        }
                        initRecyclerView(context, data!!, recyclerView)
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

    private fun initRecyclerView(context: Context, data: MutableList<Model>, recyclerView: RecyclerView) {
        val glm = GridLayoutManager(context, 2)
        glm.isSmoothScrollbarEnabled = true
        glm.isAutoMeasureEnabled = true
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        recyclerView.layoutManager = glm
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        val mAdapter: SceneClassifyAdapter? = SceneClassifyAdapter(R.layout.item_classify_scene, data, glm)
        recyclerView.adapter = mAdapter
    }

}