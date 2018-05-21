package com.example.emall_ec.main.detail.example

import android.graphics.Paint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.delegate_goods_detail.*
import kotlinx.android.synthetic.main.delegate_optics1_example.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.example.emall_ec.main.detail.example.Optics1ExampleDelegate.MyPagerAdapter
import com.example.emall_ec.main.detail.example.optics.MultispectralDelegate
import com.example.emall_ec.main.detail.example.optics.PanchromaticDelegate
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.delegate_noctilucence_example.*


class Optics1ExampleDelegate : EmallDelegate() {
    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("全色", "多光谱")
    private var mAdapter: MyPagerAdapter? = null
    private var mTabLayout_2: CommonTabLayout? = null
    fun create(): Optics1ExampleDelegate?{
        return Optics1ExampleDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_optics1_example
    }

    override fun initial() {

        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        optics1_example_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(optics1_example_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        optics1_example_toolbar.setNavigationIcon(R.drawable.ic_back_small)
        optics1_example_toolbar.setNavigationOnClickListener {
            pop()
        }
        initTabLayout()

    }

    private fun initTabLayout() {
        val mTitles = arrayOf("全色", "多光谱")
        val mIconUnselectIds = intArrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect)
        val mIconSelectIds = intArrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select)
        val mTabEntities: ArrayList<CustomTabEntity>? = ArrayList()
        for (i in 0 until mTitles.size) {
            mTabEntities!!.add(GoodsDetailDelegate.TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
            optics1_example_ctl.setTabData(mTabEntities)
        }
        mTabLayout_2 = ViewFindUtils.find(activity.window.decorView, R.id.optics1_example_ctl)

        mFragments.add(MultispectralDelegate())
        mFragments.add(PanchromaticDelegate())

        mAdapter = MyPagerAdapter(activity.supportFragmentManager)
        vp.adapter = mAdapter
        mTabLayout_2!!.setTabData(mTabEntities)
        mTabLayout_2!!.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
                }
            }
        })

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                mTabLayout_2!!.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        vp.currentItem = 1
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }
}

