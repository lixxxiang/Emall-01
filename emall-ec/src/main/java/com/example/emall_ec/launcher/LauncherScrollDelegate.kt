package com.example.emall_ec.launcher

import android.view.View
import android.widget.AdapterView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.example.emall_core.delegates.BaseDelegate
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.ui.LauncherHolderCreator
import com.example.emall_ec.R



/**
 * Created by lixiang on 02/02/2018.
 */
class LauncherScrollDelegate(): EmallDelegate(), AdapterView.OnItemClickListener, OnItemClickListener {
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onItemClick(position: Int) {

    }

    private var mConvenientBanner : ConvenientBanner<Int>? = null
    private val INTEGERS = mutableListOf<Int>()


    fun initBanner(){
        INTEGERS.add(R.mipmap.launcher_01)
        INTEGERS.add(R.mipmap.launcher_02)
        INTEGERS.add(R.mipmap.launcher_03)
        INTEGERS.add(R.mipmap.launcher_04)
        INTEGERS.add(R.mipmap.launcher_05)
        mConvenientBanner!!.setPages(LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(intArrayOf(R.drawable.dot_normal, R.drawable.dot_focus))
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .isCanLoop = false

    }
    override fun setLayout(): Any? {
        mConvenientBanner = ConvenientBanner(context)
        return mConvenientBanner


    }

    override fun initial() {
        initBanner()
    }
}