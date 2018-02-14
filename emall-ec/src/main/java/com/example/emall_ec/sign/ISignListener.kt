package com.example.emall_ec.sign

/**
 * Created by lixiang on 2018/2/9.
 */
interface ISignListener {

    /**
     * 登录成功回调
     */
    fun onSignInSuccess()

    /**
     * 注册成功回调
     */
    fun onSignUpSuccess()
}