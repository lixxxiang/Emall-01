package com.example.emall_ec.main.program

import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.order.OrderListDelegate
import com.example.emall_ec.main.program.adapter.ProgramParamsAdapter
import kotlinx.android.synthetic.main.delegate_program_params.*

/**
 * Created by lixiang on 2018/3/19.
 */
class ProgramParamsDelegate : BottomItemDelegate(){
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
            if (i == 0){
                val delegate: ProgramParamsTypeDelegate = ProgramParamsTypeDelegate().create()!!
                start(delegate)
            }
        }
    }

}