package com.example.emall_ec.main.program

import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.program.adapter.ProgramParamsAdapter
import kotlinx.android.synthetic.main.delegate_program_params.*
import kotlinx.android.synthetic.main.item_program_params.*
import me.yokeyword.fragmentation.ISupportFragment
import android.app.DatePickerDialog
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

    fun create(): ProgramParamsDelegate? {
        return ProgramParamsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_program_params
    }


    override fun initial() {

        titleList!!.add(R.string.image_type)
        titleList!!.add(R.string.start_time)
        titleList!!.add(R.string.end_time)
        detailList!!.add("")
        detailList!!.add("")
        detailList!!.add("")
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
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {
                "0" -> {
                    detailList!![0] = resources.getString(R.string.optics_1)
                    adapter!!.notifyDataSetChanged()
                }
                "1" -> {
                    detailList!![0] = resources.getString(R.string.noctilucence)
                    adapter!!.notifyDataSetChanged()
                }
                "2" -> {
                    detailList!![0] = resources.getString(R.string.video1A_1B)
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            else
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append(mDay).append("日").toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            else
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日").toString()
        }
        detailList!![1] = days
        adapter!!.notifyDataSetChanged()
    }

    private val onDateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            else
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append(mDay).append("日").toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
             else
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日").toString()
        }
        detailList!![2] = days
        adapter!!.notifyDataSetChanged()
    }
}