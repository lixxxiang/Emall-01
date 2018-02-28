package com.example.emall_ec.main.classify

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import kotlinx.android.synthetic.main.delegate_classify.*

/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

    var DELEGATE : EmallDelegate ?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
        test_classify_btn.setOnClickListener {
            val url: String = if (FileUtil.checkEmulator()) {
                "http://10.0.2.2:3033/data"
            } else {
                "http://10.10.90.38:3033/data"
            }

            RestClient().builder()
                    .url(url)//EMULATOR
                    .params(RestCreator.params)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            EmallLogger.json("USER_PROFILE", response)
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
                    .get()
            val delegate: GoodsDetailDelegate = GoodsDetailDelegate().create()!!
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }
}