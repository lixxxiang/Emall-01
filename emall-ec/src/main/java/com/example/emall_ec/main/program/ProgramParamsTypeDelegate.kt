package com.example.emall_ec.main.program

import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.program.adapter.ProgramParamsTypeAdapter
import kotlinx.android.synthetic.main.delegate_program_params_type.*
import kotlinx.android.synthetic.main.item_program_params_type.*

/**
 *eated by lixiang on 2018/3/19.
 */
class ProgramParamsTypeDelegate : BottomItemDelegate() {
    var titleList: MutableList<Int>? = mutableListOf()

    fun create(): ProgramParamsTypeDelegate? {
        return ProgramParamsTypeDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_program_params_type
    }

    override fun initial() {
        titleList!!.add(R.string.optics_1)
        titleList!!.add(R.string.noctilucence)
        titleList!!.add(R.string.video1A_1B)
        val adapter = ProgramParamsTypeAdapter(titleList, context)
        program_params_type_listview.adapter = adapter
        program_params_type_listview.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        program_params_type_listview.setOnItemClickListener { adapterView, view, i, l ->
            program_params_type_done_tv.visibility = View.VISIBLE
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
        }
    }

}