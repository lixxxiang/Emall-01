package com.example.emall_core.net.callback

import android.os.Handler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/**
 * Created by lixiang on 2018/1/26.
 */
class RequestCallbacks(private val REQUEST: IRequest?, private val SUCCESS: ISuccess?,
                       private val FAILURE: IFailure?,
                       private val ERROR: IError?) : Callback<String> {


    var handler  = Handler()
    override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
            if (call.isExecuted) {
                SUCCESS?.onSuccess(response.body().toString())
            }
        } else {
            ERROR?.onError(response.code(), response.message())
        }

//        if (LOADER_STYLE != null) {
//            handler.postDelayed({ EmallLoader().stopLoading() }, 1000)
//        }

    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        FAILURE?.onFailure()
        REQUEST?.onRequestEnd()
    }

}
