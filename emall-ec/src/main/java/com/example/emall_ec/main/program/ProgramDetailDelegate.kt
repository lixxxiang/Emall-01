package com.example.emall_ec.main.program

import android.content.Context
import android.content.SharedPreferences
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.program.data.DemandBean
import com.google.gson.Gson
import java.util.*

class ProgramDetailDelegate : EmallDelegate(){
    private var demandParams : WeakHashMap<String, Any> ?= WeakHashMap()
    var demandBean = DemandBean()
    fun create(): ProgramDetailDelegate?{
        return ProgramDetailDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_program_detail
    }

    override fun initial() {
        val sp = activity.getSharedPreferences("PROGRAMMING", Context.MODE_PRIVATE)
        EmallLogger.d(sp.getString("scopeGeo", ""))
        getData(sp)
    }

    private fun getData(sp: SharedPreferences) {
        demandParams!!["productType"] = sp.getString("productType", "")
        demandParams!!["scopeType"] = "2"
        demandParams!!["scopeDetail"] = ""
        demandParams!!["scopeGeo"] = sp.getString("scopeGeo", "")
        demandParams!!["resolution"] = "0.72"
        demandParams!!["userId"] = ""
        demandParams!!["startTime"] = sp.getString("startTime", "")
        demandParams!!["endTime"] = sp.getString("endTime", "")
        demandParams!!["cloud"] = sp.getString("cloud", "")
        demandParams!!["angle"] = sp.getString("angle", "")
        demandParams!!["duration"] = ""
        demandParams!!["area"] = ""
        demandParams!!["pointNum"] = ""
        demandParams!!["center"] = sp.getString("center", "")
        demandParams!!["originalPrice"] = ""
        demandParams!!["salePrice"] = ""
        demandParams!!["finalPrice"] = ""

        RestClient().builder()
                .url("http://59.110.164.214:8025/global/order/create/demand")
                .params(demandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        demandBean = Gson().fromJson(response, DemandBean::class.java)
                        if (demandBean.msg == "成功"){
                            
                        }
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
                .post()
    }

}