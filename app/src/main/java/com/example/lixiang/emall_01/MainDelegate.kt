package com.example.lixiang.emall_01

import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.lixiang.emall_01.R
import retrofit2.Response.success


/**
 * Created by lixiang on 2018/1/25.
 */
class MainDelegate : EmallDelegate() {
    override fun initial() {
        testHttp()
    }

    override fun setLayout(): Any {
        return R.layout.delegate_main
    }

    fun testHttp() {
        RestClient()
                .builder()
//                .params("","")
//                .url("http://news.baidu.com")
                .url("http://127.0.0.1:8080/index")
                .success(
                        object : ISuccess {
                            override fun onSuccess(response: String) {
                                println("response-->" + response)
                                Toast.makeText(activity, response, Toast.LENGTH_LONG).show()
                            }
                        })
                .failure(
                        object : IFailure {
                            override fun onFailure() {
                                println("response-->MMMM")
                            }

                        })
//                .loader(context, LoaderStyle.BallClipRotateIndicator)
                .progressbar(context)
                .build()
                .get()


    }
}