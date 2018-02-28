package com.example.emall_ec.main.discover

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.os.Bundle
import android.support.annotation.NonNull
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.delegates.web.WebDelegateImpl
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.RestCreator
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.sign.SignHandler


/**
 * Created by lixiang on 2018/2/26.
 */
class DiscoverDelegate : BottomItemDelegate() {
    override fun initial() {

    }

    override fun setLayout(): Any? {
        return R.layout.delegate_discover
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)




        val delegate = WebDelegateImpl.create("index.html")
        delegate.setTopDelegate(this.getParentDelegate())
        supportDelegate.loadRootFragment(R.id.web_discovery_container, delegate)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}