package com.example.lixiang.emall_01

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.webkit.WebIconDatabase
import com.baidu.mapapi.SDKInitializer
import com.blankj.utilcode.util.Utils
import com.example.emall_core.app.Emall
import com.example.emall_core.net.interceptors.DebugInterceptor
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.database.DatabaseManager
import com.example.lixiang.emall_01.event.TestEvent
import com.facebook.stetho.Stetho
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.tencent.bugly.Bugly.applicationContext
import com.tencent.bugly.crashreport.CrashReport
import io.vov.vitamio.Vitamio

/**
 * Created by lixiang on 2018/1/22.
 */
class EmallApplication : MultiDexApplication() {

    private var mMyApplication: EmallApplication? = null
    override fun onCreate() {
        super.onCreate()
//        initData();
        EmallLogger.d("IIIINIT")
        SDKInitializer.initialize(applicationContext)
        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).path)
        CrashReport.initCrashReport(getApplicationContext(), "68de5f9c44", false);
        Emall().init(this)
                .withIcon(FontAwesomeModule())
                .withApiHost("http://127.0.0.1:8080/")
                .withInterceptor(DebugInterceptor("index", R.raw.test))
                .withJavascriptInterface("emall")
                .withWebEvent("test", TestEvent())
                .configure()
        initStetho()
        Utils.init(this)
        DatabaseManager().getInstance()!!.init(this)

        Vitamio.isInitialized(this)
    }

    private fun initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}