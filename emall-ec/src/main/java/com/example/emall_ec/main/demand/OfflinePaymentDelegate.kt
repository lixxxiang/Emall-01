package com.example.emall_ec.main.demand

import android.support.v7.app.AppCompatActivity
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegat_offline_payment.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.Spannable
import android.view.View
import android.graphics.Color.parseColor
import android.os.Bundle
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.ClickableSpan
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.GoodsDetailDelegate
import com.example.emall_ec.main.me.ContactDelegate
import com.example.emall_ec.main.order.OrderListDelegate


class OfflinePaymentDelegate : BottomItemDelegate() {

    fun create(): OfflinePaymentDelegate? {
        return OfflinePaymentDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegat_offline_payment
    }

    override fun initial() {
        setSwipeBackEnable(false)

        offline_toolbar.title = "线下支付"
        (activity as AppCompatActivity).setSupportActionBar(offline_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        offline_toolbar.setNavigationOnClickListener {
            EmallLogger.d(arguments.getString("PAGE_FROM"))
            when {
                arguments.getString("PAGE_FROM") == "CLASSIFY" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "ORDER_LIST" -> popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "ME" -> popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)

                arguments.getString("PAGE_FROM") == "GOODS_DETAIL" -> popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "PAYMENT" -> {
                    if (findFragment(GoodsDetailDelegate().javaClass) == null) {
                        popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                    } else
                        popTo(findFragment(GoodsDetailDelegate().javaClass).javaClass, false)
                }
                arguments.getString("PAGE_FROM") == "PROGRAM_INDEX" ->
                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                arguments.getString("PAGE_FROM") == "PROGRAM" ->
                    popTo(findFragment(EcBottomDelegate().javaClass).javaClass, false)
                else -> pop()
            }
//            pop()
        }
        imageSpan()
        imageSpan2()
        imageSpan3()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    fun imageSpan() {
        val spannableImgae = Spannable.Factory.getInstance().newSpannable(offline_tv.text)

        spannableImgae.setSpan(object : NolineClickSpan() {
            override fun onClick(widget: View) {
                val delegate: ContactDelegate = ContactDelegate().create()!!
                start(delegate)
            }
        }, spannableImgae.length - 4, spannableImgae.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        offline_tv.movementMethod = LinkMovementMethod.getInstance()
        val backgroundColorSpan = ForegroundColorSpan(parseColor("#4A90E2"))
        spannableImgae.setSpan(backgroundColorSpan, spannableImgae.length - 4, spannableImgae.length - 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        val image = this.resources.getDrawable(R.drawable.contact_icon)
        image.setBounds(0, 0, 60, 60)
        val imageSpan = ImageSpan(image)
        spannableImgae.setSpan(imageSpan, spannableImgae.length - 2, spannableImgae.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        offline_tv.text = spannableImgae
    }

    fun imageSpan2() {
        val spannableImgae = Spannable.Factory.getInstance().newSpannable(offline_tv2.text)

        spannableImgae.setSpan(object : NolineClickSpan() {
            override fun onClick(widget: View) {
                val delegate: ContactDelegate = ContactDelegate().create()!!
                start(delegate)
            }
        }, spannableImgae.length - 12, spannableImgae.length - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        offline_tv2.movementMethod = LinkMovementMethod.getInstance()


        val backgroundColorSpan = ForegroundColorSpan(parseColor("#4A90E2"))
        spannableImgae.setSpan(backgroundColorSpan, spannableImgae.length - 12, spannableImgae.length - 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        val image = this.resources.getDrawable(R.drawable.contact_icon)
        image.setBounds(0, 0, 60, 60)
        val imageSpan = ImageSpan(image)
        spannableImgae.setSpan(imageSpan, spannableImgae.length - 10, spannableImgae.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        offline_tv2.text = spannableImgae
    }

    fun imageSpan3() {
        val spannableImgae = Spannable.Factory.getInstance().newSpannable(offline_tv3.text)

        spannableImgae.setSpan(object : NolineClickSpan() {
            override fun onClick(widget: View) {
                val delegate: OrderListDelegate = OrderListDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", arguments.getString("PAGE_FROM"))
                delegate.arguments = bundle
                start(delegate)
            }
        }, spannableImgae.length - 14, spannableImgae.length - 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        offline_tv3.movementMethod = LinkMovementMethod.getInstance()


        val backgroundColorSpan = ForegroundColorSpan(parseColor("#4A90E2"))
        spannableImgae.setSpan(backgroundColorSpan, spannableImgae.length - 14, spannableImgae.length - 10, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        val image = this.resources.getDrawable(R.drawable.order_icon)
        image.setBounds(0, 0, 60, 60)
        val imageSpan = ImageSpan(image)
        spannableImgae.setSpan(imageSpan, spannableImgae.length - 10, spannableImgae.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        offline_tv3.text = spannableImgae
    }

    internal open class NolineClickSpan : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ds.linkColor
            ds.isUnderlineText = false //去掉下划线
        }

        override fun onClick(widget: View) {

        }
    }

}