package com.example.emall_ec.launcher

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.timer.BaseTimerTask
import com.example.emall_core.util.timer.ITimerListener
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_launcher.*
import java.text.MessageFormat
import java.util.*

/**
 * Created by lixiang on 2018/2/2.
 */
class LauncherDelegate : EmallDelegate(), ITimerListener {

    private var mTimer: Timer? = null
    private var mCount = 5

    override fun setLayout(): Any? {
        return R.layout.delegate_launcher
    }

    private fun initTimer(){
        mTimer = Timer()
        val task: BaseTimerTask? = BaseTimerTask(this)
        mTimer!!.schedule(task, 0, 1000 )
    }
    override fun initial() {
       initTimer()
    }

    override fun onTimer() {
        getProxyActivity()!!.runOnUiThread {
            tv_launcher_timer.text = MessageFormat.format("跳过\n{0}s", mCount)
            mCount--
            if (mCount < 0) {
                if (mTimer != null) {
                    mTimer!!.cancel()
                    mTimer = null
                }
            }
        }
    }
}


