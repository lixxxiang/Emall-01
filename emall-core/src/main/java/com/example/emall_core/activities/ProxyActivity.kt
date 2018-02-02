package com.example.emall_core.activities

import android.os.Bundle
import android.support.v7.widget.ContentFrameLayout
import com.example.emall_core.R
import com.example.emall_core.delegates.EmallDelegate
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by lixiang on 2018/1/25.
 */

abstract class ProxyActivity : SupportActivity() {
    /**
     * 主fragment
     */
    abstract fun setRootDelegate(): EmallDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    fun initContainer(savedInstanceState: Bundle?) {
        val container = ContentFrameLayout(this)
        container.id = R.id.delegate_container
        setContentView(container)
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
    }
}
