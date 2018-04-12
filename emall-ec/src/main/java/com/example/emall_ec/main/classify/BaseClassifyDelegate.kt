package com.example.emall_ec.main.classify

import android.os.Bundle
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.classify.data.fuckOthers.ApiService
import com.example.emall_ec.main.classify.data.fuckOthers.NetUtils
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.classify.data.VideoSearch
import com.example.emall_ec.main.program.ProgramDelegate
import kotlinx.android.synthetic.main.delegate_base_classify.*
import retrofit2.Retrofit
import java.util.*

/**
 * Created by lixiang on 2018/3/12.
 */
class BaseClassifyDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null
    var sceneSearch = SceneSearch()
    var videoSearch = VideoSearch()

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
            val call = apiService!!.sceneSearch("{\"type\":\"Polygon\",\"coordinates\":[[[2.288164,48.871997],[2.466378,48.894223],[2.487259,48.848535],[2.309833,48.826008],[2.288164,48.871997]]]}",
                    "","",
                    "","2015-04-30",
                    "2017-12-01","",
                    "0","10","1")
            call.enqueue(object : retrofit2.Callback<SceneSearch> {

                override fun onResponse(call: retrofit2.Call<SceneSearch>, response: retrofit2.Response<SceneSearch>) {
                    if (response.body() != null) {
                        EmallLogger.d(response.body()!!.data.searchReturnDtoList[0].thumbnailUrl)
                        sceneSearch = response.body()!!
                        bundle!!.putString("type","1")
                        bundle.putSerializable("sceneData", sceneSearch)
                        delegate.arguments = bundle
                        (DELEGATE as EcBottomDelegate).start(delegate)
                    } else {
                        EmallLogger.d("errpr")
                    }
                }

                override fun onFailure(call: retrofit2.Call<SceneSearch>, throwable: Throwable) {}
            })
        }

        base_classify_noctilucence_siv.setOnClickListener {

        }

        base_classify_program_siv.setOnClickListener {

            (DELEGATE as EcBottomDelegate).start(ProgramDelegate().create()!!)
        }

        base_classify_video_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()

            retrofit = NetUtils.getRetrofit()
            apiService = retrofit!!.create(ApiService::class.java)
            val call = apiService!!.videoSearch("{\"type\":\"Polygon\",\"coordinates\":[[[35.457584,33.948135],[35.594112,33.92194],[35.586525,33.867798],[35.449474,33.894363],[35.457584,33.948135]]]}",
                    "0")
            call.enqueue(object : retrofit2.Callback<VideoSearch> {

                override fun onResponse(call: retrofit2.Call<VideoSearch>, response: retrofit2.Response<VideoSearch>) {
                    if (response.body() != null) {
                        EmallLogger.d(response.body()!!.data[0].detailPath)
                        videoSearch = response.body()!!
                        bundle!!.putString("type","0")
                        bundle.putSerializable("videoData", videoSearch)
                        delegate.arguments = bundle
                        (DELEGATE as EcBottomDelegate).start(delegate)
                    } else {
                        EmallLogger.d("errpr")
                    }
                }

                override fun onFailure(call: retrofit2.Call<VideoSearch>, throwable: Throwable) {}
            })
        }

    }
}