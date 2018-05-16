package com.example.emall_ec.main.me.collect.type

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.classify.ClassifyDelegate
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.collect.CollectionDelegate
import com.example.emall_ec.main.me.collect.data.MyAllCollectionBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_goods.*
import retrofit2.Retrofit
import java.util.*

class GoodsDelegate : EmallDelegate() {

    private var flag = false
    var myAllCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var myAllCollectionBean = MyAllCollectionBean()
    private var myAllCollectionList: MutableList<MyAllCollectionBean.DataBean.CollectionBean> = mutableListOf()
    private var glm: GridLayoutManager? = null
    private var type = -1
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    var BACK_FROM = String()
    var ALL_OR_TYPE = "ALL"
    fun create(): GoodsDelegate? {
        return GoodsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods
    }

    override fun initial() {
        setSwipeBackEnable(false)
        goods_all_btn2.setOnClickListener {
            if (!flag) {
                goods_screen_rl.visibility = View.VISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#B80017"))
                goods_gray_iv.setBackgroundResource(R.drawable.collection_up)
                goods_all_tv.setTextColor(Color.parseColor("#4A4A4A"))
                flag = true
            } else {
                if (ALL_OR_TYPE == "TYPE") {
                    goods_gray_iv.setBackgroundResource(R.drawable.collection_down_red)
                }else{
                    goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
                    goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
                    goods_all_tv.setTextColor(Color.parseColor("#4A4A4A"))
                }
                goods_screen_rl.visibility = View.INVISIBLE
                flag = false
            }

        }
        goods_all_btn1.setOnClickListener {
            goods_screen_rl.visibility = View.INVISIBLE
            goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
            goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
            goods_all_tv.setTextColor(Color.parseColor("#B80017"))
            getData()
            type = -1
            ALL_OR_TYPE = "ALL"
        }

        goods_rl1.setOnClickListener {
            type = 1
            getDataByType("1")
            reset()
            ALL_OR_TYPE = "TYPE"
        }

        goods_rl3.setOnClickListener {
            type = 3
            getDataByType("3")
            reset()
            ALL_OR_TYPE = "TYPE"

        }

        goods_rl4.setOnClickListener {
            type = 5
            getDataByType("5")
            reset()
            ALL_OR_TYPE = "TYPE"

        }

        collection_btn.setOnClickListener {
//            Toast.makeText(activity, "to what page", Toast.LENGTH_SHORT).show()
            val delegate = ClassifyDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("TYPE", "SCENE")

            delegate!!.arguments = bundle
            getParentDelegate<CollectionDelegate>().start(delegate)
        }

        glm = GridLayoutManager(context, 2)
        glm!!.isSmoothScrollbarEnabled = true
        glm!!.isAutoMeasureEnabled = true
        goods_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        goods_rv.layoutManager = glm
        goods_rv.setHasFixedSize(true)
        goods_rv.isNestedScrollingEnabled = false
        getData()

        goods_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            goods_srl.isRefreshing = true
            Handler().postDelayed({
                if (type != -1) {
                    getDataByType(type.toString())
                } else
                    getData()
                goods_srl.isRefreshing = false
            }, 1200)
        })
    }

    private fun reset() {
        goods_screen_rl.visibility = View.INVISIBLE
        goods_gray_iv.setBackgroundResource(R.drawable.collection_down_red)
        flag = false

    }

    private fun getDataByType(s: String) {
        goods_srl.isRefreshing = true
        myAllCollectionParams!!["productType"] = s
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/myCollectionByType")
                .params(myAllCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        println(response)
                        myAllCollectionBean = Gson().fromJson(response, MyAllCollectionBean::class.java)
                        if (myAllCollectionBean.message == "success") {
                            /**
                             * success
                             */
                            collection_no_result_rl.visibility = View.GONE
                            goods_srl.visibility = View.VISIBLE
                            EmallLogger.d(response)
                            myAllCollectionList = myAllCollectionBean.data.collection
                            val size = myAllCollectionBean.data.collection.size
                            val data: MutableList<Model>? = mutableListOf()

                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = myAllCollectionBean.data.collection[i].thumbnailUrl
                                model.price = myAllCollectionBean.data.collection[i].originalPrice
                                model.time = myAllCollectionBean.data.collection[i].shootingTime
                                model.productId = myAllCollectionBean.data.collection[i].productId
                                model.productType = myAllCollectionBean.data.collection[i].productType
                                model.title = myAllCollectionBean.data.collection[i].title
                                model.duration = myAllCollectionBean.data.collection[i].duration

                                data!!.add(model)
                            }
                            initRecyclerView(data!!)
                        } else if (myAllCollectionBean.message == "方法返回为空") {
                            goods_srl.visibility = View.GONE
                            collection_no_result_rl.visibility = View.VISIBLE
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
                .post()
    }

    private fun getData() {
        myAllCollectionParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        myAllCollectionParams!!["PageNum"] = "1"
        myAllCollectionParams!!["PageSize"] = "10"
        goods_srl.isRefreshing = true


        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)

        val call = apiService!!.myAllCollection(myAllCollectionParams!!["userId"].toString(), myAllCollectionParams!!["PageNum"].toString(), myAllCollectionParams!!["PageSize"].toString())
        call.enqueue(object : retrofit2.Callback<MyAllCollectionBean> {
            override fun onResponse(call: retrofit2.Call<MyAllCollectionBean>, response: retrofit2.Response<MyAllCollectionBean>) {
                if (response.body() != null) {
                    myAllCollectionBean = response.body()!!
                    EmallLogger.d(myAllCollectionBean.message)
                    if (myAllCollectionBean.message == "success") {
                        /**
                         * success
                         */
                        if (collection_no_result_rl != null && goods_srl != null) {
                            collection_no_result_rl.visibility = View.GONE
                            goods_srl.visibility = View.VISIBLE
                            EmallLogger.d(response)
                            myAllCollectionList = myAllCollectionBean.data.collection
                            val size = myAllCollectionBean.data.collection.size
                            var data: MutableList<Model>? = mutableListOf()

                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = myAllCollectionBean.data.collection[i].thumbnailUrl
                                model.price = myAllCollectionBean.data.collection[i].originalPrice
                                model.time = myAllCollectionBean.data.collection[i].shootingTime
                                model.productId = myAllCollectionBean.data.collection[i].productId
                                model.productType = myAllCollectionBean.data.collection[i].productType
                                model.title = myAllCollectionBean.data.collection[i].title
                                model.duration = myAllCollectionBean.data.collection[i].duration

                                data!!.add(model)
                            }
                            initRecyclerView(data!!)
                        }

                    } else if (myAllCollectionBean.message == "方法返回为空") {
                        if (collection_no_result_rl != null && goods_srl != null) {
                            goods_srl.visibility = View.GONE
                            collection_no_result_rl.visibility = View.VISIBLE
                        }
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<MyAllCollectionBean>, throwable: Throwable) {}
        })
    }

    private fun initRecyclerView(data: MutableList<Model>) {

        val mAdapter: SceneClassifyAdapter? = SceneClassifyAdapter(R.layout.item_classify_scene, data, glm)
        goods_rv.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
        goods_srl.isRefreshing = false
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", data[position].productId)
            bundle.putString("type", data[position].productType)
            bundle.putString("PAGE_FROM", "COLLECTION")
            bundle.putString("COLLECTION_TYPE", type.toString())
            delegate!!.arguments = bundle
            getParentDelegate<CollectionDelegate>().start(delegate)
        }
    }



    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val sp = activity.getSharedPreferences("COLLECTION", Context.MODE_PRIVATE)
        if (sp.getString("collection", "") == "true") {
            if (sp.getString("collection_type", "") == "-1")
                getData()
            else
                getDataByType(sp.getString("collection_type", ""))
        }
        sp.edit().clear().commit()
    }
}