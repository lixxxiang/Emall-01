package com.example.emall_ec.main.search

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.TextView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneSearch
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_classify.*
import kotlinx.android.synthetic.main.delegate_search_poi.*
import android.widget.TextView.OnEditorActionListener
import com.example.emall_ec.main.search.data.CitiesBean


/**
 * Created by lixiang on 2018/3/20.
 */
class SearchPoiDelegate :BottomItemDelegate() {
    var citiesList: MutableList<String>? = mutableListOf()
    var count = 0
    fun create(): SearchPoiDelegate?{
        return SearchPoiDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_search_poi
    }

    override fun initial() {
        search_poi_et.isFocusable = true
        search_poi_et.isFocusableInTouchMode = true
        search_poi_et.requestFocus()
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        search_poi_et.setOnEditorActionListener { v, actionId, event ->
            val url = "http://59.110.161.48:8023/GetPoiByGaode.do?poiName=" + search_poi_et.text
            RestClient().builder()
                    .url(url)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            val citiesInfo = Gson().fromJson(response, CitiesBean::class.java)
                            for (i in 0..citiesInfo.sug.size) {
                                for (j in 0..citiesInfo.sug[i].cites.size){
                                    citiesList!!.add(citiesInfo.sug[i].cites[j].name)
                                }
                            }
                            EmallLogger.d(citiesList!!)
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
            false
        }

    }
}