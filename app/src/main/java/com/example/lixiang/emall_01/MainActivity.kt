package com.example.lixiang.emall_01

import android.annotation.SuppressLint
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
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewGroup
import android.widget.TextView
import com.example.lixiang.emall_01.R.layout.activity_main
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ProxyActivity(), ISignListener, ILauncherListener {

    protected var useThemestatusBarColor = false//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected var useStatusBarColor = true//是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置

    @SuppressLint("ResourceType")
    protected fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //根据上面设置是否对状态栏单独设置颜色
//            if (useThemestatusBarColor) {
//                window.statusBarColor = resources.getColor(Color.WHITE)
//            } else {
                window.statusBarColor = Color.TRANSPARENT
//            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
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
        setStatusBar()
        val actionBar: android.support.v7.app.ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }



//        Log.e("TAG", "xmlroot = " + ll.id)
        Log.e("TAG", "android.R.id.content.getChildAt(0) = " + (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0))
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
