package com.example.emall_core.delegates


/**
 * Created by lixiang on 2018/1/25.
 */
abstract class   EmallDelegate : PermissionCheckDelegate(){

    fun <T : EmallDelegate> getParentDelegate(): T {
        return parentFragment as T
    }

}