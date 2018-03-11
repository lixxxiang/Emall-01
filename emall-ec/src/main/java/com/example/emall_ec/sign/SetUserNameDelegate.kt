package com.example.emall_ec.sign

import android.app.Activity
import android.graphics.Typeface
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator.params
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_set_user_name.*
import kotlinx.android.synthetic.main.delegate_sign_up.*

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
        set_nickname_title_tv.typeface = Typeface.createFromAsset(activity.assets, "fonts/pingfang.ttf")
        set_nickname_close.typeface = Typeface.createFromAsset(activity.assets, "iconfont/close.ttf")
        btn_set_nickname_submit.setOnClickListener{
            EmallLogger.d(params)
            val url: String = if (FileUtil.checkEmulator()) {
                "http://10.0.2.2:3003/news"
            } else {
//                "http://10.10.90.38:3003/news"
                "http://192.168.1.36:3003/news"

            }

            var fakeResponce = "{\n" +
                    "avatar: \"avatar\",\n" +
                    "gender: \"gender\",\n" +
                    "userId: \"123\",\n" +
                    "name: \"n\",\n" +
                    "address: \"address\",\n" +
                    "id: 44\n" +
                    "}"
            SignHandler().onSignUp(fakeResponce, mISignListener!!)
//                            startWithPop(SetPasswordDelegate())
            startWithPop(SignInByTelDelegate())

//            RestClient().builder()
//                    .url(url)//EMULATOR
////                    .url("http://192.168.2.162:3003/news")//HOME
////                    .params("password", edit_sign_up_pwd.text.toString())
////                    .params("email", edit_sign_up_name.text.toString())
//                    .params(params)
//                    .success(object : ISuccess {
//                        override fun onSuccess(response: String) {
//                            EmallLogger.json("USER_PROFILE", response)
//                            SignHandler().onSignUp(response, mISignListener!!)
////                            startWithPop(SetPasswordDelegate())
//                            startWithPop(SignInByTelDelegate())
//                        }
//                    })
//                    .failure(object : IFailure {
//                        override fun onFailure() {
//                            EmallLogger.d("failure")
//                        }
//                    })
//                    .error(object : IError {
//                        override fun onError(code: Int, msg: String) {
//                            EmallLogger.d("error")
//                        }
//                    })
//                    .build()
////                    .get()
//                    .post()
        }
    }
}