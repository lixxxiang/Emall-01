package com.example.emall_core.net.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.view.View
import com.bigkoo.convenientbanner.holder.Holder

/**
 * Created by lixiang on 02/02/2018.
 */
class LauncherHolder : Holder<Int> {

    private var mImageView : AppCompatImageView? = null
    override fun UpdateUI(context: Context?, position: Int, data: Int?) {
        mImageView!!.setBackgroundResource(data!!)
    }

    override fun createView(context: Context?): View {
        mImageView = AppCompatImageView(context)
        return mImageView as AppCompatImageView
    }
}