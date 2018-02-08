package com.example.lixiang.emall_01

import android.app.Application
import com.example.emall.ec.icon.FontEcModule
import com.example.emall_core.app.Emall
import com.example.emall_core.net.interceptors.DebugInterceptor
import com.example.emall_ec.database.DatabaseManager
import com.joanzapata.iconify.fonts.FontAwesomeModule

/**
 * Created by lixiang on 2018/1/22.
 */
class EmallApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Emall().init(this)
                .withIcon(FontAwesomeModule())
                .withIcon(FontEcModule())
                .withApiHost("http://127.0.0.1:8080/")
                .withInterceptor(DebugInterceptor("index", R.raw.test))
                .configure()

        DatabaseManager().getInstance()!!.init(this)

    }
}