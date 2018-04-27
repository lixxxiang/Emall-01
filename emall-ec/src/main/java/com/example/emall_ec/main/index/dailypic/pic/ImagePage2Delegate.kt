package com.example.emall_ec.main.index.dailypic.pic

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_page_1.*
import java.util.*

/**
 * Created by lixiang on 2018/3/29.
 */
class ImagePage2Delegate : BottomItemDelegate() {
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
    fun create(): ImagePage2Delegate? {
        return ImagePage2Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_2
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {
//        val sp = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE) //取得user_id和手机号
//        pic_brief.text = sp.getString("imageName", "")
//        pic_date.text = String.format("每日一图 · %s", sp.getString("imageDate", ""))
        timer.schedule(object : TimerTask() {
            override fun run() {
                var msg = Message()
                msg.what = 0
                mHandler.sendMessage(msg)
            }

        },500,500)
    }
}