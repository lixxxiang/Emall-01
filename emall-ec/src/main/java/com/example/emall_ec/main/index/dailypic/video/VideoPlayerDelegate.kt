package com.example.emall_ec.main.index.dailypic.video

import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.R.layout.activity_vitamio_player
import io.vov.vitamio.Vitamio
import io.vov.vitamio.widget.MediaController
import kotlinx.android.synthetic.main.activity_vitamio_player.*

class VideoPlayerDelegate : EmallDelegate() {

    fun create():VideoPlayerDelegate?{
        return VideoPlayerDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.activity_vitamio_player
    }

    override fun initial() {
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        vitamio_toolbar.title = arguments.getString("title")
        (activity as AppCompatActivity).setSupportActionBar(vitamio_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Vitamio.isInitialized(activity)
        vitamio_toolbar.setNavigationOnClickListener {
            pop()
        }
        init()
    }

    private fun init() {

        if (Vitamio.isInitialized(activity)) {
            videoview!!.setVideoPath(arguments.getString("url"))
            videoview!!.setMediaController(MediaController(activity))
            videoview!!.start()
        }
    }
}