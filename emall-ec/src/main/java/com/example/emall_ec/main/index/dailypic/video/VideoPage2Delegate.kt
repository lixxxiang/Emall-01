package com.example.emall_ec.main.index.dailypic.video

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_page_1.*
import kotlinx.android.synthetic.main.delegate_video_page_2.*

class VideoPage2Delegate : BottomItemDelegate() {

    fun create(): VideoPage1Delegate? {
        return VideoPage1Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_page_2
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {
        val sp = activity.getSharedPreferences("VIDEO_DETAIL", Context.MODE_PRIVATE) //取得user_id和手机号
        EmallLogger.d(sp.getString("videoName",""))
        video_brief.text = sp.getString("videoName", "")
        video_date.text = String.format("脉动地球 · %s", sp.getString("videoDate", ""))
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        EmallLogger.d("stop")
    }
}