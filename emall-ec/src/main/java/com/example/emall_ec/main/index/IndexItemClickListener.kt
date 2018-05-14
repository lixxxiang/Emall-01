package com.example.emall_ec.main.index

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.SimpleClickListener
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.main.index.dailypic.DailyPicDelegate


/**
 * Created by lixiang on 2018/2/26.
 */
class IndexItemClickListener(private val DELEGATE: EmallDelegate) : SimpleClickListener() {

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
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