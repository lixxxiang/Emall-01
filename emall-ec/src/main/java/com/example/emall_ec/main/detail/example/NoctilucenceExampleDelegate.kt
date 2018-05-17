package com.example.emall_ec.main.detail.example

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_discover.*
import kotlinx.android.synthetic.main.delegate_noctilucence_example.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class NoctilucenceExampleDelegate : EmallDelegate() {

    fun create(): NoctilucenceExampleDelegate?{
        return NoctilucenceExampleDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_noctilucence_example

    }

    override fun initial() {
        n_example_toolbar.title = getString(R.string.noctilucence)
        (activity as AppCompatActivity).setSupportActionBar(n_example_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        n_example_toolbar.setNavigationOnClickListener {
            pop()
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}