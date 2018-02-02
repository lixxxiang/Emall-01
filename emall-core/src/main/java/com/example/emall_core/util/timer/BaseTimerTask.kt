package com.example.emall_core.util.timer

import java.util.*

/**
 * Created by lixiang on 2018/2/2.
 */
class BaseTimerTask(timerListener: ITimerListener) : TimerTask(){
    var mITimerListener : ITimerListener? = null

    override fun run() {
        if(mITimerListener != null){
            mITimerListener!!.onTimer()
        }
    }
}