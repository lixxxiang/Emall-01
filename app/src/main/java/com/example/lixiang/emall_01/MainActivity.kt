package com.example.lixiang.emall_01

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.emall_core.activities.ProxyActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.ui.launcher.ILauncherListener
import com.example.emall_core.ui.launcher.OnLauncherFinishTag
import com.example.emall_ec.R
import com.example.emall_ec.launcher.LauncherDelegate
import com.example.emall_ec.sign.ISignListener
import com.example.emall_ec.sign.SignUpDelegate
import com.example.emall_ec.main.EcBottomDelegate
import com.githang.statusbar.StatusBarCompat


class MainActivity : ProxyActivity(), ISignListener, ILauncherListener {
    override fun onLauncherFinish(tag: OnLauncherFinishTag) {
        when (tag) {
            OnLauncherFinishTag.SIGNED -> {
                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show()
                startWithPop(EcBottomDelegate())
            }

            OnLauncherFinishTag.NOT_SIGNED -> {
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show()
                supportDelegate.startWithPop(SignUpDelegate())

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: android.support.v7.app.ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

//        StatusBarCompat.setStatusBarColor(this, Color.WHITE)
//        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).path)
    }

    override fun setRootDelegate(): EmallDelegate {
        return LauncherDelegate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
}
