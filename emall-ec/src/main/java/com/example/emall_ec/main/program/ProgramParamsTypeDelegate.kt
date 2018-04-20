package com.example.emall_ec.main.program

import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.program.adapter.ProgramParamsTypeAdapter
import kotlinx.android.synthetic.main.delegate_program_params_type.*
import kotlinx.android.synthetic.main.item_program_params_type.*
import android.os.Bundle
import me.yokeyword.fragmentation.ISupportFragment


/**
 *eated by lixiang on 2018/3/19.
 */
class ProgramParamsTypeDelegate : BottomItemDelegate() {
    var titleList: MutableList<Int>? = mutableListOf()
    var index = -1
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
            index = i
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
        }

        program_params_type_done_tv.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("index", index.toString())
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            _mActivity.onBackPressed()
        }
        program_back_btn.setOnClickListener{
            pop()
        }
    }

}