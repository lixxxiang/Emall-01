package com.example.emall_ec.main.me.collect.type

import android.graphics.Color
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_goods.*

class GoodsDelegate :EmallDelegate() {

    private var flag = false
    fun create(): GoodsDelegate?{
        return GoodsDelegate()
    }
    override fun setLayout(): Any? {
        return R.layout.delegate_goods
    }

    override fun initial() {
        goods_gray_tv.setOnClickListener {
            if (!flag){
                goods_top_ll.visibility = View.VISIBLE
                goods_mask_rl.visibility = View.VISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#B80017"))
                goods_gray_iv.setBackgroundResource(R.drawable.ic_up_red)
                flag = true
            }else{
                goods_top_ll.visibility = View.INVISIBLE
                goods_mask_rl.visibility = View.INVISIBLE
                goods_gray_tv.setTextColor(Color.parseColor("#9B9B9B"))
                goods_gray_iv.setBackgroundResource(R.drawable.ic_down_gray)
                flag = false
            }

        }
    }
}