package com.example.emall_ec.main.me.collect.type

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
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
import com.example.emall_ec.main.search.SearchResultDelegate
import com.example.emall_ec.main.sign.SignHandler
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_goods.*
import kotlinx.android.synthetic.main.delegate_optics1.*
import java.util.*

class GoodsDelegate : EmallDelegate() {

    private var flag = false
    var myAllCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var myAllCollectionBean = MyAllCollectionBean()
    private var myAllCollectionList: MutableList<MyAllCollectionBean.DataBean.CollectionBean> = mutableListOf()
    private var data: MutableList<Model>? = mutableListOf()
    private var glm: GridLayoutManager? = null
    fun create(): GoodsDelegate? {
        return GoodsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods
    }

    override fun initial() {
        goods_gray_tv.setOnClickListener {
            if (!flag) {
//                goods_top_ll.visibility = View.VISIBLE
                goods_screen_rl.visibility = View.VISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#B80017"))
                goods_gray_iv.setBackgroundResource(R.drawable.ic_up_red)
                flag = true
            } else {
//                goods_top_ll.visibility = View.INVISIBLE
                goods_screen_rl.visibility = View.INVISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#9B9B9B"))
                goods_gray_iv.setBackgroundResource(R.drawable.ic_down_gray)
                flag = false
            }

        }

        glm = GridLayoutManager(context, 2)
        glm!!.isSmoothScrollbarEnabled = true
        glm!!.isAutoMeasureEnabled = true
        goods_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        goods_rv.layoutManager = glm
        goods_rv.setHasFixedSize(true)
        goods_rv.isNestedScrollingEnabled = false
        getData()
    }

    private fun getData() {
        myAllCollectionParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        myAllCollectionParams!!["PageNum"] = "1"
        myAllCollectionParams!!["PageSize"] = "10"

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
                            EmallLogger.d(response)
                            myAllCollectionList = myAllCollectionBean.data.collection
                            val size = myAllCollectionBean.data.collection.size
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
                        } else {
                            Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT).show()
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
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
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
        if (!data!!.isEmpty()) {
            data!!.clear()
        }
        getData()

    }
}