package com.example.emall_ec.main.discover

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.delegates.web.WebDelegateImpl
import com.example.emall_ec.R


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