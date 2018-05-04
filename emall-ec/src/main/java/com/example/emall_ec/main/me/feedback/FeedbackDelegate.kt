package com.example.emall_ec.main.me.feedback

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.R.id.feedback_tl
import com.example.emall_ec.R.id.feedback_vp
import com.example.emall_ec.main.me.collect.type.ContentDelegate
import com.example.emall_ec.main.me.collect.type.GoodsDelegate
import com.example.emall_ec.main.order.Find_tab_Adapter
import kotlinx.android.synthetic.main.delegate_feedback.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class FeedbackDelegate : EmallDelegate() {
    var listTitle: MutableList<String>? = mutableListOf()
    var listFragment: MutableList<Fragment>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null
    fun create(): FeedbackDelegate? {
        return FeedbackDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_feedback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);//加上这句话，menu才会显示出来
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        feedback_toolbar.title = getString(R.string.feedback)
        (activity as AppCompatActivity).setSupportActionBar(feedback_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        feedback_toolbar.setNavigationOnClickListener {
            pop()
        }
        feedback_toolbar.inflateMenu(R.menu.feedback_menu)
        feedback_toolbar.setOnMenuItemClickListener { item ->
            when(item!!.itemId){
                R.id.menu_edit -> EmallLogger.d("fsdfsdfsds")
            }
            true
        }
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        initControls()

    }

    private fun initControls() {
        val myOpinionDelegate = MyOpinionDelegate()
        val commonProblemDelegate = CommonProblemDelegate()
        listFragment!!.add(myOpinionDelegate)
        listFragment!!.add(commonProblemDelegate)

        listTitle!!.add(getString(R.string.my_opinion))
        listTitle!!.add(getString(R.string.common_question))

        feedback_tl.tabMode = TabLayout.MODE_FIXED
        feedback_tl.addTab(feedback_tl.newTab().setText(listTitle!![0]))
        feedback_tl.addTab(feedback_tl.newTab().setText(listTitle!![1]))

        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        feedback_vp.adapter = fAdapter
        feedback_tl.setupWithViewPager(feedback_vp)
        feedback_vp.offscreenPageLimit = 2
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity.menuInflater.inflate(R.menu.feedback_menu, menu)

    }
}