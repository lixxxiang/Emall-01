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
import android.widget.Toast
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.CustomLoadMoreView
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.search.SearchResultDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_noctilucence.*
import kotlinx.android.synthetic.main.delegate_optics1.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixiang on 2018/3/20.
 */
class NoctilucenceDelegate : EmallDelegate() {
    private var ssp: WeakHashMap<String, Any>? = WeakHashMap()
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
    var sceneGlm: GridLayoutManager? = null
    var mAdapter: SceneClassifyAdapter? = null
    var gatherTimeFlag = false
    var priceFlag = false
    var startTime = String()
    var endTime = String()

    var resetFlag = false

    override fun setLayout(): Any? {
        return R.layout.delegate_noctilucence
    }

    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        setSwipeBackEnable(false)
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        ssp!!["scopeGeo"] = Optics1Delegate().geoFormat(sp.getString("GEO", ""))
        ssp!!["productType"] = ""
        ssp!!["resolution"] = ""
        ssp!!["satelliteId"] = ""
        ssp!!["startTime"] = ""
        ssp!!["endTime"] = ""
        ssp!!["cloud"] = ""
        ssp!!["type"] = "2"
        ssp!!["pageSize"] = "10"
        initSceneGlm()

        getData(ssp!!, pages)

        noctilucence_srl.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mAdapter = null
            noctilucence_srl.isRefreshing = true
            Handler().postDelayed({
                getData(ssp!!, 1)
                noctilucence_srl.isRefreshing = false
            }, 1200)
        })

        noctilucence_gather_time_rl.setOnClickListener {

            noctilucence_price_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
            noctilucence_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)

            noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)

            gatherTimeFlag = if (!gatherTimeFlag) {
                noctilucence_gather_time_tv.setTextColor(Color.parseColor("#B80017"))
                noctilucence_gather_time_iv.setBackgroundResource(R.drawable.ic_up_red)
                true
            } else {
                noctilucence_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
                noctilucence_gather_time_iv.setBackgroundResource(R.drawable.ic_down_gray)
                false
            }
        }

        noctilucence_price_rl.setOnClickListener {
            noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)

            noctilucence_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_gather_time_iv.setBackgroundResource(R.drawable.ic_down_gray)


            priceFlag = if (!priceFlag) {
                noctilucence_price_tv.setTextColor(Color.parseColor("#B80017"))
                noctilucence_price_up_iv.setBackgroundResource(R.drawable.ic_up_red)
                noctilucence_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)
                true
            } else {
                noctilucence_price_tv.setTextColor(Color.parseColor("#B80017"))
                noctilucence_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
                noctilucence_price_down_iv.setBackgroundResource(R.drawable.ic_down_red)
                false
            }
        }

        noctilucence_screen_rel.setOnClickListener {
            noctilucence_price_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_price_up_iv.setBackgroundResource(R.drawable.ic_up_gray)
            noctilucence_price_down_iv.setBackgroundResource(R.drawable.ic_down_gray)

            noctilucence_gather_time_tv.setTextColor(Color.parseColor("#9B9B9B"))
            noctilucence_gather_time_iv.setBackgroundResource(R.drawable.ic_down_gray)

            if (!screenIsShow) {
                noctilucence_screen_rl.visibility = View.VISIBLE
                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_up_red)
                noctilucence_screen_tv.setTextColor(Color.parseColor("#B80017"))
                screenIsShow = true
            } else {
                noctilucence_screen_rl.visibility = View.INVISIBLE
                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
                noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                screenIsShow = false
            }
        }


//        noctilucence_screen_tv.setOnClickListener {
//            if (!screenIsShow) {
//                noctilucence_screen_rl.visibility = View.VISIBLE
//                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_up_red)
//                noctilucence_screen_tv.setTextColor(Color.parseColor("#B80017"))
//                screenIsShow = true
//            } else {
//                noctilucence_screen_rl.visibility = View.INVISIBLE
//                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
//                noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
//                screenIsShow = false
//            }
//        }

        noctilucence_btn_1_1.setOnClickListener {
            flag_1_2 = false
            flag_1_3 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_1) {
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_1 = true
                confirmChangeColor()
            } else {
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_1 = false
                confirmChangeColor()
            }
        }

        noctilucence_btn_1_2.setOnClickListener {
            flag_1_1 = false
            flag_1_3 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_2) {
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_2 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_2 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_1_3.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_4 = false
            flag_1_5 = false
            if (!flag_1_3) {
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_3 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_3 = false
                confirmChangeColor()
            }
        }

        noctilucence_btn_1_4.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_3 = false
            flag_1_5 = false
            if (!flag_1_4) {
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))

                flag_1_4 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_4 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_1_5.setOnClickListener {
            flag_1_1 = false
            flag_1_2 = false
            flag_1_3 = false
            flag_1_4 = false
            if (!flag_1_5) {
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_5 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
                flag_1_5 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_2_1.setOnClickListener {
            flag_2_2 = false
            flag_2_3 = false
            flag_2_4 = false
            if (!flag_2_1) {
                noctilucence_btn_2_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_2_1.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_1 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_1 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_2_2.setOnClickListener {
            flag_2_1 = false
            flag_2_3 = false
            flag_2_4 = false
            if (!flag_2_2) {
                noctilucence_btn_2_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_2_2.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_2 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_2 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_2_3.setOnClickListener {
            flag_2_1 = false
            flag_2_2 = false
            flag_2_4 = false
            if (!flag_2_3) {
                noctilucence_btn_2_3.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_2_3.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_3 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_3 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_2_4.setOnClickListener {
            flag_2_1 = false
            flag_2_2 = false
            flag_2_3 = false
            if (!flag_2_4) {
                noctilucence_btn_2_4.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
                noctilucence_btn_2_4.setTextColor(Color.parseColor("#B4A078"))
                noctilucence_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
                noctilucence_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_4 = true
                confirmChangeColor()

            } else {
                noctilucence_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
                noctilucence_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
                flag_2_4 = false
                confirmChangeColor()

            }
        }

        noctilucence_btn_3_1.setOnClickListener {
            DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            confirmChangeColor()
        }

        noctilucence_btn_3_2.setOnClickListener {
            DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()
            confirmChangeColor()
        }

        noctilucence_btn_confirm.setOnClickListener {
            if (resetFlag){
                noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
                resetFlag = false
            }else{
                noctilucence_screen_tv.setTextColor(Color.parseColor("#B80017"))
                noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_red)
            }
            screenIsShow = false

            if (flag_1_1) {
                ssp!!["resolution"] = "1"
            }
            if (flag_1_2) {
                ssp!!["resolution"] = "3"
            }
            if (flag_1_3) {
                ssp!!["resolution"] = "8"
            }
            if (flag_1_4) {
                ssp!!["resolution"] = "16"
            }
            if (flag_1_5) {
                ssp!!["satelliteId"] = "JL101A"
            }
            if (!flag_1_1 && !flag_1_2 && !flag_1_3 && !flag_1_4 && !flag_1_5) {
                ssp!!["resolution"] = ""
                ssp!!["satelliteId"] = ""
            }
            if (flag_2_1) {
                ssp!!["cloud"] = "10"
            }
            if (flag_2_2) {
                ssp!!["cloud"] = "20"
            }
            if (flag_2_3) {
                ssp!!["cloud"] = "30"
            }
            if (flag_2_4) {
                ssp!!["cloud"] = "40"
            }
            if (!flag_2_1 && !flag_2_2 && !flag_2_3 && !flag_2_4) {
                ssp!!["resolution"] = ""
                ssp!!["satelliteId"] = ""
            }

            if (flag_3_1) {
                ssp!!["startTime"] = startTime
            }
            if (flag_3_2) {
                ssp!!["endTime"] = endTime
            }
            noctilucence_screen_rl.visibility = View.INVISIBLE
            getData(ssp!!, 1)
        }

        noctilucence_btn_confirm.isClickable = false

        noctilucence_btn_reset.setOnClickListener {
            ssp!!["productType"] = ""
            ssp!!["resolution"] = ""
            ssp!!["satelliteId"] = ""
            ssp!!["startTime"] = ""
            ssp!!["endTime"] = ""
            ssp!!["cloud"] = ""

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


            noctilucence_btn_1_5.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_1_5.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_1_1.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_1_1.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_1_2.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_1_2.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_1_3.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_1_3.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_1_4.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_1_4.setTextColor(Color.parseColor("#4A4A4A"))

            noctilucence_btn_2_4.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_2_4.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_2_1.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_2_1.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_2_2.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_2_2.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_2_3.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_2_3.setTextColor(Color.parseColor("#4A4A4A"))

            noctilucence_btn_3_1.text = resources.getString(R.string.screen_date)
            noctilucence_btn_3_1.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_3_1.setTextColor(Color.parseColor("#4A4A4A"))
            noctilucence_btn_3_2.text = resources.getString(R.string.screen_date)
            noctilucence_btn_3_2.setBackgroundResource(R.drawable.screen_btn_shape)
            noctilucence_btn_3_2.setTextColor(Color.parseColor("#4A4A4A"))

            confirmChangeColor()
            noctilucence_btn_confirm.isClickable = true
            noctilucence_btn_confirm.setBackgroundResource(R.drawable.screen_btn_shape_confirm)
            resetFlag = true
        }
    }

    private fun getData(ssp: WeakHashMap<String, Any>, p: Int) {
        ssp["pageNum"] = p
        println(ssp)
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(ssp)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        println(response)
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        if (sceneSearch.status != 103) {
                            noctilucence_no_result.visibility = View.GONE
                            noctilucence_rv.visibility = View.VISIBLE
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
                            initRecyclerView(data!!)
                        } else {
                            noctilucence_rv.visibility = View.GONE
                            noctilucence_no_result.visibility = View.VISIBLE
                            noctilucence_screen_tv.setTextColor(Color.parseColor("#9B9B9B"))
                            noctilucence_screen_iv.setBackgroundResource(R.drawable.ic_down_gray)
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
        noctilucence_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        noctilucence_rv.layoutManager = sceneGlm
        noctilucence_rv.setHasFixedSize(true)
        noctilucence_rv.isNestedScrollingEnabled = false
    }

    private fun initRecyclerView(data: MutableList<Model>) {

        mAdapter = SceneClassifyAdapter(R.layout.item_classify_scene, data, sceneGlm)
        mAdapter!!.setOnLoadMoreListener {
            //            loadMoreData(ssp!!, pages, data)
        }
        mAdapter!!.setLoadMoreView(CustomLoadMoreView())

        noctilucence_rv.adapter = mAdapter
        if (pages < pagesAmount)
            pages += 1
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val delegate = GoodsDetailDelegate().create()
            val bundle: Bundle? = Bundle()
            bundle!!.putString("productId", data[position].productId)
            bundle.putString("type", "5")
            delegate!!.arguments = bundle
            getParentDelegate<SearchResultDelegate>().start(delegate)
        }
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
        EmallLogger.d(days)
        startTime = days
        noctilucence_btn_3_1.text = days
        noctilucence_btn_3_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        noctilucence_btn_3_1.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(noctilucence_btn_3_1.text.toString(), noctilucence_btn_3_2.text.toString()) == 1) {
            Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
            flag_3_1 = false
            confirmChangeColor()
        } else {
            flag_3_1 = true
        }
        confirmChangeColor()
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
        endTime = days
        noctilucence_btn_3_2.text = days
        noctilucence_btn_3_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        noctilucence_btn_3_2.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(noctilucence_btn_3_1.text.toString(), noctilucence_btn_3_2.text.toString()) == 1) {
            Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
            flag_3_2 = false
            confirmChangeColor()
        } else {
            flag_3_2 = true
        }
        confirmChangeColor()

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
        EmallLogger.d(String.format("%s %s %s %s %s", flag_1_1, flag_1_2, flag_1_3, flag_1_4, flag_1_5))
        EmallLogger.d(String.format("%s %s %s %s", flag_2_1, flag_2_2, flag_2_3, flag_2_4))
        EmallLogger.d(String.format("%s", flag_3_1))

        flag1 = flag_1_1 || flag_1_2 || flag_1_3 || flag_1_4 || flag_1_5
        flag2 = flag_2_1 || flag_2_2 || flag_2_3 || flag_2_4
        flag3 = flag_3_1

        EmallLogger.d(String.format("%s %s %s", flag1, flag2, flag3))

        if (flag1 || flag2 || flag3) {
            noctilucence_btn_confirm.isClickable = true
            noctilucence_btn_confirm.setBackgroundResource(R.drawable.screen_btn_shape_confirm)
        } else {
            noctilucence_btn_confirm.isClickable = false
            noctilucence_btn_confirm.setBackgroundResource(R.drawable.sign_up_btn_shape)
        }
    }
}