package com.example.emall_ec.main.program

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.program.adapter.ProgramParamsAdapter
import kotlinx.android.synthetic.main.delegate_program_params.*
import kotlinx.android.synthetic.main.item_program_params.*
import me.yokeyword.fragmentation.ISupportFragment
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import com.example.emall_core.util.log.EmallLogger
import kotlinx.android.synthetic.main.delegate_optics1.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by lixiang on 2018/3/19.
 */
class ProgramParamsDelegate : BottomItemDelegate() {

    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    var adapter: ProgramParamsAdapter? = null
    var titleList: MutableList<Int>? = mutableListOf()
    var detailList: MutableList<String>? = mutableListOf()
    var mSharedPreferences: SharedPreferences? = null
    var productType = String()
    var startTime = String()
    var endTime = String()
    private var scopeGeo = String()
    private var angle = String()
    private var cloud = String()
    private var center = String()
    private var geoString = String()
    private var area = String()
    private var bitmapByte: ByteArray? = null

    fun create(): ProgramParamsDelegate? {
        return ProgramParamsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_program_params
    }


    @SuppressLint("ApplySharedPref")
    override fun initial() {

        EmallLogger.d(String.format("%s,%s, %s", arguments.getString("scopeGeo"), arguments.getString("angle"), arguments.getString("cloud")))
        scopeGeo = arguments.getString("scopeGeo")
        angle = arguments.getString("angle")
        cloud = arguments.getString("cloud")
        center = arguments.getString("center")
        geoString = arguments.getString("geoString")
        area = arguments.getString("area")

        titleList!!.add(R.string.image_type)
        titleList!!.add(R.string.start_time)
        titleList!!.add(R.string.end_time)
        detailList!!.add("")
        detailList!!.add("")
        detailList!!.add("")
        mSharedPreferences = activity.getSharedPreferences("PROGRAMMING", Context.MODE_PRIVATE)

        adapter = ProgramParamsAdapter(titleList, detailList, context)
        program_params_listview.adapter = adapter
        program_params_listview.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {
                val delegate: ProgramParamsTypeDelegate = ProgramParamsTypeDelegate().create()!!
                startForResult(delegate, 1)
            } else if (i == 1) {
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            } else if (i == 2) {
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()
            }

        }

        program_back_btn.setOnClickListener {
            pop()
        }

        program_next_step_tv.setOnClickListener {
            val editor = mSharedPreferences!!.edit()
            editor.putString("productType", productType)
            editor.putString("scopeGeo", scopeGeo)
            /**
             * 在哪里登陆？？？？？？？？？？？？？？？？？
             * 在哪里登陆？？？？？？？？？？？？？？？？？
             * 在哪里登陆？？？？？？？？？？？？？？？？？
             */
            editor.putString("startTime", startTime)
            editor.putString("endTime", endTime)
            editor.putString("cloud", cloud)
            editor.putString("angle", angle)
            editor.putString("center", center)
            editor.putString("geoString", geoString)
            editor.putString("area", area)
            editor.commit()

            val delegate: ProgramDetailDelegate = ProgramDetailDelegate().create()!!
            val bundle = Bundle()
            bundle.putByteArray("image", arguments.getByteArray("image"))
            delegate.arguments = bundle
            start(delegate)
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    detailList!![0] = resources.getString(R.string.optics_1)
                    productType = "1"
                    adapter!!.notifyDataSetChanged()
                }
                "1" -> {
                    detailList!![0] = resources.getString(R.string.noctilucence)
                    productType = "2"
                    adapter!!.notifyDataSetChanged()
                }
                "2" -> {
                    detailList!![0] = resources.getString(R.string.video1A_1B)
                    productType = "3"
                    adapter!!.notifyDataSetChanged()
                }
            }
            showNextStep()

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
        detailList!![1] = days
        if (detailList!![2].isEmpty()) {
            adapter!!.notifyDataSetChanged()
            startTime = days
        } else {
            if (compare_date(detailList!![1], detailList!![2]) != 1) {
                adapter!!.notifyDataSetChanged()
                startTime = days
            } else {
                if (!detailList!![2].isEmpty()) {
                    Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
                    detailList!![1] = ""
                }
                adapter!!.notifyDataSetChanged()
            }
        }

        showNextStep()
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

        detailList!![2] = days
        EmallLogger.d(detailList!![2])
        if (detailList!![1].isEmpty()) {
            adapter!!.notifyDataSetChanged()
            endTime = days
        } else {
            if (compare_date(detailList!![1], detailList!![2]) != 1) {
                adapter!!.notifyDataSetChanged()
                endTime = days
            } else {
                if (!detailList!![1].isEmpty()) {
                    Toast.makeText(activity, getString(R.string.input_right_time), Toast.LENGTH_SHORT).show()
                    detailList!![2] = ""
                }
                adapter!!.notifyDataSetChanged()
            }
        }
        showNextStep()

    }

    @SuppressLint("SimpleDateFormat")
    private fun compare_date(DATE1: String, DATE2: String): Int {
        val df = SimpleDateFormat("yyyy-MM-dd")
        try {
            val dt1 = df.parse(DATE1)
            val dt2 = df.parse(DATE2)
            EmallLogger.d(String.format("%s %s", dt1.toString(), dt2.toString()))

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

    private fun checkDone(): Boolean {
        return !detailList!![0].isEmpty() && !detailList!![1].isEmpty() && !detailList!![2].isEmpty()
    }

    private fun showNextStep() {
        if (checkDone()) {
            program_next_step_tv.visibility = View.VISIBLE
        } else
            program_next_step_tv.visibility = View.INVISIBLE

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}