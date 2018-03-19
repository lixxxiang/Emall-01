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

    var titleList: MutableList<Int>? = mutableListOf()

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
        program_params_listview.adapter = ProgramParamsAdapter(titleList, context)
        program_params_listview.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {
                val delegate: ProgramParamsTypeDelegate = ProgramParamsTypeDelegate().create()!!
                startForResult(delegate, 1)
            } else if (i == 1) {
                DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == ISupportFragment.RESULT_OK) {
            val index = data.getString("index")
            when (index) {
                "0" -> program_params_detail_tv.text = resources.getString(R.string.optics_1)
                "1" -> program_params_detail_tv.text = resources.getString(R.string.noctilucence)
                "2" -> program_params_detail_tv.text = resources.getString(R.string.video1A_1B)
            }
        }
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10) {
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            } else {
                StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).append("月").append(mDay).append("日").toString()
            }

        } else {
            if (mDay < 10) {
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString()
            } else {
                StringBuffer().append(mYear).append("年").append(mMonth + 1).append("月").append(mDay).append("日").toString()
            }

        }
        println("abababab" + days)
    }
}