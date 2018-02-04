package com.example.emall_ec.launcher

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.timer.BaseTimerTask
import com.example.emall_core.util.timer.ITimerListener
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_launcher.*
import java.text.MessageFormat
import java.util.*
import com.example.emall_core.net.ui.ScrollLauncherTag
import com.example.emall_core.util.storage.EmallPreference
import me.yokeyword.fragmentation.ISupportFragment


/**
 * Created by lixiang on 2018/2/2.
 */
class LauncherDelegate : EmallDelegate(), ITimerListener {

    private var mTimer: Timer? = null
    private var mCount = 5

    override fun setLayout(): Any? {
        return R.layout.delegate_launcher
    }

    private fun checkIsShowScroll() {
        if (!EmallPreference().getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.toString())) {
            start(LauncherScrollDelegate(), ISupportFragment.SINGLETASK)
        } else {
            //检查用户是否登录了APP
        }
    }

    private fun initTimer() {
        mTimer = Timer()
        val task: BaseTimerTask? = BaseTimerTask(this)
        mTimer!!.schedule(task, 0, 1000)
    }

    override fun initial() {
        initTimer()


        tv_launcher_timer.setOnClickListener {
            if (mTimer != null) {
                mTimer!!.cancel()
                mTimer = null
                checkIsShowScroll()
            }
        }

    }

    override fun onTimer() {
        getProxyActivity()!!.runOnUiThread {
            tv_launcher_timer.text = MessageFormat.format("跳过\n{0}s", mCount)
            mCount--
            if (mCount < 0) {
                if (mTimer != null) {
                    mTimer!!.cancel()
                    mTimer = null
                    checkIsShowScroll()
                }
            }
        }
    }
}


