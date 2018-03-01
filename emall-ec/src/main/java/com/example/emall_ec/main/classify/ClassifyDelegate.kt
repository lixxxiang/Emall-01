package com.example.emall_ec.main.classify

import android.os.Bundle
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.DetailDataConverter
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.detail.VideoDetailDataConverter
import com.example.emall_ec.main.index.VideoDetailFields
import kotlinx.android.synthetic.main.delegate_classify.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator



/**
 * Created by lixiang on 15/02/2018.
 */
class ClassifyDelegate : BottomItemDelegate() {

    var DELEGATE : EmallDelegate ?= null
    override fun setLayout(): Any? {
        return R.layout.delegate_classify
    }

    override fun initial() {
        DELEGATE = getParentDelegate()
        test_classify_btn.setOnClickListener {

            val delegate: GoodsDetailDelegate = GoodsDetailDelegate().create()!!
            val bundle : Bundle ?= Bundle()
            bundle!!.putString("KEY", "ID")
            delegate.arguments = bundle
            (DELEGATE as EcBottomDelegate).start(delegate)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}