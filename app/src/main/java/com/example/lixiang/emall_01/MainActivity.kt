package com.example.lixiang.emall_01

import com.example.emall_core.activities.ProxyActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.launcher.LauncherDelegate
import com.example.emall_ec.launcher.LauncherScrollDelegate

class MainActivity : ProxyActivity() {
    override fun setRootDelegate(): EmallDelegate {
        return LauncherScrollDelegate()
    }
}
