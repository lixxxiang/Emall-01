package com.example.emall_ec.main.detail

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.delegate_goods_detail.*

/**
 * Created by lixiang on 2018/2/26.
 */
class GoodsDetailDelegate : BottomItemDelegate() {

    fun create(): GoodsDetailDelegate? {
        return GoodsDetailDelegate()
    }
    override fun initial() {

        val mTitles = arrayOf("预览图","参数","位置")
        val mIconUnselectIds = intArrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect)
        val mIconSelectIds = intArrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select)
        var mTabEntities : ArrayList<CustomTabEntity> ?= ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            tl_6.setTabData(mTabEntities)
        }
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_goods_detail
    }

    internal inner class TabEntity(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {

        override fun getTabTitle(): String {
            return title
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }
    }
}
