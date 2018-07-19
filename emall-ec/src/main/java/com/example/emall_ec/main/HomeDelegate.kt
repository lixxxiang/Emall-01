package com.example.emall_ec.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.view.StatusBarUtil
import com.example.emall_ec.R
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.main.classify.BaseClassifyDelegate
import com.example.emall_ec.main.index.IndexDelegate
import com.example.emall_ec.main.me.MeDelegate
import com.example.emall_ec.main.program.ProgramIndexDelegate
import com.example.emall_ec.main.special.view.SpecialDelegate
import kotlinx.android.synthetic.main.delegate_home.*
import java.util.*


class HomeDelegate: EmallDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_home
    }

    override fun initial() {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0,null)

        mBottomBar = mBottomNavigationView.findViewById(R.id.mBottomView)
        initFragment()
        initNavigationView()
        changeFragment(0)
    }

    private var mBottomBar: LinearLayout? = null
    private val mIndexDelegate by lazy { IndexDelegate() }
    private val mBaseClassifyDelegate by lazy { BaseClassifyDelegate() }
    private val mProgramIndexDelegate by lazy { ProgramIndexDelegate() }
    private val mSpecialDelegate by lazy { SpecialDelegate() }
    private val mMeDelegate by lazy { MeDelegate() }

    private val mStack = Stack<Fragment>()


    private fun initNavigationView() {
        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
        val mIcon2 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon2)
        val mIcon4 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon4)
        val mIcon5 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon5)

        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
        val mTitle2 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle2)
        val mTitle4 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle3)
        val mTitle5 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle4)

        val mField1 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField1)
        val mField2 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField2)
        val mField3 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField3)
        val mField4 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField4)
        val mField5 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField5)

        mIcon1.setBackgroundResource(R.drawable.home_nav_icon)
        mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorMain))

        mField1.setOnClickListener {
            mIcon1.setBackgroundResource(R.drawable.home_nav_icon)
            mIcon2.setBackgroundResource(R.drawable.classify_nav_icon_gray)
            mIcon4.setBackgroundResource(R.drawable.special_nav_icon_gray)
            mIcon5.setBackgroundResource(R.drawable.me_nav_icon_gray)

            mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorMain))
            mTitle2.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle5.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))

            changeFragment(0)
        }

        mField2.setOnClickListener {
            mIcon1.setBackgroundResource(R.drawable.home_nav_icon_gray)
            mIcon2.setBackgroundResource(R.drawable.classify_nav_icon)
            mIcon4.setBackgroundResource(R.drawable.special_nav_icon_gray)
            mIcon5.setBackgroundResource(R.drawable.me_nav_icon_gray)

            mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity, R.color.colorMain))
            mTitle4.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle5.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            changeFragment(1)

        }

        mField3.setOnClickListener {
            mIcon1.setBackgroundResource(R.drawable.home_nav_icon_gray)
            mIcon2.setBackgroundResource(R.drawable.classify_nav_icon_gray)
            mIcon4.setBackgroundResource(R.drawable.special_nav_icon_gray)
            mIcon5.setBackgroundResource(R.drawable.me_nav_icon_gray)

            mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle5.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
        }

        mField4.setOnClickListener {
            mIcon1.setBackgroundResource(R.drawable.home_nav_icon_gray)
            mIcon2.setBackgroundResource(R.drawable.classify_nav_icon_gray)
            mIcon4.setBackgroundResource(R.drawable.special_nav_icon)
            mIcon5.setBackgroundResource(R.drawable.me_nav_icon_gray)

            mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity, R.color.colorMain))
            mTitle5.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))

            changeFragment(3)

        }

        mField5.setOnClickListener {
            mIcon1.setBackgroundResource(R.drawable.home_nav_icon_gray)
            mIcon2.setBackgroundResource(R.drawable.classify_nav_icon_gray)
            mIcon4.setBackgroundResource(R.drawable.special_nav_icon_gray)
            mIcon5.setBackgroundResource(R.drawable.me_nav_icon)

            mTitle1.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle2.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle4.setTextColor(ContextCompat.getColor(activity, R.color.colorTextGray))
            mTitle5.setTextColor(ContextCompat.getColor(activity, R.color.colorMain))
            changeFragment(4)

        }
    }


    private fun initFragment() {
        val manager = childFragmentManager.beginTransaction()
        manager.add(R.id.mFrameLayout, mIndexDelegate)
        manager.add(R.id.mFrameLayout, mBaseClassifyDelegate)
        manager.add(R.id.mFrameLayout, mProgramIndexDelegate)

        manager.add(R.id.mFrameLayout, mSpecialDelegate)
        manager.add(R.id.mFrameLayout, mMeDelegate)

        manager.commit()
        mStack.add(mIndexDelegate)
        mStack.add(mBaseClassifyDelegate)
        mStack.add(mProgramIndexDelegate)
        mStack.add(mSpecialDelegate)
        mStack.add(mMeDelegate)


    }


    private fun changeFragment(position: Int) {
        val manager = childFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }
}