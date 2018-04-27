package com.example.emall_ec.main.index.dailypic.video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.blankj.utilcode.util.FragmentUtils.pop
import com.example.emall_ec.R
import io.vov.vitamio.Vitamio
import io.vov.vitamio.widget.MediaController
import kotlinx.android.synthetic.main.activity_vitamio_player.*
import kotlinx.android.synthetic.main.delegate_daily_pic.*

class VitamioPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_vitamio_player)
        vitamio_toolbar.title = intent.getStringExtra("title")
        setSupportActionBar(vitamio_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Vitamio.isInitialized(this)
        vitamio_toolbar.setNavigationOnClickListener {
            finish()
        }
        init()
    }

    private fun init() {

        if (Vitamio.isInitialized(this)) {
            videoview!!.setVideoPath(intent.getStringExtra("url"))
            videoview!!.setMediaController(MediaController(this))
            videoview!!.start()
        }
    }
}