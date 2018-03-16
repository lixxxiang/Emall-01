package com.example.emall_ec.main

import android.graphics.Color
import com.example.emall_core.delegates.bottom.BaseBottomDelegate
import com.example.emall_core.delegates.bottom.BottomTabBean
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.delegates.bottom.ItemBuilder
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.BaseClassifyDelegate
import com.example.emall_ec.main.discover.DiscoverDelegate
import com.example.emall_ec.main.index.IndexDelegate
import com.example.emall_ec.main.classify.ClassifyDelegate
import com.example.emall_ec.main.me.MeDelegate
import com.example.emall_ec.main.program.ProgramDelegate
import com.example.emall_ec.main.special.SpecialDelegate


/**
 * Created by lixiang on 15/02/2018.
 */
open class EcBottomDelegate : BaseBottomDelegate(){
    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate> {
        val items : LinkedHashMap<BottomTabBean, BottomItemDelegate> ?= LinkedHashMap()
        items!!.put(BottomTabBean(R.drawable.home_h, "首页"), IndexDelegate())
        items.put(BottomTabBean(R.drawable.classify_n, "分类"), BaseClassifyDelegate())
        items.put(BottomTabBean(R.drawable.center, "发现"), ProgramDelegate())
        items.put(BottomTabBean(R.drawable.special_n, "专题"), SpecialDelegate())
        items.put(BottomTabBean(R.drawable.me_n, "我的"), MeDelegate())
        return builder.addItems(items)!!.build()!!
    }

    override fun setIndexDelegate(): Int {
        return 0
    }

    override fun setClickedColor(): Int {
        return Color.parseColor("#B4A078")
    }
}