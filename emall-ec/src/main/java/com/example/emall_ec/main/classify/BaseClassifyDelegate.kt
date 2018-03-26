package com.example.emall_ec.main.classify

import android.os.Bundle
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneDetail
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.order.OrderListDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_base_classify.*
import retrofit2.Retrofit
import java.util.*

/**
 * Created by lixiang on 2018/3/12.
 */
class BaseClassifyDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    var ssp: WeakHashMap<String, Any>? = WeakHashMap()
    internal var retrofit: Retrofit? = null
    internal var apiService: ApiService? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_base_classify
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
        base_classify_optics_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()

            retrofit = NetUtils.getRetrofit()
            apiService = retrofit!!.create(ApiService::class.java)
            val call = apiService!!.sceneSearch("{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}","","",
                    "","2015-04-30","2017-12-01","",
                    "0","10","1")
            call.enqueue(object : retrofit2.Callback<SceneSearch> {

                override fun onResponse(call: retrofit2.Call<SceneSearch>, response: retrofit2.Response<SceneSearch>) {
                    if (response.body() != null) {
                        EmallLogger.d(response.message())
                    } else {
                        EmallLogger.d("errpr")
                    }
                }

                override fun onFailure(call: retrofit2.Call<SceneSearch>, throwable: Throwable) {}
            })
//            ssp!!["scopeGeo"] = "{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}"
//            ssp!!["productType"] = ""
//            ssp!!["resolution"] = ""
//            ssp!!["satelliteId"] = ""
//            ssp!!["startTime"] = "2018-01-01"
//            ssp!!["endTime"] = "2018-03-25"
//            ssp!!["cloud"] = ""
//            ssp!!["type"] = "0"
//            ssp!!["pageSize"] = "10"
//            ssp!!["pageNum"] = "1"
//
//            RestClient().builder()
//                    .url("http://59.110.164.214:8024/global/mobile/sceneSearch")//EMULATOR
//                    .params(ssp!!)
//                    .success(object : ISuccess {
//                        override fun onSuccess(response: String) {
//                            sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
//                            bundle!!.putSerializable("data", sceneSearch)
//                            delegate.arguments = bundle
//                            (DELEGATE as EcBottomDelegate).start(delegate)
//                        }
//                    })
//                    .failure(object : IFailure {
//                        override fun onFailure() {
//
//                        }
//                    })
//                    .error(object : IError {
//                        override fun onError(code: Int, msg: String) {
//
//                        }
//                    })
//                    .build()
//                    .post()
        }
    }
}