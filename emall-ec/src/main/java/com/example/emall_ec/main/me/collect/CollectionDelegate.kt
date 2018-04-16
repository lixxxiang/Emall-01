package com.example.emall_ec.main.me.collect

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.me.collect.type.ContentDelegate
import com.example.emall_ec.main.me.collect.type.GoodsDelegate
import com.example.emall_ec.main.order.Find_tab_Adapter
import com.example.emall_ec.main.search.type.NoctilucenceDelegate
import com.example.emall_ec.main.search.type.Optics1Delegate
import com.example.emall_ec.main.search.type.Video1A1BDelegate
import kotlinx.android.synthetic.main.delegate_collection.*
import kotlinx.android.synthetic.main.delegate_search_result.*

class CollectionDelegate : EmallDelegate() {
    var listTitle: MutableList<String>? = mutableListOf()
    var listFragment: MutableList<Fragment>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null

    fun create(): CollectionDelegate?{
        return CollectionDelegate()
    }
    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        collection_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(collection_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collection_toolbar.setNavigationOnClickListener {
            pop()
        }

        initControls()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_collection
    }

    private fun initControls() {

        val goodsDelegate = GoodsDelegate()
        val contentDelegate = ContentDelegate()

        listFragment!!.add(goodsDelegate)
        listFragment!!.add(contentDelegate)

        listTitle!!.add(resources.getString(R.string.goods))
        listTitle!!.add(resources.getString(R.string.content))

        collection_tl.tabMode = TabLayout.MODE_FIXED
        collection_tl.addTab(collection_tl.newTab().setText(listTitle!![0]))
        collection_tl.addTab(collection_tl.newTab().setText(listTitle!![1]))

        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        collection_vp.adapter = fAdapter
        collection_tl.setupWithViewPager(collection_vp)
        collection_vp.offscreenPageLimit = 2
//        optics_screen_tv.setOnClickListener {
//            optics_screen_rl.visibility = View.VISIBLE
//        }


    }
}