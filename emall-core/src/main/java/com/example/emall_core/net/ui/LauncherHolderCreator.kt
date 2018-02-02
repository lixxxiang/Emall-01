package com.example.emall_core.net.ui

import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator

/**
 * Created by lixiang on 02/02/2018.
 */
class LauncherHolderCreator : CBViewHolderCreator<LauncherHolder> {
    override fun createHolder(): LauncherHolder {
        return LauncherHolder()
    }
}