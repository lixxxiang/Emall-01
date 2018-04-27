package com.example.emall_ec.main.index.dailypic.video

import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import cn.jzvd.JZVideoPlayerStandard
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_video_play.*
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.widget.MediaController


class VideoPlayDelegate : EmallDelegate() {

    fun create(): VideoPlayDelegate? {
        return VideoPlayDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_play
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        videoView.setMediaController(MediaController(activity))
        videoView.setVideoURI(Uri.parse(arguments.getString("url")))
        videoView.start()
    }
}