package com.example.emall_ec.main.discover

import android.content.Context
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.delegates.web.WebDelegateImpl
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import kotlinx.android.synthetic.main.delegate_discover.*
import kotlinx.android.synthetic.main.delegate_order_list.*


/**
 * Created by lixiang on 2018/2/26.
 */
class DiscoverDelegate : BottomItemDelegate() {

    fun create(): DiscoverDelegate? {
        return DiscoverDelegate()
    }

    override fun initial() {
        toolbar_webview.title = "WHAT_VIEW"
        (activity as AppCompatActivity).setSupportActionBar(toolbar_webview)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar_webview.setNavigationOnClickListener {
            pop()
        }
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_discover
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val delegate = WebDelegateImpl.create(arguments.getString("URL"))
//        delegate.setTopDelegate(this.getParentDelegate())
        supportDelegate.loadRootFragment(R.id.web_discovery_container, delegate)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}