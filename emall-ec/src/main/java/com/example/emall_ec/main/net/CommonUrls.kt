package com.example.emall_ec.main.net

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.emall_core.delegates.web.WebDelegate
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
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.search.SearchResultDelegate
import com.google.gson.Gson
import java.util.*


/**
 * Created by lixiang on 2018/3/20.
 */
class CommonUrls() {
    var sceneSearch = SceneSearch()
    private var data: MutableList<Model>? = mutableListOf()
    private var type = String()
    private var productIdList: MutableList<SceneSearch.DataBean.SearchReturnDtoListBean> = mutableListOf()
    fun sceneSearch(context: Context, sceneSearchParams: WeakHashMap<String, Any>?, recyclerView: RecyclerView, t: String): MutableList<Model>? {
        type = t
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(sceneSearchParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        if (sceneSearch.status != 103) {
                            productIdList = sceneSearch.data.searchReturnDtoList
                            val size = sceneSearch.data.searchReturnDtoList.size
                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                                model.price = sceneSearch.data.searchReturnDtoList[i].price
                                model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                                model.productId = sceneSearch.data.searchReturnDtoList[i].productId
                                data!!.add(model)
                                EmallLogger.d(data!![0].imageUrl)

                            }
//                            initRecyclerView(context, data!!, recyclerView)

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
        EmallLogger.d(data!![0].imageUrl)
        return data!!
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

        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            EmallLogger.d(position)
            if (type == "OPTICS") {
                val delegate = GoodsDetailDelegate().create()
                val bundle: Bundle? = Bundle()
                bundle!!.putString("productId", sceneSearch.data.searchReturnDtoList[position].productId)
                bundle.putString("type", "1")
                delegate!!.arguments = bundle
                SearchResultDelegate().start(delegate)
            }
        }
    }

}