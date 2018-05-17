package com.example.emall_ec.main.detail.example

import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_video_example.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class VideoExampleDelegate : EmallDelegate() {

    fun create(): VideoExampleDelegate?{
        return VideoExampleDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_video_example
    }

    override fun initial() {
        video_example_toolbar.title = getString(R.string.video1A_1B)
        (activity as AppCompatActivity).setSupportActionBar(video_example_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        video_example_toolbar.setNavigationOnClickListener {
            pop()
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}