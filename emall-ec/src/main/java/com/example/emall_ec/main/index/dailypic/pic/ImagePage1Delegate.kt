package com.example.emall_ec.main.index.dailypic.pic

import android.os.Bundle
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_page_1.*
import android.annotation.SuppressLint
import android.content.Context


/**
 * Created by lixiang on 2018/3/29.
 */
class ImagePage1Delegate : BottomItemDelegate() {

    fun create(): ImagePage1Delegate? {
        return ImagePage1Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_1
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {
        val sp = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE) //取得user_id和手机号
        EmallLogger.d(sp.getString("imageName",""))
        pic_brief.text = sp.getString("imageName", "")
        pic_date.text = String.format("每日一图 · %s", sp.getString("imageDate", ""))
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        EmallLogger.d("stop")
    }
}