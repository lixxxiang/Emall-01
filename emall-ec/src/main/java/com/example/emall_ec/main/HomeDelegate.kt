//package com.example.emall_ec.main
//
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v4.content.ContextCompat
//import android.support.v7.widget.AppCompatImageView
//import android.support.v7.widget.AppCompatTextView
//import android.widget.LinearLayout
//import android.widget.RelativeLayout
//import com.example.emall_core.util.view.StatusBarUtil
//import com.example.emall_ec.R
//import com.example.emall_ec.main.bottom.BottomItemDelegate
//import java.util.*
//
//
//class HomeDelegate: BottomItemDelegate() {
//    override fun setLayout(): Any? {
//        return R.layout.delegate_home
//    }
//
//    override fun initial() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0,null)
//
//        mBottomBar = mBottomNavigationView.findViewById(R.id.mBottomView)
//        initFragment()
//        initNavigationView()
//        changeFragment(0)
//    }
//
//    private var mBottomBar: LinearLayout? = null
//    private val mHomeFragment by lazy { HomeFragment() }
//    private val mMapFragment by lazy { MapFragment() }
//    private val mMessageFragment by lazy { MessageFragment() }
//    private val mUserFragment by lazy { UserFragment() }
//    private val mStack = Stack<Fragment>()
//
//
//    private fun initNavigationView() {
//        val mIcon1 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon1)
//        val mIcon2 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon2)
//        val mIcon3 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon3)
//        val mIcon4 = mBottomNavigationView.findViewById<AppCompatImageView>(R.id.mIcon4)
//
//        val mTitle1 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle1)
//        val mTitle2 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle2)
//        val mTitle3 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle3)
//        val mTitle4 = mBottomNavigationView.findViewById<AppCompatTextView>(R.id.mTitle4)
//
//        val mField1 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField1)
//        val mField2 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField2)
//        val mField3 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField3)
//        val mField4 = mBottomNavigationView.findViewById<RelativeLayout>(R.id.mField4)
//
//        mIcon1.setBackgroundResource(R.drawable.ic_home_h)
//        mTitle1.setTextColor(ContextCompat.getColor(this, R.color.colorMain))
//
//        mField1.setOnClickListener {
//            mIcon1.setBackgroundResource(R.drawable.ic_home_h)
//            mIcon2.setBackgroundResource(R.drawable.ic_map)
//            mIcon3.setBackgroundResource(R.drawable.ic_message)
//            mIcon4.setBackgroundResource(R.drawable.ic_user)
//
//            mTitle1.setTextColor(ContextCompat.getColor(this, R.color.colorMain))
//            mTitle2.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle3.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle4.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//
//            changeFragment(0)
//        }
//
//        mField2.setOnClickListener {
//            mIcon1.setBackgroundResource(R.drawable.ic_home)
//            mIcon2.setBackgroundResource(R.drawable.ic_map_h)
//            mIcon3.setBackgroundResource(R.drawable.ic_message)
//            mIcon4.setBackgroundResource(R.drawable.ic_user)
//
//            mTitle1.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle2.setTextColor(ContextCompat.getColor(this, R.color.colorMain))
//            mTitle3.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle4.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            changeFragment(1)
//
//        }
//
//        mField3.setOnClickListener {
//            mIcon1.setBackgroundResource(R.drawable.ic_home)
//            mIcon2.setBackgroundResource(R.drawable.ic_map)
//            mIcon3.setBackgroundResource(R.drawable.ic_message_h)
//            mIcon4.setBackgroundResource(R.drawable.ic_user)
//
//            mTitle1.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle2.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle3.setTextColor(ContextCompat.getColor(this, R.color.colorMain))
//            mTitle4.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            changeFragment(2)
//
//        }
//
//        mField4.setOnClickListener {
//            mIcon1.setBackgroundResource(R.drawable.ic_home)
//            mIcon2.setBackgroundResource(R.drawable.ic_map)
//            mIcon3.setBackgroundResource(R.drawable.ic_message)
//            mIcon4.setBackgroundResource(R.drawable.ic_user_h)
//
//            mTitle1.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle2.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle3.setTextColor(ContextCompat.getColor(this, R.color.colorTextGray))
//            mTitle4.setTextColor(ContextCompat.getColor(this, R.color.colorMain))
//
//            changeFragment(3)
//
//        }
//    }
//
//
//    private fun initFragment() {
//        val manager = supportFragmentManager.beginTransaction()
//        manager.add(R.id.mFrameLayout, mHomeFragment)
//        manager.add(R.id.mFrameLayout, mMapFragment)
//        manager.add(R.id.mFrameLayout, mMessageFragment)
//        manager.add(R.id.mFrameLayout, mUserFragment)
//        manager.commit()
//        mStack.add(mHomeFragment)
//        mStack.add(mMapFragment)
//        mStack.add(mMessageFragment)
//        mStack.add(mUserFragment)
//
//    }
//
//    private fun changeFragment(position: Int) {
//        val manager = supportFragmentManager.beginTransaction()
//        for (fragment in mStack) {
//            manager.hide(fragment)
//        }
//
//        manager.show(mStack[position])
//        manager.commit()
//    }
//}