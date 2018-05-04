package com.example.emall_ec.main.order

import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import java.util.*

/**
 * Created by lixiang on 2018/3/1.
 */
class OrderDelegate : BottomItemDelegate() {

    var commoditySubmitDemandParams: WeakHashMap<String, Any>? = WeakHashMap()
    var viewDemandParams: WeakHashMap<String, Any>? = WeakHashMap()

    fun create(): OrderDelegate? {
        return OrderDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order
    }

    override fun initial() {
        val url: String = if (FileUtil.checkEmulator()) {
            "http://10.0.2.2:3033/data"
        } else {
            "http://10.10.90.38:3033/data"
        }

        commoditySubmitDemandParams!!["productId"] = "JL101A_PMS_20170725103441_000020182_102_0025_002_L1_PAN;JL101A_PMS_20170725103441_000020182_102_0029_002_L1_PAN"
        commoditySubmitDemandParams!!["status"] = "1"
        commoditySubmitDemandParams!!["type"] = "1"
        commoditySubmitDemandParams!!["geo"] = "0"

        EmallLogger.d(commoditySubmitDemandParams!!)
        RestClient().builder()
                .url("http://10.10.90.11:8086/global/commoditySubmitDemand")//EMULATOR
                .params(commoditySubmitDemandParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
//                        val bannerSize = VideoDetailDataConverter().setJsonData(response).convert()
//                        EmallLogger.d(bannerSize[0].getField(VideoDetailFields.DURATION))
//                        DATA = bannerSize
                        viewDemandParams!!["type"] = "1"
                        viewDemandParams!!["demandId"] = "180301143300028664"
                        RestClient().builder()
                                .url("http://10.10.90.11:8086/global/viewDemand")//EMULATOR
                                .params(viewDemandParams!!)
                                .success(object : ISuccess {
                                    override fun onSuccess(response: String) {
                                        EmallLogger.d(response)
//                        val bannerSize = VideoDetailDataConverter().setJsonData(response).convert()
//                        EmallLogger.d(bannerSize[0].getField(VideoDetailFields.DURATION))
//                        DATA = bannerSize
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