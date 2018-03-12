package com.example.emall_ec.main.classify

import android.os.Bundle
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.order.OrderListDelegate
import kotlinx.android.synthetic.main.delegate_base_classify.*

/**
 * Created by lixiang on 2018/3/12.
 */
class BaseClassifyDelegate : BottomItemDelegate() {
    var DELEGATE: EmallDelegate? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_base_classify
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
        base_classify_optics_siv.setOnClickListener {
            val delegate: ClassifyDelegate = ClassifyDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }
}