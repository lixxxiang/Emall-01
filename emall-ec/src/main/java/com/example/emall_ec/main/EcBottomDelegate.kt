package com.example.emall_ec.main

import android.graphics.Color
import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomTabBean
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.delegates.bottom.ItemBuilder
import com.example.emall_ec.R
import com.example.emall_ec.main.discover.DiscoverDelegate
import com.example.emall_ec.main.index.IndexDelegate
import com.example.emall_ec.main.classify.ClassifyDelegate
import com.example.emall_ec.main.me.MeDelegate


/**
 * Created by lixiang on 15/02/2018.
 */
class EcBottomDelegate : BaseBottomDelegate(){
    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate> {
        val items : LinkedHashMap<BottomTabBean, BottomItemDelegate> ?= LinkedHashMap()
        items!!.put(BottomTabBean(R.drawable.test_icon_small, "主页"), IndexDelegate())
        items.put(BottomTabBean(R.drawable.test_icon_small, "分类"), ClassifyDelegate())
        items.put(BottomTabBean(R.drawable.test_icon, "发现"), DiscoverDelegate())
        items.put(BottomTabBean(R.drawable.test_icon_small, "购物车"), ClassifyDelegate())
        items.put(BottomTabBean(R.drawable.test_icon_small, "我的"), MeDelegate())
        return builder.addItems(items)!!.build()!!
    }

    override fun setIndexDelegate(): Int {
        return 0
    }

    override fun setClickedColor(): Int {
        return Color.parseColor("#ffff8800")
    }
}