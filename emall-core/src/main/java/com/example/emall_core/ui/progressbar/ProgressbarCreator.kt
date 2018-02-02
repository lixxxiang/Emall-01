package com.example.emall_core.ui.progressbar

import android.content.Context
import android.widget.ProgressBar

/**
 * Created by lixiang on 2018/1/30.
 */
class ProgressbarCreator {

    fun creator(context: Context): ProgressBar {
        var progressbar = ProgressBar(context)
        return progressbar
    }
}