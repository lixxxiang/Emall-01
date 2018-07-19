package com.example.emall_ec.main.detail

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.example.emall_ec.R
import io.vov.vitamio.Vitamio
import kotlinx.android.synthetic.main.activity_jiaozi.*
import kotlinx.android.synthetic.main.delegate_goods_detail.*

class JiaoZiActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_jiaozi)
        jiaozi_toolbar.title = intent.getStringExtra("title")
        setSupportActionBar(jiaozi_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        jiaozi_toolbar.setNavigationOnClickListener {
            JZVideoPlayer.releaseAllVideos()
            finish()
        }
        videoplayer.setUp(intent.getStringExtra("url"), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
//        videoplayer.startButton.performClick()
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}