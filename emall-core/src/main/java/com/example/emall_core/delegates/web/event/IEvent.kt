package com.example.emall_core.delegates.web.event

/**
 * Created by lixiang on 2018/2/27.
 */
interface IEvent {
    fun execute(params: String): String?
}