package com.example.emall_core.delegates.web.event

import com.example.emall_core.util.log.EmallLogger

/**
 * Created by lixiang on 2018/2/27.
 */

class UndefindEvent : Event() {
    override fun execute(params: String): String? {
        EmallLogger.d("UndefineEvent", params)
        return null
    }
}
