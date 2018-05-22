package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.CustomLoadMoreView
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_core.util.view.MyDatePickerDialog
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.search.SearchResultDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_optics1.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by lixiang on 2018/3/20.
 */
class Optics1Delegate : EmallDelegate(), AdapterView.OnItemClickListener {
    private var ssp2: WeakHashMap<String, Any>? = WeakHashMap()
    var screenIsShow = false
    var flag_1_1 = false
    var flag_1_2 = false
    var flag_1_3 = false
    var flag_1_4 = false
    var flag_1_5 = false
    var flag_2_1 = false
    var flag_2_2 = false
    var flag_2_3 = false
    var flag_2_4 = false
    var flag_3_1 = false
    var flag_3_2 = false

    var flag1 = false
    var flag2 = false
    var flag3 = false
    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    var sceneSearch = SceneSearch()
    private var productIdList: MutableList<SceneSearch.DataBean.SearchReturnDtoListBean> = mutableListOf()
    private var pages = 1
    private var pagesAmount = -1
    var mAdapter: SceneClassifyAdapter? = null
    var sceneGlm: GridLayoutManager? = null
    var gatherTimeFlag = false
    var priceFlag = false

    var startTime = String()
    var endTime = String()
    var itemSize = 0

    var resetFlag = false


    override fun setLayout(): Any? {
        return R.layout.delegate_optics1
    }


    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        setSwipeBackEnable(false)
        optics_srl.isRefreshing = true

        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        ssp2!!["scopeGeo"] = geoFormat(sp.getString("GEO", ""))
        EmallLogger.d(geoFormat(sp.getString("GEO", "")))
        ssp2!!["productType"] = ""
        ssp2!!["resolution"] = ""
        ssp2!!["satelliteId"] = ""
        ssp2!!["startTime"] = ""
        ssp2!!["endTime"] = ""
        ssp2!!["cloud"] = ""
        ssp2!!["type"] = "0"
        ssp2!!["pageSize"] = "10"
        initSceneGlm()
        getData(ssp2!!, pages)


        optics_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mAdapter = null
            optics_srl.isRefreshing = true
            itemSize = 0
            Handler().postDelayed({
                getData(ssp2!!, 1)
                optics_srl.isRefreshing = false
            }, 1200)
        })

        optics_gather_time_rl.setOnClickListener {
            optics_screen_rl.visibility = View.INVISIBLE
            optics_srl.isRefreshing = true

            optics_price_tv.setTextColor(Color.parseColor("#9B9B9B"))
            optics_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
            optics_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)

            gatherTimeFlag = if (!gatherTimeFlag) {
                //正序
                ssp2!!["orderBy"] = "centerTimeASC"
                pages = 1
                getData(ssp2!!, pages)
                optics_gather_time_tv.setTextColor(Color.parseColor("#B80017"))
                optics_gather_time_up_iv.setBackgroundResource(R.drawable.ic_up_red)
                optics_gather_time_down_iv.setBackgroundResource(R.drawable.ic_down_gray)

                true
            } else {
                //倒序
                ssp2!!["orderBy"] = "centerTimeDESC"
                pages = 1
                getData(ssp2!!, pages)
                optics_gather_time_tv.setTextColor(Color.parseColor("#B80017"))
                optics_gather_time_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
                optics_gather_time_down_iv.setBackgroundResource(R.drawable.ic_down_red)
                false
            }
        }

        optics_price_rl.setOnClickListener {
            optics_srl.isRefreshing = true

            optics_screen_rl.visibility = View.INVISIBLE
            optics_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
            optics_gather_time_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
            optics_gather_time_down_iv.setBackgroundResource(R.drawable.ic_down_gray)
            priceFlag = if (!priceFlag) {
                ssp2!!["orderBy"] = "priceASC"
                pages = 1
                getData(ssp2!!, pages)
                optics_price_tv.setTextColor(Color.parseColor("#B80017"))
                optics_price_up_iv.setBackgroundResource(R.drawable.ic_up_red)
                optics_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)
                true
            } else {
                ssp2!!["orderBy"] = "priceDESC"
                pages = 1
                getData(ssp2!!, pages)
                optics_price_tv.setTextColor(Color.parseColor("#B80017"))
                optics_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
                optics_price_down_iv.setBackgroundResource(R.drawable.ic_down_red)
                false
            }
        }

        optics_screen_rel.setOnClickListener {

            if (!screenIsShow) {
                optics_screen_rl.visibility = View.VISIBLE
                optics_screen_iv.setBackgroundResource(R.drawable.ic_up_red)
                optics_screen_tv.setTextColor(Color.parseColor("#B80017"))
                screenIsShow = true
            } else {
                optics_screen_rl.visibility = View.INVISIBLE
                optics_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
                optics_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                screenIsShow = false
            }
        }

        optics_btn_1_1.setOnClickListener {
            flag_1_2 = false
            flag_1_3 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_1) {
                optics_btn_1_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_1 = true
                confirmChangeColor()
            } else {
                optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_1 = false
                confirmChangeColor()
            }
        }

        optics_btn_1_2.setOnClickListener {
            flag_1_1 = false
            flag_1_3 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_2) {
                optics_btn_1_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_2 = true
                confirmChangeColor()

            } else {
                optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_2 = false
                confirmChangeColor()

            }
        }

        optics_btn_1_3.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_3) {
                optics_btn_1_3.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_3 = true
                confirmChangeColor()

            } else {
                optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_3 = false
                confirmChangeColor()
            }
        }

        optics_btn_1_4.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_3 = false
            flag_1_5 = false
            if (!flag_1_4) {
                optics_btn_1_4.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))

                flag_1_4 = true
                confirmChangeColor()

            } else {
                optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_4 = false
                confirmChangeColor()

            }
        }

        optics_btn_1_5.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_3 = false
            flag_1_4 = false
            if (!flag_1_5) {
                optics_btn_1_5.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_5 = true
                confirmChangeColor()

            } else {
                optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_5 = false
                confirmChangeColor()

            }
        }

        optics_btn_2_1.setOnClickListener {
            flag_2_2 = false
            flag_2_3 = false
            flag_2_4 = false
            if (!flag_2_1) {
                optics_btn_2_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_2_1.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_1 = true
                confirmChangeColor()

            } else {
                optics_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_1 = false
                confirmChangeColor()

            }
        }

        optics_btn_2_2.setOnClickListener {
            flag_2_1 = false
            flag_2_3 = false
            flag_2_4 = false
            if (!flag_2_2) {
                optics_btn_2_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_2_2.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_2 = true
                confirmChangeColor()

            } else {
                optics_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_2 = false
                confirmChangeColor()

            }
        }

        optics_btn_2_3.setOnClickListener {
            flag_2_1 = false
            flag_2_2 = false
            flag_2_4 = false
            if (!flag_2_3) {
                optics_btn_2_3.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_2_3.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_3 = true
                confirmChangeColor()

            } else {
                optics_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_3 = false
                confirmChangeColor()

            }
        }

        optics_btn_2_4.setOnClickListener {
            flag_2_1 = false
            flag_2_2 = false
            flag_2_3 = false
            if (!flag_2_4) {
                optics_btn_2_4.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                optics_btn_2_4.setTextColor(Color.parseColor("#B4A078"))
                optics_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                optics_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_4 = true
                confirmChangeColor()

            } else {
                optics_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                optics_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_4 = false
                confirmChangeColor()

            }
        }

        optics_btn_3_1.setOnClickListener {
            var dp = MyDatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay)
            dp.datePicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
            dp.show()
            confirmChangeColor()
        }

        optics_btn_3_2.setOnClickListener {
            var dp = DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay)
            dp.show()
            confirmChangeColor()
        }

        optics_btn_confirm.setOnClickListener {
            optics_srl.isRefreshing = true

            if (resetFlag) {
                optics_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                optics_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
                resetFlag = false
            } else {
                optics_screen_tv.setTextColor(Color.parseColor("#B80017"))
                optics_screen_iv.setBackgroundResource(R.drawable.ic_down_red)
            }

            screenIsShow = false

            if (flag_1_1) {
                ssp2!!["resolution"] = "1"
            }
            if (flag_1_2) {
                ssp2!!["resolution"] = "3"
            }
            if (flag_1_3) {
                ssp2!!["resolution"] = "8"
            }
            if (flag_1_4) {
                ssp2!!["resolution"] = "16"
            }
            if (flag_1_5) {
                ssp2!!["resolution"] = ""
                ssp2!!["satelliteId"] = "JL101A"
            }
            if (!flag_1_1 && !flag_1_2 && !flag_1_3 && !flag_1_4 && !flag_1_5) {
                ssp2!!["resolution"] = ""
                ssp2!!["satelliteId"] = ""
            }
            if (flag_2_1) {
                ssp2!!["cloud"] = "10"
            }
            if (flag_2_2) {
                ssp2!!["cloud"] = "20"
            }
            if (flag_2_3) {
                ssp2!!["cloud"] = "30"
            }
            if (flag_2_4) {
                ssp2!!["cloud"] = "40"
            }
            if (!flag_2_1 && !flag_2_2 && !flag_2_3 && !flag_2_4) {
                ssp2!!["cloud"] = ""
            }

            if (flag_3_1) {
                ssp2!!["startTime"] = startTime
            }
            if (flag_3_2) {
                ssp2!!["endTime"] = endTime
            }
            optics_screen_rl.visibility = View.INVISIBLE
            getData(ssp2!!, 1)
        }

        optics_btn_confirm.isClickable = false

        optics_btn_reset.setOnClickListener {
            ssp2!!["productType"] = ""
            ssp2!!["resolution"] = ""
            ssp2!!["satelliteId"] = ""
            ssp2!!["startTime"] = ""
            ssp2!!["endTime"] = ""
            ssp2!!["cloud"] = ""

            flag_1_1 = false
            flag_1_2 = false
            flag_1_3 = false
            flag_1_4 = false
            flag_1_5 = false

            flag_2_1 = false
            flag_2_2 = false
            flag_2_3 = false
            flag_2_4 = false

            flag_3_1 = false
            flag_3_2 = false



            optics_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))

            optics_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))

            optics_btn_3_1.text = resources.getString(R.string.screen_date)
            optics_btn_3_1.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_3_1.setTextColor(Color.parseColor("#4A4A4A"))
            optics_btn_3_2.text = resources.getString(R.string.screen_date)
            optics_btn_3_2.setBackgroundResource(R.drawable.screen_btn_shape)
            optics_btn_3_2.setTextColor(Color.parseColor("#4A4A4A"))

            confirmChangeColor()
            optics_btn_confirm.isClickable = true
            optics_btn_confirm.setBackgroundResource(R.drawable.screen_btn_shape_confirm)
            resetFlag = true
        }
    }

    private fun getData(ssp2: WeakHashMap<String, Any>, p: Int) {
        ssp2["pageNum"] = p
        ssp2["client"] = "android"
        EmallLogger.d(ssp2)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(ssp2)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
//                        EmallLogger.d(sceneSearch.data.count)
                        optics_srl.isRefreshing = false

                        itemSize = 0
                        if (sceneSearch.status != 103) {
                            optics_rv.visibility = View.VISIBLE
                            optics_no_result.visibility = View.GONE
                            productIdList = sceneSearch.data.searchReturnDtoList
                            pagesAmount = sceneSearch.data.pages
                            val data: MutableList<Model>? = mutableListOf()
                            pages = 1
                            val size = sceneSearch.data.searchReturnDtoList.size
                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                                model.price = sceneSearch.data.searchReturnDtoList[i].price
                                model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                                model.productId = sceneSearch.data.searchReturnDtoList[i].productId
                                model.productType = "1"
                                data!!.add(model)
                            }
                            initRecyclerView(data!!, sceneSearch.data.count)
                        } else {
                            optics_rv.visibility = View.GONE
                            optics_no_result.visibility = View.VISIBLE
                            screenIsShow = false
                            optics_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                            optics_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
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

    private fun loadMoreData(ssp2: WeakHashMap<String, Any>, p: Int, data: MutableList<Model>) {
        EmallLogger.d(p)
        ssp2["pageNum"] = p
        ssp2["client"] = "android"

        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(ssp2)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        if (sceneSearch.status != 103) {
                            productIdList = sceneSearch.data.searchReturnDtoList
                            pagesAmount = sceneSearch.data.pages

                            val size = sceneSearch.data.searchReturnDtoList.size
                            for (i in 0 until size) {
                                val model = Model()

                                model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                                model.price = sceneSearch.data.searchReturnDtoList[i].price
                                model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                                model.productId = sceneSearch.data.searchReturnDtoList[i].productId
                                model.productType = "1"
                                data.add(model)
                            }
                            mAdapter!!.notifyDataSetChanged()
                            mAdapter!!.loadMoreComplete()
                            if (optics_srl != null)
                                optics_srl.isEnabled = true
                            if (pages < pagesAmount)
                                pages += 1
//                            initRecyclerView(data!!)
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

    private fun initSceneGlm() {
        sceneGlm = GridLayoutManager(context, 2)
        sceneGlm!!.isSmoothScrollbarEnabled = true
        sceneGlm!!.isAutoMeasureEnabled = true
        optics_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        optics_rv.layoutManager = sceneGlm
        optics_rv.setHasFixedSize(true)
        optics_rv.isNestedScrollingEnabled = false
    }

    private fun initRecyclerView(data: MutableList<Model>, size: Int) {

        mAdapter = SceneClassifyAdapter(R.layout.item_classify_scene, data, sceneGlm)
        mAdapter!!.setOnLoadMoreListener {
            itemSize += 10
            if (size > itemSize) {
                EmallLogger.d("In le me ")
                optics_srl.isEnabled = false
                loadMoreData(ssp2!!, pages, data)
            } else {
            }
        }
        mAdapter!!.setLoadMoreView(CustomLoadMoreView())
        optics_rv.adapter = mAdapter

        if (pages < pagesAmount)
            pages += 1
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", data[position].productId)
            bundle.putString("type", "1")
            delegate!!.arguments = bundle
            getParentDelegate<SearchResultDelegate>().start(delegate)
        }
    }

    fun geoFormat(geo: String): String {
        val prefix = "{\"type\":\"Polygon\",\"coordinates\":[["
        val suffix = "]]}"
        val geoArray = geo.split(" ".toRegex())
        val data = "[" + geoArray[0] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[1] +
                "]"
        return String.format("%s%s%s", prefix, data, suffix)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        optics_btn_3_1.text = days
        startTime = days
        optics_btn_3_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        optics_btn_3_1.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(optics_btn_3_1.text.toString(), optics_btn_3_2.text.toString()) == 1) {
            Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
            optics_btn_confirm.isClickable = false
            optics_btn_confirm.setBackgroundResource(R.drawable.sign_up_btn_shape)
//            flag_3_1 = false
//            confirmChangeColor()
        } else {
            flag_3_1 = true
            confirmChangeColor()

        }
    }

    private val onDateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        optics_btn_3_2.text = days
        endTime = days
        optics_btn_3_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        optics_btn_3_2.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(optics_btn_3_1.text.toString(), optics_btn_3_2.text.toString()) == 1) {
            Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
            optics_btn_confirm.isClickable = false
            optics_btn_confirm.setBackgroundResource(R.drawable.sign_up_btn_shape)
//            flag_3_2 = false
//            confirmChangeColor()
        } else {
            flag_3_2 = true
            confirmChangeColor()

        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun compare_date(DATE1: String, DATE2: String): Int {
        val df = SimpleDateFormat("yyyy-MM-dd")
        try {
            val dt1 = df.parse(DATE1)
            val dt2 = df.parse(DATE2)
            return when {
                dt1.time > dt2.time -> {
                    1
                }
                dt1.time < dt2.time -> {
                    -1
                }
                else -> 0
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return 0
    }

    fun confirmChangeColor() {
        flag1 = flag_1_1 || flag_1_2 || flag_1_3 || flag_1_4 || flag_1_5
        flag2 = flag_2_1 || flag_2_2 || flag_2_3 || flag_2_4
        flag3 = flag_3_1 || flag_3_2
        if (flag1 || flag2 || flag3) {
            optics_btn_confirm.isClickable = true
            optics_btn_confirm.setBackgroundResource(R.drawable.screen_btn_shape_confirm)
        } else {
            optics_btn_confirm.isClickable = false
            optics_btn_confirm.setBackgroundResource(R.drawable.sign_up_btn_shape)
        }
    }

}