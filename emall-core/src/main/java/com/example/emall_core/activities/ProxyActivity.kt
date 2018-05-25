package com.example.emall_core.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.ContentFrameLayout
import android.view.KeyEvent
import com.example.emall_core.R
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.view.StatusBarUtil
import me.yokeyword.fragmentation.SupportActivity
import android.view.KeyEvent.KEYCODE_BACK



/**
 * Created by lixiang on 2018/1/25.
 */

abstract class ProxyActivity : SupportActivity() {
    /**
     * 主fragment
     */


    abstract fun setRootDelegate(): EmallDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



//
}
