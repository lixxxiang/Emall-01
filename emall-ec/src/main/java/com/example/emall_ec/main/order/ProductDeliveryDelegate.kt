package com.example.emall_ec.main.order

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_product_delivery.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class ProductDeliveryDelegate : EmallDelegate() {

    fun create(): ProductDeliveryDelegate? {
        return ProductDeliveryDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_product_delivery
    }

    override fun initial() {
        delivery_toolbar.title = getString(R.string.product_delivery)
        (activity as AppCompatActivity).setSupportActionBar(delivery_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        delivery_toolbar.setNavigationIcon(R.drawable.ic_back_small)
        delivery_scan_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/scan.ttf")
        delivery_toolbar.setNavigationOnClickListener {
            pop()
        }
        if (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(delivery_ll) > 0) {
            val rl = RelativeLayout(activity)
            val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(delivery_ll) + DimenUtil().dip2px(context, 54F)))
            rl.layoutParams = rlParams
            rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
            delivery_ll.addView(rl, rlParams)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}