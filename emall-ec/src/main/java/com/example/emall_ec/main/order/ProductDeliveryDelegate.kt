package com.example.emall_ec.main.order

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.demand.FillOrderDelegate
import com.example.emall_ec.main.scanner.ScannerDelegate
import kotlinx.android.synthetic.main.delegate_product_delivery.*
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class ProductDeliveryDelegate : EmallDelegate() {

    var mSharedPreferences: SharedPreferences? = null

    fun create(): ProductDeliveryDelegate? {
        return ProductDeliveryDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_product_delivery
    }

    override fun initial() {
        delivery_toolbar.title = getString(R.string.product_delivery)
        mSharedPreferences = activity.getSharedPreferences("BACK_FROM", Context.MODE_PRIVATE)

        (activity as AppCompatActivity).setSupportActionBar(delivery_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        delivery_toolbar.setNavigationIcon(R.drawable.ic_back_small)
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

        delivery.setOnClickListener{
            val delegate: ScannerDelegate = ScannerDelegate().create()!!
            val bundle: Bundle? = Bundle()
            EmallLogger.d( arguments.getString("PAGE_FROM"))
            bundle!!.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
            delegate.arguments = bundle
            start(delegate)
        }

        swipeBackLayout.addSwipeListener(object : SwipeBackLayout.OnSwipeListener{
            override fun onEdgeTouch(oritentationEdgeFlag: Int) {
                val editor = mSharedPreferences!!.edit()
                editor.putString("BACK_FROM", "DELIVERY")
                editor.commit()
            }

            override fun onDragScrolled(scrollPercent: Float) {
            }

            override fun onDragStateChange(state: Int) {
            }
        })
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}