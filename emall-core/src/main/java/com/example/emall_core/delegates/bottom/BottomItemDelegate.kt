package com.example.emall_core.delegates.bottom

import android.view.KeyEvent
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import android.widget.Toast
import com.example.emall_core.R
import com.example.emall_core.app.Emall
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 15/02/2018.
 */
abstract class BottomItemDelegate : EmallDelegate(), View.OnKeyListener {
    var mExitTime: Long = 0
    val EXIT_TIME: Int = 2000

    override fun onResume() {
        super.onResume()
        val rootView: View? = view
        if (rootView != null) {
            rootView.isFocusableInTouchMode = true
            rootView.requestFocus()
            rootView.setOnKeyListener(this)
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1 == KeyEvent.KEYCODE_BACK && p2!!.action == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - mExitTime) > mExitTime) {
//                mExitTime = System.currentTimeMillis()
//            } else {
//                _mActivity.finish()
//                if (mExitTime.toInt() != 0) {
//                    mExitTime = 0
//                }
//            }
            _mActivity.onBackPressed()
        }
//        EmallLogger.d("fffff")

        return true
    }
}