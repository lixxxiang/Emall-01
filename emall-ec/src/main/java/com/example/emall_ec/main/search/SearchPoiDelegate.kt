package com.example.emall_ec.main.search

import android.view.View
import android.view.WindowManager
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_search_poi.*
import com.example.emall_ec.main.search.adapter.SearchPoiCitiesAdapter
import com.example.emall_ec.main.search.adapter.SearchPoiPoisAdapter
import com.example.emall_ec.main.search.data.CitiesBean
import com.example.emall_ec.main.search.data.PoiBean


/**
 * Created by lixiang on 2018/3/20.
 */
class SearchPoiDelegate : BottomItemDelegate() {
    var citiesList: MutableList<String>? = mutableListOf()
    var countList: MutableList<String>? = mutableListOf()
    var poiList: MutableList<String>? = mutableListOf()
    var poiAddressList: MutableList<String>? = mutableListOf()

    var searchPoiCitiesAdapter: SearchPoiCitiesAdapter? = null
    var searchPoiPoisAdapter: SearchPoiPoisAdapter? = null


    fun create(): SearchPoiDelegate? {
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

        addHeadView()

        search_poi_cities_listview.setOnItemClickListener { adapterView, view, index, l ->
            RestClient().builder()
                    .url(String.format("http://59.110.161.48:8023/GetPoiByGaode.do?poiName=%s&cityName=%s", search_poi_et.text, citiesList!![index - 1]))
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            clearPois()
                            showPois(response)
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

        search_poi_poi_listview.setOnItemClickListener { adapterView, view, i, l ->
            _mActivity.onBackPressed()
        }
        search_poi_et.setOnEditorActionListener { v, actionId, event ->
            RestClient().builder()
                    .url(String.format("http://59.110.161.48:8023/GetPoiByGaode.do?poiName=%s", search_poi_et.text))
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
//                            hideText()
                            val tempCities = Gson().fromJson(response, CitiesBean::class.java)
                            val tempPois = Gson().fromJson(response, PoiBean::class.java)

                            if (tempPois.type == "0" || tempCities.type == "0") {//is cities
                                clearCities()
                                showCities(response)
                            } else if (tempPois.type == "1" || tempCities.type == "1") {//is pois
                                clearPois()
                                showPois(response)
                            } else {//no return
                                clearCities()
                                clearPois()
                                showNoResult()
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
            false
        }
    }

    private fun addHeadView() {
        val head = View.inflate(activity, R.layout.item_head_search_poi_cities, null)
        search_poi_cities_listview.addHeaderView(head)
    }

    fun clearPois() {
        if (poiList!!.size != 0 || poiAddressList!!.size != 0) {
            poiList!!.clear()
            poiAddressList!!.clear()
        }
    }

    fun clearCities() {
        if (citiesList!!.size != 0 || countList!!.size != 0) {
            citiesList!!.clear()
            countList!!.clear()
        }
    }

    fun hideText() {
        search_poi_text1_tv.visibility = View.INVISIBLE
        search_poi_text2_tv.visibility = View.INVISIBLE
    }

    fun showNoResult() {
        if (search_poi_cities_listview.visibility == View.VISIBLE || search_poi_poi_listview.visibility == View.INVISIBLE) {
            search_poi_poi_listview.visibility = View.INVISIBLE
            search_poi_cities_listview.visibility = View.INVISIBLE
        }
        search_poi_no_result_rl.visibility = View.VISIBLE
    }


    fun showCities(response: String) {
        if (search_poi_cities_listview.visibility == View.INVISIBLE) {
            search_poi_poi_listview.visibility = View.INVISIBLE
            search_poi_cities_listview.visibility = View.VISIBLE
        }
        val citiesInfo = Gson().fromJson(response, CitiesBean::class.java)
        for (i in 0 until citiesInfo.sug.size) {
            for (j in 0 until citiesInfo.sug[i].cites.size) {
                citiesList!!.add(citiesInfo.sug[i].cites[j].name)
                countList!!.add(citiesInfo.sug[i].cites[j].num)
            }
        }

        searchPoiCitiesAdapter = SearchPoiCitiesAdapter(citiesList, countList, context)
        search_poi_cities_listview.adapter = searchPoiCitiesAdapter
    }

    fun showPois(response: String) {
        if (search_poi_poi_listview.visibility == View.INVISIBLE) {
            search_poi_cities_listview.visibility = View.INVISIBLE
            search_poi_poi_listview.visibility = View.VISIBLE
        }

        val poiInfo = Gson().fromJson(response, PoiBean::class.java)
        for (i in 0 until poiInfo.gdPois.poiList.size) {
            println(i)
            poiList!!.add(poiInfo.gdPois.poiList[i].name)
            poiAddressList!!.add(String.format("%s %s %s",
                    poiInfo.gdPois.poiList[i].cityname,
                    poiInfo.gdPois.poiList[i].adname,
                    poiInfo.gdPois.poiList[i].address))
        }

        EmallLogger.d(poiList!!.size)

        searchPoiPoisAdapter = SearchPoiPoisAdapter(poiList, poiAddressList, context)
        search_poi_poi_listview.adapter = searchPoiPoisAdapter
    }
}