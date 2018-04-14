package com.example.emall_ec.main.index.dailypic.pic

import android.annotation.SuppressLint
import android.content.Context
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_page_1.*

/**
 * Created by lixiang on 2018/3/29.
 */
class ImagePage2Delegate : BottomItemDelegate() {
    fun create(): ImagePage2Delegate? {
        return ImagePage2Delegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_page_2
    }

    @SuppressLint("ApplySharedPref")
    override fun initial() {
        val sp = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE) //取得user_id和手机号
        pic_brief.text = sp.getString("imageName", "")
        pic_date.text = String.format("每日一图 · %s", sp.getString("imageDate", ""))
    }
}