package com.example.lixiang.emall_01

import android.app.ActionBar
import android.os.Bundle
import com.example.emall_core.activities.ProxyActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.launcher.LauncherDelegate
import com.example.emall_ec.launcher.LauncherScrollDelegate

class MainActivity : ProxyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar : android.support.v7.app.ActionBar? = supportActionBar
        if (actionBar != null){
            actionBar.hide()
        }
    }
    override fun setRootDelegate(): EmallDelegate {
        return LauncherDelegate()
    }
}
