package com.example.emall_ec.launcher

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.timer.BaseTimerTask
import com.example.emall_core.util.timer.ITimerListener
import com.example.emall_ec.R
import java.util.*

/**
 * Created by lixiang on 2018/2/2.
 */
class LauncherDelegate : EmallDelegate(), ITimerListener {

    var mTimer: Timer? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_launcher
    }

    fun initTimer(){

    }
    override fun initial() {
        mTimer = Timer()
        val task:BaseTimerTask? = BaseTimerTask(this)
        mTimer!!.schedule(task, 0, 1000 )
    }

}
