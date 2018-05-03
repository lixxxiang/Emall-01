package com.example.emall_core.delegates.bottom

import android.graphics.Color
import com.example.emall_core.delegates.EmallDelegate
import android.support.annotation.ColorInt
import com.example.emall_core.R
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import me.yokeyword.fragmentation.ISupportFragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.view.KeyEvent
import com.joanzapata.iconify.widget.IconTextView
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.delegate_bottom.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 15/02/2018.
 */
abstract class BaseBottomDelegate : BottomItemDelegate(), View.OnClickListener {
    private val TAB_BEANS: ArrayList<BottomTabBean> = ArrayList()//icon 和 文字
    private val ITEM_DELEGATES: ArrayList<BottomItemDelegate> = ArrayList()//每一页的内容
    private val ITEMS: LinkedHashMap<BottomTabBean, BottomItemDelegate> = LinkedHashMap()//关联
    private val ICONS_N: MutableList<Int> = mutableListOf()
    private val ICONS_H: MutableList<Int> = mutableListOf()
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0
    private var mCurrentDelegate = 0
    private var mIndexDelegate = 0
    private var mClickedColor = Color.parseColor("#B4A078")

    abstract fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate>

    override fun setLayout(): Any? {
        return R.layout.delegate_bottom
    }

    abstract fun setIndexDelegate(): Int

    @ColorInt
    abstract fun setClickedColor(): Int

//    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            _mActivity.finish()
//        } else {
//            TOUCH_TIME = System.currentTimeMillis()
//            Toast.makeText(_mActivity, "Press again to exit", Toast.LENGTH_SHORT).show()
//        }
//        return true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndexDelegate = setIndexDelegate()
        if (setClickedColor() !== 0) {
            mClickedColor = setClickedColor()
        }

        val builder: ItemBuilder? = ItemBuilder().builder()
        val items: LinkedHashMap<BottomTabBean, BottomItemDelegate> = setItems(builder!!)
        ITEMS.putAll(items)

        for ((key, value) in ITEMS) {
            TAB_BEANS.add(key)
            ITEM_DELEGATES.add(value)
        }
    }

    fun setIcons(){
        ICONS_H.add(R.drawable.home_h)
        ICONS_H.add(R.drawable.classify_h)
        ICONS_H.add(R.drawable.classify_h)
        ICONS_H.add(R.drawable.special_h)
        ICONS_H.add(R.drawable.me_h)
        ICONS_N.add(R.drawable.home_n)
        ICONS_N.add(R.drawable.classify_n)
        ICONS_N.add(R.drawable.classify_n)
        ICONS_N.add(R.drawable.special_n)
        ICONS_N.add(R.drawable.me_n)
    }
    override fun initial() {
        setSwipeBackEnable(false)

        setIcons()

        val size = ITEMS.size
        for (i in 0 until size) {
            LayoutInflater.from(context).inflate(R.layout.bottom_item_icon_text_layout, bottom_bar)
            if (i != 2) {
                val item: RelativeLayout = bottom_bar.getChildAt(i) as RelativeLayout
                //设置每个item的点击事件
                item.tag = i
                item.setOnClickListener(this)
                val itemIcon: AppCompatImageView = item.getChildAt(0) as AppCompatImageView
                val itemTitle: AppCompatTextView = item.getChildAt(1) as AppCompatTextView
                val bean: BottomTabBean = TAB_BEANS[i]
                //初始化数据
                itemIcon.setImageResource(bean.icon)
                itemTitle.text = bean.title
                if (i == mIndexDelegate) {
                    itemTitle.setTextColor(Color.parseColor("#B4A078"))
                }
            } else {
                val item: RelativeLayout = bottom_bar.getChildAt(i) as RelativeLayout
                //设置每个item的点击事件
                item.tag = i
                item.setOnClickListener {
                    val tag = item.tag as Int
                    supportDelegate.showHideFragment(ITEM_DELEGATES[tag], ITEM_DELEGATES[mCurrentDelegate])
                    //注意先后顺序
                    mCurrentDelegate = tag
                }
                val itemIcon: AppCompatImageView = item.getChildAt(0) as AppCompatImageView
                val bean: BottomTabBean = TAB_BEANS[i]
                itemIcon.setImageResource(bean.icon)
                itemIcon.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                if (i == mIndexDelegate) {
//                    itemIcon.setTextColor(mClickedColor)
//                    itemTitle.setTextColor(mClickedColor)
                }
            }
        }

        val delegateArray = ITEM_DELEGATES.toArray(arrayOfNulls<ISupportFragment>(size))
        supportDelegate.loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, *delegateArray)
    }

    private fun resetColor() {
        val count = bottom_bar.childCount
        for (i in 0 until count) {
            if (i != 2) {
                val item = bottom_bar.getChildAt(i) as RelativeLayout
                val itemIcon = item.getChildAt(0) as AppCompatImageView
                itemIcon.setImageResource(ICONS_N[i])
                val itemTitle = item.getChildAt(1) as AppCompatTextView
                itemTitle.setTextColor(Color.parseColor("#5C5C5C"))
            }
        }
    }

    override fun onClick(v: View) {
        val tag = v.tag as Int

        if(tag != 4){
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        resetColor()
        val item = v as RelativeLayout
        val itemIcon = item.getChildAt(0) as AppCompatImageView
        itemIcon.setImageResource(ICONS_H[tag])
        val itemTitle = item.getChildAt(1) as AppCompatTextView
        itemTitle.setTextColor(mClickedColor)
        supportDelegate.showHideFragment(ITEM_DELEGATES[tag], ITEM_DELEGATES[mCurrentDelegate])
        //注意先后顺序
        mCurrentDelegate = tag
    }

//    override fun onBackPressedSupport(): Boolean {
//        return true
//    }

    override fun onBackPressedSupport(): Boolean {
        activity.moveTaskToBack(false)
        EmallLogger.d("main")
        return true
    }
}