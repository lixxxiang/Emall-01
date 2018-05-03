package com.example.emall_core.delegates

import android.widget.Toast
import com.example.emall_core.R


/**
 * Created by lixiang on 2018/1/25.
 */
abstract class EmallDelegate : PermissionCheckDelegate() {
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0
    fun <T : EmallDelegate> getParentDelegate(): T {
        return parentFragment as T
    }

//    override fun onBackPressedSupport(): Boolean {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            _mActivity.finish()
//        } else {
//            TOUCH_TIME = System.currentTimeMillis()
//            Toast.makeText(_mActivity, "Press again to exit", Toast.LENGTH_SHORT).show()
//        }
//        return true
//
//    }

}