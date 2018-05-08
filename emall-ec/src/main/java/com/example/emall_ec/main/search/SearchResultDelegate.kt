package com.example.emall_ec.main.search

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.order.Find_tab_Adapter
import com.example.emall_ec.main.search.type.NoctilucenceDelegate
import com.example.emall_ec.main.search.type.Optics1Delegate
import com.example.emall_ec.main.search.type.Video1A1BDelegate
import kotlinx.android.synthetic.main.delegate_order_list.*
import kotlinx.android.synthetic.main.delegate_search_result.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by lixiang on 2018/3/20.
 */
class SearchResultDelegate : BottomItemDelegate() {
    var listTitle: MutableList<String>? = mutableListOf()
    var listFragment: MutableList<Fragment>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null
    var geo = String()
    var delegate : SearchResultDelegate ?= null

    fun create(): SearchResultDelegate? {
        return SearchResultDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_search_result
    }

    override fun initial() {
        search_result_toolbar.title = resources.getString(R.string.search_result)
        (activity as AppCompatActivity).setSupportActionBar(search_result_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        search_result_toolbar.setNavigationOnClickListener {
            pop()
         }
        delegate = this
        geo = arguments.getString("GEO")
        EmallLogger.d(geo)
        initControls()
    }

    private fun initControls() {

        val optics1Delegate = Optics1Delegate()
        val video1A1BDelegate = Video1A1BDelegate()
        val noctilucenceDelegate = NoctilucenceDelegate()

        listFragment!!.add(optics1Delegate)
        listFragment!!.add(video1A1BDelegate)
        listFragment!!.add(noctilucenceDelegate)

        listTitle!!.add(resources.getString(R.string.optics_1))
        listTitle!!.add(resources.getString(R.string.video1A_1B))
        listTitle!!.add(resources.getString(R.string.noctilucence))

        search_result_tl.tabMode = TabLayout.MODE_FIXED
        search_result_tl.addTab(search_result_tl.newTab().setText(listTitle!![0]))
        search_result_tl.addTab(search_result_tl.newTab().setText(listTitle!![1]))
        search_result_tl.addTab(search_result_tl.newTab().setText(listTitle!![2]))

        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        search_result_vp.adapter = fAdapter
        search_result_tl.setupWithViewPager(search_result_vp)
        search_result_vp.offscreenPageLimit = 3
        search_result_vp.currentItem = arguments.getInt("PRODUCT_TYPE")
//        optics_screen_tv.setOnClickListener {
//            optics_screen_rl.visibility = View.VISIBLE
//        }


    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}