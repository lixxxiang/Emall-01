package com.example.emall_ec.main.index

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.SimpleClickListener
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.index.dailypic.DailyPicDelegate
import com.example.emall_ec.main.program.ProgramDelegate


/**
 * Created by lixiang on 2018/2/26.
 */
class IndexItemClickListener(private val DELEGATE: EmallDelegate) : SimpleClickListener() {

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val delegate : ProgramDelegate = ProgramDelegate().create()!!
        DELEGATE.start(delegate)
        if (position == 1){
            DELEGATE.start(DailyPicDelegate().create())
        }
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        println("CHILD" + position)
    }

    override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}

    companion object {
        fun create(delegate: EmallDelegate): SimpleClickListener {
            return IndexItemClickListener(delegate)
        }
    }
}