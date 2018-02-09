package com.example.emall_ec.sign

import android.app.Activity
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.orhanobut.logger.Logger.json
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.util.log.EmallLogger
import java.util.*


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {

    private var mISignListener : ISignListener ?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

    override fun initial() {
        val params : WeakHashMap<String, Any> ?= RestClient().PARAMS
        params!!["name"] = "n"
        params["userId"] = 123
        params["avatar"] = "avatar"
        params["address"] = "address"
        params["gender"] = "gender"

        btn_sign_up_submit.setOnClickListener {
            RestClient().builder()
                    .url("http://10.0.2.2:3003/news")
//                    .params("password", edit_sign_up_pwd.text.toString())
//                    .params("email", edit_sign_up_name.text.toString())
                    .params(params)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.json("USER_PROFILE", response)
                            SignHandler().onSignUp(response, mISignListener!!)
                        }
                    })
                    .failure(object : IFailure{
                        override fun onFailure() {

                        }
                    })
                    .error(object : IError{
                        override fun onError(code: Int, msg: String) {

                        }
                    })
                    .build()
//                    .get()
                    .post()
        }
    }
}