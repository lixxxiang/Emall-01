package com.example.emall_ec.main.program

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_order_list.*
import kotlinx.android.synthetic.main.delegate_program.*
import com.example.emall_core.util.view.ScreenUtil.dip2px
import android.widget.RelativeLayout
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.log.EmallLogger
import android.util.TypedValue
import kotlinx.android.synthetic.main.delegate_index.*


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
        val leftRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()),DimenUtil().dip2px(context, 250F))
        leftRlParams.addRule(RelativeLayout.BELOW, topRl.id)
        leftRlParams.setMargins(DimenUtil().dip2px(context, 0F), DimenUtil().dip2px(context, 0F), 0, 0)
        leftRl.setBackgroundColor(Color.parseColor("#99000000"))
        leftRl.layoutParams = leftRlParams
        program_root_rl.addView(leftRl, leftRlParams)

        val rightRl = RelativeLayout(activity)
        val rightRlParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, RlWidth.toFloat()),DimenUtil().dip2px(context, 250F))
        rightRlParams.addRule(RelativeLayout.BELOW, topRl.id)
        rightRlParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rightRlParams.setMargins(0, DimenUtil().dip2px(context, 0F), 0, DimenUtil().dip2px(context, 0F))
        rightRl.setBackgroundColor(Color.parseColor("#99000000"))
        rightRl.layoutParams = rightRlParams
        program_root_rl.addView(rightRl, rightRlParams)

        val bottomRl = RelativeLayout(activity)
        val bottomRlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        bottomRlParams.addRule(RelativeLayout.BELOW, leftRl.id)
        bottomRlParams.addRule(RelativeLayout.ABOVE, R.id.program_bottom_rl)
        bottomRl.setBackgroundColor(Color.parseColor("#99000000"))
        bottomRl.layoutParams = bottomRlParams
        program_root_rl.addView(bottomRl, bottomRlParams)

        val satelliteImageView = ImageView(activity)
        val satelliteImageViewParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context, 23F),DimenUtil().dip2px(context, 23F))
        satelliteImageViewParams.addRule(RelativeLayout.BELOW, topRl.id)
        satelliteImageViewParams.setMargins(0, DimenUtil().dip2px(context, 280F), 0, 0)
        satelliteImageViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        satelliteImageView.setImageResource(R.drawable.program_satellite)
        satelliteImageView.layoutParams = satelliteImageViewParams
        program_root_rl.addView(satelliteImageView, satelliteImageViewParams)

    }
}