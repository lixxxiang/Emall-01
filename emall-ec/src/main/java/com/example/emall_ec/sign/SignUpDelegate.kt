package com.example.emall_ec.sign

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_sign_up.*
import com.orhanobut.logger.Logger.json
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.util.log.EmallLogger
import java.util.*


/**
 * Created by lixiang on 2018/2/5.
 */
class SignUpDelegate : EmallDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_sign_up
    }

    override fun initial() {

        var params : WeakHashMap<String, Any> ?= RestClient().PARAMS

        params!!["name"] = "n"
        params["userId"] = 123
        params["avatar"] = "avatar"
        params["address"] = "address"
        params["gender"] = "gender"
//        val params  = mapOf("password" to 24,"name" to "zhangsan","age" to 25)

        btn_sign_up_submit.setOnClickListener {
            RestClient().builder()
                    .url("http://10.0.2.2:3003/news")
//                    .params("password", edit_sign_up_pwd.text.toString())
//                    .params("email", edit_sign_up_name.text.toString())
                    .params(params)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.json("USER_PROFILE", response)
                            SignHandler().onSignUp(response)
                        }
                    })
                    .failure(object : IFailure{
                        override fun onFailure() {
                            EmallLogger.json("USER_PROFILE","thefuck")

                        }
                    })
                    .error(object : IError{
                        override fun onError(code: Int, msg: String) {
                            EmallLogger.json("USER_PROFILE", "error")
                        }
                    })
                    .build()
//                    .get()
                    .post()
        }
    }
}