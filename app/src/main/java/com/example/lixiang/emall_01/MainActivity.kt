package com.example.lixiang.emall_01

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.ContentFrameLayout
import android.view.Menu
import android.widget.Toast
import com.example.emall_core.activities.ProxyActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.ui.launcher.ILauncherListener
import com.example.emall_core.ui.launcher.OnLauncherFinishTag
import com.example.emall_ec.R
import com.example.emall_ec.main.launcher.LauncherDelegate
import com.example.emall_ec.main.sign.ISignListener
import com.example.emall_ec.main.EcBottomDelegate
import com.blankj.utilcode.util.AppUtils
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.StatusBarUtil
import com.facebook.drawee.backends.pipeline.Fresco


class MainActivity : ProxyActivity(), ISignListener, ILauncherListener {

    var iid = String()
    fun getIId(): String {
        return iid
    }

    fun setIId() {
        this.iid = iid
    }

    var container: ContentFrameLayout? = null


    fun initContainer(savedInstanceState: Bundle?) {
        container = ContentFrameLayout(this)

        container!!.id = com.example.emall_core.R.id.delegate_container
        container!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
        setContentView(container)
//        StatusBarUtil.setLightMode(this)
//        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)

        if (savedInstanceState == null) {
            loadRootFragment(com.example.emall_core.R.id.delegate_container, setRootDelegate())
        }
    }

    override fun onLauncherFinish(tag: OnLauncherFinishTag) {
        when (tag) {
            OnLauncherFinishTag.SIGNED -> {
                container!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
                startWithPop(EcBottomDelegate())
//                startWithPop(HomeDelegate())

            }

            OnLauncherFinishTag.NOT_SIGNED -> {
                container!!.setBackgroundColor(Color.parseColor("#FFFFFF"))

                startWithPop(EcBottomDelegate())
//                startWithPop(HomeDelegate())

            }

            else -> {
            }
        }
    }

    override fun onSignInSuccess() {
    }

    override fun onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)

        val actionBar: android.support.v7.app.ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        Fresco.initialize(this)
        if (Build.VERSION.SDK_INT < KITKAT) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("当前系统版本过低，无法使用本软件，请升级系统后使用。")
            builder.setPositiveButton("确定") { dialog, _ ->
                AppUtils.exitApp()
            }
            builder.create().show()
        }
    }

    override fun setRootDelegate(): EmallDelegate {
        val sp = getSharedPreferences("TO_VIDEO", Context.MODE_PRIVATE)
        if (sp.getString("to_video", "") == "true") {
            sp.edit().clear().commit()
            return EcBottomDelegate()
        } else{
            sp.edit().clear().commit()
            return LauncherDelegate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
}
