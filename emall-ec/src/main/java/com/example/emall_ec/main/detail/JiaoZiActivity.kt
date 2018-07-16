package com.example.emall_ec.main.detail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.activity_jiaozi.*

class JiaoZiActivity : AppCompatActivity() {
    var mSharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_jiaozi)
        jiaozi_toolbar.title = intent.getStringExtra("title")
        setSupportActionBar(jiaozi_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mSharedPreferences = getSharedPreferences("TO_VIDEO", Context.MODE_PRIVATE)
        val editor = mSharedPreferences!!.edit()
        editor.putString("to_video", "true")
        editor.commit()
        jiaozi_toolbar.setNavigationOnClickListener {
            JZVideoPlayer.releaseAllVideos()
            finish()
        }
        videoplayer.setUp(intent.getStringExtra("url"), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        videoplayer.startButton.performClick()
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        JZVideoPlayer.releaseAllVideos()
    }
}