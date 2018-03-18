package com.example.emall_ec.main.program

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_program.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.view.TextSwitcherView
import java.util.ArrayList


/**
 * Created by lixiang on 2018/3/16.
 */
class ProgramDelegate : BottomItemDelegate() {
    override fun setLayout(): Any? {
        return R.layout.delegate_program
    }

    override fun initial() {
        initViews()
        println("dddd" + DimenUtil().px2dip(context, DimenUtil().getScreenHeight().toFloat()) + DimenUtil().px2dip(context, DimenUtil().getScreenWidth().toFloat()))
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        val topRl = RelativeLayout(activity)
        topRl.id = 1
        val topRlHeight = (DimenUtil().px2dip(context, DimenUtil().getScreenHeight().toFloat()) - 72 - 92 - 250) * 0.4
        val topRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DimenUtil().dip2px(context, topRlHeight.toFloat()))
        topRlParams.addRule(RelativeLayout.BELOW, R.id.program_toolbar)
        topRl.setBackgroundColor(Color.parseColor("#99000000"))
        topRl.layoutParams = topRlParams
        program_root_rl.addView(topRl, topRlParams)

        val leftRl = RelativeLayout(activity)
        leftRl.id = 2
        val RlWidth = (DimenUtil().px2dip(context, DimenUtil().getScreenWidth().toFloat()) - 250) * 0.5
        val leftRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()), DimenUtil().dip2px(context, 250F))
        leftRlParams.addRule(RelativeLayout.BELOW, topRl.id)
        leftRlParams.setMargins(DimenUtil().dip2px(context, 0F), DimenUtil().dip2px(context, 0F), 0, 0)
        leftRl.setBackgroundColor(Color.parseColor("#99000000"))
        leftRl.layoutParams = leftRlParams
        program_root_rl.addView(leftRl, leftRlParams)

        val rightRl = RelativeLayout(activity)
        rightRl.id = 3
        val rightRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()), DimenUtil().dip2px(context, 250F))
        rightRlParams.addRule(RelativeLayout.BELOW, topRl.id)
        rightRlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightRlParams.setMargins(0, DimenUtil().dip2px(context, 0F), 0, DimenUtil().dip2px(context, 0F))
        rightRl.setBackgroundColor(Color.parseColor("#99000000"))
        rightRl.layoutParams = rightRlParams
        program_root_rl.addView(rightRl, rightRlParams)

        val bottomRl = RelativeLayout(activity)
        bottomRl.id = 4
        val bottomRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        bottomRlParams.addRule(RelativeLayout.BELOW, leftRl.id)
        bottomRlParams.addRule(RelativeLayout.ABOVE, R.id.program_bottom_rl)
        bottomRl.setBackgroundColor(Color.parseColor("#99000000"))
        bottomRl.layoutParams = bottomRlParams
        program_root_rl.addView(bottomRl, bottomRlParams)

        val satelliteImageView = ImageView(activity)
        val satelliteImageViewParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 23F), DimenUtil().dip2px(context, 23F))
        satelliteImageViewParams.addRule(RelativeLayout.BELOW, topRl.id)
        satelliteImageViewParams.setMargins(0, DimenUtil().dip2px(context, 280F), 0, 0)
        satelliteImageViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        satelliteImageView.setImageResource(R.drawable.program_satellite)
        satelliteImageView.layoutParams = satelliteImageViewParams
        program_root_rl.addView(satelliteImageView, satelliteImageViewParams)

        val scrollTextView = TextSwitcherView(activity)
        val scrollTextViewParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, DimenUtil().dip2px(context, 17F))
        scrollTextViewParams.addRule(RelativeLayout.BELOW, topRl.id)
        scrollTextViewParams.setMargins(0, DimenUtil().dip2px(context, 316F), 0, 0)
        scrollTextViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        scrollTextView.layoutParams = scrollTextViewParams
        val textArray: MutableList<String> = mutableListOf("111111", "2222222", "3333333")
        scrollTextView.getResource(textArray as ArrayList<String>?)
        program_root_rl.addView(scrollTextView, scrollTextViewParams)


        val tl = ImageView(activity)
        val tlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        tlParams.addRule(RelativeLayout.BELOW, topRl.id)
        tlParams.addRule(RelativeLayout.RIGHT_OF, leftRl.id)
        tlParams.setMargins(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F), 0, 0)
        tl.setImageResource(R.drawable.purple_border)
        tl.pivotX = (tl.width / 2).toFloat()
        tl.pivotY = (tl.height / 2).toFloat()
        tl.rotation = 180F
        tl.layoutParams = tlParams
        program_root_rl.addView(tl, tlParams)

        val tr = ImageView(activity)
        val trParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        trParams.addRule(RelativeLayout.BELOW, topRl.id)
        trParams.addRule(RelativeLayout.LEFT_OF, rightRl.id)
        trParams.setMargins(0, DimenUtil().dip2px(context, 16F), 0, 0)
        tr.setImageResource(R.drawable.purple_border)
        tr.pivotX = (tl.width / 2).toFloat()
        tr.pivotY = (tl.height / 2).toFloat()
        tr.rotation = 270F
        tr.layoutParams = trParams
        program_root_rl.addView(tr, trParams)

        val bl = ImageView(activity)
        val blParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        blParams.addRule(RelativeLayout.ABOVE, bottomRl.id)
        blParams.addRule(RelativeLayout.RIGHT_OF, leftRl.id)
        blParams.setMargins(DimenUtil().dip2px(context, 16F), 0, 0, 0)
        bl.setImageResource(R.drawable.purple_border)
        bl.pivotX = (tl.width / 2).toFloat()
        bl.pivotY = (tl.height / 2).toFloat()
        bl.rotation = 90F
        bl.layoutParams = tlParams
        program_root_rl.addView(bl, blParams)

        val br = ImageView(activity)
        val brParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 16F), DimenUtil().dip2px(context, 16F))
        brParams.addRule(RelativeLayout.ABOVE, bottomRl.id)
        brParams.addRule(RelativeLayout.LEFT_OF, rightRl.id)
        brParams.setMargins(DimenUtil().dip2px(context, 0F), DimenUtil().dip2px(context, 0F), 0, 0)
        br.setImageResource(R.drawable.purple_border)
        br.layoutParams = tlParams
        program_root_rl.addView(br, brParams)

        val title = TextView(activity)
        val titleParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        titleParams.setMargins(0, DimenUtil().dip2px(context, 28F), 0, 0)
        title.layoutParams = titleParams
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        title.text = resources.getString(R.string.program_toolbar)
        title.setTextColor(Color.parseColor("#FFFFFF"))
        title.textSize = 14F
        title.visibility = View.GONE
        program_root_rl.addView(title, titleParams)


        val nextStep = TextView(activity)
        val nextStepParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        nextStepParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        nextStepParams.setMargins(0, DimenUtil().dip2px(context, 28F), DimenUtil().dip2px(context, 18F), 0)
        nextStep.layoutParams = titleParams
        nextStep.text = resources.getString(R.string.next_step)
        nextStep.setTextColor(Color.parseColor("#FFFFFF"))
        nextStep.textSize = 14F
        nextStep.visibility = View.GONE
        program_root_rl.addView(nextStep, nextStepParams)

        program_camera.setOnClickListener {
            program_toolbar.setBackgroundColor(Color.parseColor("#333333"))
            topRl.setBackgroundColor(Color.parseColor("#333333"))
            leftRl.setBackgroundColor(Color.parseColor("#333333"))
            rightRl.setBackgroundColor(Color.parseColor("#333333"))
            bottomRl.setBackgroundColor(Color.parseColor("#333333"))
            program_bottom_rl.setBackgroundColor(Color.parseColor("#333333"))
            ll_bar.setBackgroundColor(Color.parseColor("#333333"))
            program_camera.visibility = View.GONE
            satelliteImageView.visibility = View.GONE
            scrollTextView.visibility = View.GONE
            title.visibility = View.VISIBLE
            program_toolbar_searchbar.visibility = View.INVISIBLE
            nextStep.visibility = View.VISIBLE
        }

    }


}