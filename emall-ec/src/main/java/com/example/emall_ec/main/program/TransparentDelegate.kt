package com.example.emall_ec.main.program

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate

class TransparentDelegate : BottomItemDelegate() {
    var flag = false
    var DELEGATE: EmallDelegate? = null

    override fun setLayout(): Any? {
        return R.layout.delegate_transparent
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (!flag){
            (DELEGATE as EcBottomDelegate).start(ProgramIndexDelegate().create())
            flag = true
        }
        EmallLogger.d(EcBottomDelegate().pageTag)
    }
}