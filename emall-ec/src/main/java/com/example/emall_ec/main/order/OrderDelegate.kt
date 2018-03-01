package com.example.emall_ec.main.order

import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.detail.VideoDetailDataConverter
import com.example.emall_ec.main.index.VideoDetailFields
import kotlinx.android.synthetic.main.delegate_video_goods_detail.*
import java.util.*

/**
 * Created by lixiang on 2018/3/1.
 */
class OrderDelegate : BottomItemDelegate(){

    var params :WeakHashMap<String, Any> ?= WeakHashMap()
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

        params!!["productId"]= "JL101A_PMS_20170725103441_000020182_102_0025_002_L1_PAN;JL101A_PMS_20170725103441_000020182_102_0029_002_L1_PAN"
        params!!["status"] = "1"
        params!!["type"] = "1"
        params!!["geo"] = "0"

        EmallLogger.d(params!!)
        RestClient().builder()
                .url("http://10.10.90.11:8086/global/commoditySubmitDemand")//EMULATOR
                .params(params!!)
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

}