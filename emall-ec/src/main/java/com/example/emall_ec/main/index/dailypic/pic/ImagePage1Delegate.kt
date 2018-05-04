package com.example.emall_ec.main.index.dailypic.pic

import android.os.Bundle
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_page_1.*
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import java.util.*


/**
 * Created by lixiang on 2018/3/29.
 */
class ImagePage1Delegate : BottomItemDelegate() {
    var timer = Timer()

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {

            // TODO Auto-generated method stub
            if (msg.what == 0) {
                //这里可以进行UI操作，如Toast，Dialog等
                val sp = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE)
                if(sp.getString("imageName", "") != "Empty/NULL log message"){
                    EmallLogger.d(sp.getString("imageName", ""))
                    pic_brief.text = sp.getString("imageName", "")
                    pic_date.text = String.format("每日一图 · %s", sp.getString("imageDate", ""))
                    timer.cancel()
                    timer.purge()
                }
            }
        }
    }

    fun create(): ImagePage1Delegate? {
        return ImagePage1Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_1
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {
//        Handler().postDelayed({
//            val sp = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE)
//            EmallLogger.d(sp.getString("imageName",""))
//            pic_brief.text = sp.getString("imageName", "")
//            pic_date.text = String.format("每日一图 · %s", sp.getString("imageDate", ""))
//        }, 1000)
        timer.schedule(object : TimerTask() {
            override fun run() {
                var msg = Message()
                msg.what = 0
                mHandler.sendMessage(msg)
            }

        },500,500)

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        EmallLogger.d("stop")
    }
}