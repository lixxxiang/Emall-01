package com.example.emall_ec.main

import android.graphics.Color
import android.graphics.Color.parseColor
import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomTabBean
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.delegates.bottom.ItemBuilder
import com.example.emall_ec.main.index.IndexDelegate
import com.example.emall_ec.main.sort.SortDelegate


/**
 * Created by lixiang on 15/02/2018.
 */
class EcBottomDelegate : BaseBottomDelegate(){
    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate> {
        val items : LinkedHashMap<BottomTabBean, BottomItemDelegate> ?= LinkedHashMap()
        items!!.put(BottomTabBean("{fa-home}", "主页"), IndexDelegate())
        items.put(BottomTabBean("{fa-sort}", "分类"), SortDelegate())
        items.put(BottomTabBean("{fa-compass}", "发现"), IndexDelegate())
        items.put(BottomTabBean("{fa-shopping-cart}", "购物车"), IndexDelegate())
        items.put(BottomTabBean("{fa-user}", "我的"), IndexDelegate())
        return builder.addItems(items)!!.build()!!
    }

    override fun setIndexDelegate(): Int {
        return 0
    }

    override fun setClickedColor(): Int {
        return Color.parseColor("#ffff8800")
    }
}