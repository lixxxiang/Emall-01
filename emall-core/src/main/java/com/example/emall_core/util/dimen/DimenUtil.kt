package com.example.emall_core.util.dimen

import android.content.res.Resources
import android.util.DisplayMetrics
import com.example.emall_core.app.Emall

/**
 * Created by lixiang on 2018/1/30.
 */
class DimenUtil {
    fun getScreenWidth(): Int {
        val resources: Resources = Emall().getApplication().resources
        val dm: DisplayMetrics = resources.displayMetrics
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val resources: Resources = Emall().getApplication().resources
        val dm: DisplayMetrics = resources.displayMetrics
        return dm.heightPixels
    }
}