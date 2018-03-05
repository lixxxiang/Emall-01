package com.example.emall_ec.main.order.state

import android.support.v4.app.Fragment
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

/**
 * Created by lixiang on 2018/3/5.
 */
class CheckPendingDelegate: EmallDelegate(){
    override fun setLayout(): Any? {
        return R.layout.delegate_check_pending
    }

    override fun initial() {
    }

}