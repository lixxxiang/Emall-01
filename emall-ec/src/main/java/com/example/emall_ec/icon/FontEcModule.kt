package com.example.emall.ec.icon

import com.joanzapata.iconify.Icon
import com.joanzapata.iconify.IconFontDescriptor

/**
 * Created by 傅令杰 on 2017/3/29
 */

class FontEcModule : IconFontDescriptor {
    override fun ttfFileName(): String {
        return "iconfont.ttf"
    }

    override fun characters(): Array<EcIcons> {
        return EcIcons.values()
    }
}
