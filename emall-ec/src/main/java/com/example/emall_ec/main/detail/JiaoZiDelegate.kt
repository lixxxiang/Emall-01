package com.example.emall_ec.main.detail

import android.support.v7.app.AppCompatActivity
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.activity_jiaozi.*

class JiaoZiDelegate : EmallDelegate() {

    fun create(): JiaoZiDelegate? {
        return JiaoZiDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.activity_jiaozi
    }

    override fun initial() {
//        jiaozi_toolbar.title = intent.getStringExtra("title")
        jiaozi_toolbar.title = arguments.getString("title")
        (activity as AppCompatActivity).setSupportActionBar(jiaozi_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        jiaozi_toolbar.setNavigationOnClickListener {
            JZVideoPlayer.releaseAllVideos()
//            finish()
            pop()
        }
//        videoplayer.setUp(intent.getStringExtra("url"), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        videoplayer.setUp( arguments.getString("url"), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        videoplayer.startButton.performClick()
    }
}