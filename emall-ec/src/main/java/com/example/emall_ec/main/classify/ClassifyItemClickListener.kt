package com.example.emall_ec.main.classify

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.SimpleClickListener
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.GoodsDetailDelegate

/**
 * Created by lixiang on 2018/3/26.
 */
class ClassifyItemClickListener (private val DELEGATE: EmallDelegate) : SimpleClickListener(){
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//        RestClient().builder()
//                .url("http://59.110.162.194:5201/global/homePageUnits")
//                .success(object : ISuccess {
//                    override fun onSuccess(response: String) {
//                        EmallLogger.d(response)
//                    }
//                })
//                .build()
//                .get()
        val delegate = GoodsDetailDelegate().create()
        val bundle: Bundle? = Bundle()
//        bundle!!.putString("productId", productId!![position])
        bundle!!.putString("type", "1")
        delegate!!.arguments = bundle
        DELEGATE.start(delegate)
    }
}