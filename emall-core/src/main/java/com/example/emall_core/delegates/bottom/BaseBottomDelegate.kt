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
import com.joanzapata.iconify.widget.IconTextView
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.delegate_bottom.*
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT


/**
 * Created by lixiang on 15/02/2018.
 */
abstract class BaseBottomDelegate : EmallDelegate(), View.OnClickListener {
    private val TAB_BEANS: ArrayList<BottomTabBean> = ArrayList()//icon 和 文字
    private val ITEM_DELEGATES: ArrayList<BottomItemDelegate> = ArrayList()//每一页的内容
    private val ITEMS: LinkedHashMap<BottomTabBean, BottomItemDelegate> = LinkedHashMap()//关联
    private var mCurrentDelegate = 0
    private var mIndexDelegate = 0
    private var mClickedColor = Color.RED

    abstract fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemDelegate>

    override fun setLayout(): Any? {
        return R.layout.delegate_bottom
    }

    abstract fun setIndexDelegate(): Int

    @ColorInt
    abstract fun setClickedColor(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndexDelegate = setIndexDelegate()
        if (setClickedColor() !== 0) {
            mClickedColor = setClickedColor()
        }

        val builder: ItemBuilder? = ItemBuilder().builder()
        val items : LinkedHashMap<BottomTabBean, BottomItemDelegate> = setItems(builder!!)
        ITEMS.putAll(items)

        for ((key, value) in ITEMS) {
            TAB_BEANS.add(key)
            ITEM_DELEGATES.add(value)
        }
    }

    override fun initial() {
        val size = ITEMS.size
        for (i in 0 until size) {
            LayoutInflater.from(context).inflate(R.layout.bottom_item_icon_text_layout, bottom_bar)
            if (i != 2){
                val item:RelativeLayout = bottom_bar.getChildAt(i) as RelativeLayout
                //设置每个item的点击事件
                item.tag = i
                item.setOnClickListener(this)
                val itemIcon : AppCompatImageView = item.getChildAt(0) as AppCompatImageView
                val itemTitle : AppCompatTextView = item.getChildAt(1) as AppCompatTextView
                val bean : BottomTabBean= TAB_BEANS[i]
                //初始化数据
                itemIcon.setImageResource(bean.icon)
                itemTitle.text = bean.title
                if (i == mIndexDelegate) {
                    itemTitle.setTextColor(mClickedColor)
                }
            }else{
                val item:RelativeLayout = bottom_bar.getChildAt(i) as RelativeLayout
                //设置每个item的点击事件
                item.tag = i
                item.setOnClickListener{
                    val tag = item.tag as Int
                    supportDelegate.showHideFragment(ITEM_DELEGATES[tag], ITEM_DELEGATES[mCurrentDelegate])
                    //注意先后顺序
                    mCurrentDelegate = tag
                }
                val itemIcon : AppCompatImageView = item.getChildAt(0) as AppCompatImageView
                val bean : BottomTabBean= TAB_BEANS[i]
                itemIcon.setImageResource(bean.icon)
                itemIcon.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT)
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
            if(i != 2){
                val item = bottom_bar.getChildAt(i) as RelativeLayout
                val itemIcon = item.getChildAt(0) as AppCompatImageView
                itemIcon.setImageResource(R.drawable.test_icon_small)
                val itemTitle = item.getChildAt(1) as AppCompatTextView
                itemTitle.setTextColor(Color.GRAY)
            }
        }
    }

    override fun onClick(v: View) {
        val tag = v.tag as Int
        resetColor()
        val item = v as RelativeLayout
        val itemIcon = item.getChildAt(0) as AppCompatImageView
//        itemIcon.setTextColor(mClickedColor)
        itemIcon.setImageResource(R.drawable.test_icon_small_highlight)
        val itemTitle = item.getChildAt(1) as AppCompatTextView
        itemTitle.setTextColor(mClickedColor)
        supportDelegate.showHideFragment(ITEM_DELEGATES[tag], ITEM_DELEGATES[mCurrentDelegate])
        //注意先后顺序
        mCurrentDelegate = tag
    }
}