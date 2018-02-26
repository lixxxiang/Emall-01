package com.example.emall_ec.sign

import android.app.Activity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_set_user_name.*

/**
 * Created by lixiang on 14/02/2018.
 */
class SetUserNameDelegate : EmallDelegate(){

    private var mISignListener : ISignListener ?= null

    override fun setLayout(): Any? {
        return R.layout.delegate_set_user_name
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ISignListener) {
            mISignListener = activity
        }
    }

    override fun initial() {
        btn_set_user_name_delegate_submit.setOnClickListener{
            EmallLogger.d(params)
            RestClient().builder()
                    .url("http://10.0.2.2:3003/news")//EMULATOR
//                    .url("http://192.168.2.162:3003/news")//HOME

//                    .params("password", edit_sign_up_pwd.text.toString())
//                    .params("email", edit_sign_up_name.text.toString())
                    .params(params)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.json("USER_PROFILE", response)
                            SignHandler().onSignUp(response, mISignListener!!)
//                            startWithPop(SetPasswordDelegate())
                        }
                    })
                    .failure(object : IFailure {
                        override fun onFailure() {

                        }
                    })
                    .error(object : IError {
                        override fun onError(code: Int, msg: String) {

                        }
                    })
                    .build()
//                    .get()
                    .post()
        }
    }
}