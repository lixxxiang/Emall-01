package com.example.emall_ec.main.detail.example

import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R

class ProgramExampleDelegate : EmallDelegate() {

    fun create(): ProgramExampleDelegate?{
        return ProgramExampleDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_program_example
    }

    override fun initial() {
    }
}