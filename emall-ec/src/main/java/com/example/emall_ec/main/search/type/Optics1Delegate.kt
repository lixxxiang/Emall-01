package com.example.emall_ec.main.search.type

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.blankj.utilcode.util.TimeUtils
import com.example.emall_core.app.Emall
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_core.util.view.GridSpacingItemDecoration
import com.example.emall_ec.R
import com.example.emall_ec.main.classify.data.Model
import com.example.emall_ec.main.classify.data.SceneClassifyAdapter
import com.example.emall_ec.main.classify.data.SceneSearch
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.net.CommonUrls
import com.example.emall_ec.main.search.SearchResultDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_optics1.*
import kotlinx.android.synthetic.main.delegate_optics1.view.*
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
    var flag1 = false
    var flag2 = false
    var flag3 = false
    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    var sceneSearch = SceneSearch()
    private var data: MutableList<Model>? = mutableListOf()
    private var productIdList: MutableList<SceneSearch.DataBean.SearchReturnDtoListBean> = mutableListOf()

    override fun setLayout(): Any? {
        return R.layout.delegate_optics1
    }



    @SuppressLint("SimpleDateFormat")
    override fun initial() {
        val sp = activity.getSharedPreferences("GEO_INFO", Context.MODE_PRIVATE)
        ssp2!!["scopeGeo"] = geoFormat(sp.getString("GEO", ""))
        EmallLogger.d(geoFormat(sp.getString("GEO", "")))
        ssp2!!["productType"] = ""
        ssp2!!["resolution"] = ""
        ssp2!!["satelliteId"] = ""
        ssp2!!["startTime"] = "2015-04-30"
        ssp2!!["endTime"] = SimpleDateFormat("yyyy-MM-dd").format(Date())
        ssp2!!["cloud"] = ""
        ssp2!!["type"] = ""
        ssp2!!["pageSize"] = "10"
        ssp2!!["pageNum"] = "1"

        getData(ssp2!!)
//        initRecyclerView(context, CommonUrls().sceneSearch(context, ssp2, optics_rv, "OPTICS")!!, optics_rv)

        optics_screen_tv.setOnClickListener {
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
            DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            confirmChangeColor()
        }

        optics_btn_3_2.setOnClickListener {
            DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()
            confirmChangeColor()
        }

        optics_btn_confirm.setOnClickListener {

        }

        optics_btn_confirm.isClickable = false
    }

    private fun getData(ssp2: WeakHashMap<String, Any>) {
        RestClient().builder()
                .url("http://59.110.164.214:8024/global/mobile/sceneSearch")
                .params(ssp2!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        sceneSearch = Gson().fromJson(response, SceneSearch::class.java)
                        if (sceneSearch.status != 103) {
                            productIdList = sceneSearch.data.searchReturnDtoList
                            val size = sceneSearch.data.searchReturnDtoList.size
                            for (i in 0 until size) {
                                val model = Model()
                                model.imageUrl = sceneSearch.data.searchReturnDtoList[i].thumbnailUrl
                                model.price = sceneSearch.data.searchReturnDtoList[i].price
                                model.time = sceneSearch.data.searchReturnDtoList[i].centerTime
                                model.productId = sceneSearch.data.searchReturnDtoList[i].productId
                                data!!.add(model)
                            }
                            initRecyclerView(data!!)

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

    private fun initRecyclerView(data: MutableList<Model>) {
        val glm = GridLayoutManager(context, 2)
        glm.isSmoothScrollbarEnabled = true
        glm.isAutoMeasureEnabled = true
        optics_rv.addItemDecoration(GridSpacingItemDecoration(2, 30, true))
        optics_rv.layoutManager = glm
        optics_rv.setHasFixedSize(true)
        optics_rv.isNestedScrollingEnabled = false
        val mAdapter: SceneClassifyAdapter? = SceneClassifyAdapter(R.layout.item_classify_scene, data, glm)
        optics_rv.adapter = mAdapter

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
        EmallLogger.d(days)
        optics_btn_3_1.text = days
        optics_btn_3_1.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        optics_btn_3_1.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(optics_btn_3_1.text.toString(), optics_btn_3_2.text.toString()) == 1) {
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
        optics_btn_3_2.text = days
        optics_btn_3_2.setBackgroundResource(R.drawable.sign_in_by_tel_btn_border_shape)
        optics_btn_3_2.setTextColor(Color.parseColor("#B4A078"))
        if (compare_date(optics_btn_3_1.text.toString(), optics_btn_3_2.text.toString()) == 1) {
            Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
            flag_3_1 = false
            confirmChangeColor()
        } else {
            flag_3_1 = true
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

        if (flag1 && flag2 && flag3) {
            optics_btn_confirm.isClickable = true
            optics_btn_confirm.setBackgroundResource(R.drawable.screen_btn_shape_confirm)
        } else {
            optics_btn_confirm.isClickable = false
            optics_btn_confirm.setBackgroundResource(R.drawable.sign_up_btn_shape)
        }
    }

}