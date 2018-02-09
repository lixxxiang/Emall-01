package com.example.emall_core.app

/**
 * Created by lixiang on 2018/2/9.
 */
interface IUserChecker {
    //登陆回调
    fun onSignIn()

    //未登录回调
    fun onNotSignIn()
}