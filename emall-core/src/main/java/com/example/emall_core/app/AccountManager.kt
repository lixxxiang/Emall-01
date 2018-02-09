package com.example.emall_core.app

import com.example.emall_core.util.storage.EmallPreference

/**
 * Created by lixiang on 2018/2/9.
 */
class AccountManager {
    enum class SignTag{
        SIGN_TAG
    }

    fun setSignState(state: Boolean){
        EmallPreference().setAppFlag(SignTag.SIGN_TAG.name, state)
    }

    fun isSignIn(): Boolean {
        return EmallPreference().getAppFlag(SignTag.SIGN_TAG.name)
    }

    fun CheckAccount(checker: IUserChecker){
        if (isSignIn()){
            checker.onSignIn()
        }else{
            checker.onNotSignIn()
        }
    }
}