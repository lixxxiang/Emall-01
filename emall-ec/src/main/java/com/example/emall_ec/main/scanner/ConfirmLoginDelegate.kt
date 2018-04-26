package com.example.emall_ec.main.scanner

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.scanner.data.ScanCodeLoginBean
import com.example.emall_ec.main.sign.SetPasswordDelegate
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_confirm_login.*
import kotlinx.android.synthetic.main.delegate_scanner.*
import java.util.*

class ConfirmLoginDelegate : EmallDelegate() {

    private var scanCodeLoginParams : WeakHashMap<String, Any> ?= WeakHashMap()
    private var scanCodeLoginBean = ScanCodeLoginBean()
    fun create(): ConfirmLoginDelegate? {
        return ConfirmLoginDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_confirm_login
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        confirm_login_toolbar.title = getString(R.string.confirm_login_title)
        (activity as AppCompatActivity).setSupportActionBar(confirm_login_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        confirm_login_toolbar.setNavigationOnClickListener {
            pop()
        }

        confirm_confirm_btn.setOnClickListener {
            scanCodeLoginParams!!["uuid"] = arguments.getString("UUID")
            scanCodeLoginParams!!["userTelephone"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userTelephone
            EmallLogger.d(scanCodeLoginParams!!)

            RestClient().builder()
                    .url("http://59.110.161.48:8023/scanCodeLogin.do")
                    .params(scanCodeLoginParams!!)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            scanCodeLoginBean = Gson().fromJson(response, ScanCodeLoginBean::class.java)
                            if (scanCodeLoginBean.meta == "success") {
                                Snackbar.make(view!!, getString(R.string.login_success), Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    })
                    .error(object : IError {
                        override fun onError(code: Int, msg: String) {}
                    })
                    .failure(object : IFailure {
                        override fun onFailure() {}
                    })
                    .build()
                    .post()
        }

        confirm_cancel_btn.setOnClickListener {
            pop()
        }
    }
}