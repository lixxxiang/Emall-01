package com.example.emall_ec.main.me.collect.type

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.collect.CollectionDelegate
import com.example.emall_ec.main.me.collect.data.MyAllCollectionBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_goods.*
import java.util.*

class GoodsDelegate : EmallDelegate() {

    private var flag = false
    var myAllCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var myAllCollectionBean = MyAllCollectionBean()
    private var myAllCollectionList: MutableList<MyAllCollectionBean.DataBean.CollectionBean> = mutableListOf()
    private var glm: GridLayoutManager? = null

    private var type = -1
    fun create(): GoodsDelegate? {
        return GoodsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods
    }

    override fun initial() {
        goods_all_btn2.setOnClickListener {
            if (!flag) {
                goods_screen_rl.visibility = View.VISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#B80017"))
                goods_gray_iv.setBackgroundResource(R.drawable.collection_up)
                goods_all_tv.setTextColor(Color.parseColor("#4A4A4A"))
                flag = true
            } else {
                goods_screen_rl.visibility = View.INVISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
                goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
                goods_all_tv.setTextColor(Color.parseColor("#4A4A4A"))
                flag = false
            }

        }
        goods_all_btn1.setOnClickListener {
            goods_screen_rl.visibility = View.INVISIBLE
            goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
            goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
            goods_all_tv.setTextColor(Color.parseColor("#B80017"))
        }

        goods_rl1.setOnClickListener {
            type = 1
            getDataByType("1")
            reset()
        }

//        goods_rl2.setOnClickListener {
//            type = 2
//            getDataByType("2")
//            reset()
//        }

        goods_rl3.setOnClickListener {
            type = 3
            getDataByType("3")
            reset()
        }

        goods_rl4.setOnClickListener {
            type = 5
            getDataByType("5")
            reset()
        }

        collection_btn.setOnClickListener {
            Toast.makeText(activity, "to what page", Toast.LENGTH_SHORT).show()
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
                getData()
                goods_srl.isRefreshing = false
            }, 1200)
        })
    }

    private fun reset() {
        goods_screen_rl.visibility = View.INVISIBLE
        goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
        goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
    }

    private fun getDataByType(s: String) {
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
        println(myAllCollectionParams)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/myAllCollection")
                .params(myAllCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
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
                            var data: MutableList<Model>? = mutableListOf()

                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = myAllCollectionBean.data.collection[i].thumbnailUrl
                                model.price = myAllCollectionBean.data.collection[i].originalPrice
                                model.time = myAllCollectionBean.data.collection[i].shootingTime
                                model.productId = myAllCollectionBean.data.collection[i].productId
                                model.productType = myAllCollectionBean.data.collection[i].productType
                                model.title = myAllCollectionBean.data.collection[i].title
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

    private fun initRecyclerView(data: MutableList<Model>) {

        val mAdapter: SceneClassifyAdapter? = SceneClassifyAdapter(R.layout.item_classify_scene, data, glm)
        goods_rv.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", data[position].productId)
            bundle.putString("type", data[position].productType)
            delegate!!.arguments = bundle
            getParentDelegate<CollectionDelegate>().start(delegate)
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        goods_gray_tv.setTextColor(Color.parseColor("#4A4A4A"))
        goods_gray_iv.setBackgroundResource(R.drawable.collection_down)
        goods_all_tv.setTextColor(Color.parseColor("#B80017"))

        getDataByType(type.toString())

    }
}