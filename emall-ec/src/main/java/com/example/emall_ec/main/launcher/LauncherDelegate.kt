package com.example.emall_ec.main.launcher

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_core.activities.ProxyActivity
import com.example.emall_core.app.IUserChecker
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.timer.BaseTimerTask
import com.example.emall_core.util.timer.ITimerListener
import com.example.emall_ec.R
import java.util.*
import com.example.emall_core.ui.launcher.ILauncherListener
import com.example.emall_core.ui.launcher.OnLauncherFinishTag
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import kotlinx.android.synthetic.main.delegate_launcher.*
import retrofit2.Retrofit


/**
 * Created by lixiang on 2018/2/2.
 */
class LauncherDelegate : EmallDelegate(), ITimerListener {
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    private var mTimer: Timer? = null
    private var mCount = 4
    private var mIlauncherListener: ILauncherListener? = null
    var entryPageBean = EntryPageBean()
    override fun setLayout(): Any? {
        return R.layout.delegate_launcher
    }

    private fun checkIsShowScroll() {
//        if (!EmallPreference().getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.toString())) {
//            start(LauncherScrollDelegate(), ISupportFragment.SINGLETASK)
//            start(LauncherDelegate(), ISupportFragment.SINGLETASK)
//        } else {
        //检查用户是否登录了APP
        com.example.emall_core.app.AccountManager().checkAccount(object : IUserChecker {
            override fun onSignIn() {
                if (mIlauncherListener != null)
                    mIlauncherListener!!.onLauncherFinish(OnLauncherFinishTag.SIGNED)
            }

            override fun onNotSignIn() {
                if (mIlauncherListener != null)
                    mIlauncherListener!!.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED)
            }

        })
//        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ILauncherListener) {
            mIlauncherListener = activity
        }
    }

    private fun initTimer() {
        mTimer = Timer()
        val task: BaseTimerTask? = BaseTimerTask(this)
        mTimer!!.schedule(task, 0, 1000)
    }

    override fun initial() {
        initTimer()
        retrofit = NetUtils.getRetrofit()
        apiService = retrofit!!.create(ApiService::class.java)
        val call = apiService!!.entryPage("android")
        call.enqueue(object : retrofit2.Callback<EntryPageBean> {
            override fun onResponse(call: retrofit2.Call<EntryPageBean>, response: retrofit2.Response<EntryPageBean>) {
                if (response.body() != null) {
                    entryPageBean = response.body()!!
                    if (entryPageBean.message == "success") {
                        Glide.with(context!!)
                                .load(entryPageBean.data.imageUrl)
                                .into(launcher_iv)
                        launcher_bar.visibility = View.VISIBLE
                    }
                } else {
                    EmallLogger.d("error")
                }
            }

            override fun onFailure(call: retrofit2.Call<EntryPageBean>, throwable: Throwable) {}
        })

        launcher_btn.setOnClickListener {
            if (mTimer != null) {
                mTimer!!.cancel()
                mTimer = null
                checkIsShowScroll()
            }
        }

//        tv_launcher_timer.setOnClickListener {
//            if (mTimer != null) {
//                mTimer!!.cancel()
//                mTimer = null
//                checkIsShowScroll()
//            }
//        }

    }

    override fun onTimer() {
        getProxyActivity()!!.runOnUiThread {
            //            tv_launcher_timer.text = MessageFormat.format("跳过\n{0}s", mCount)
            mCount--
            if (mCount < 0) {
                if (mTimer != null) {
                    mTimer!!.cancel()
                    mTimer = null
//                    MainActivity().container!!.setBackgroundColor( Color.parseColor("#B4A078"))

                    checkIsShowScroll()
                }
            }
        }
    }
}


