package com.example.emall.ec.icon


import com.joanzapata.iconify.Icon

/**
 * Created by 傅令杰 on 2017/3/29
 */
enum class EcIcons private constructor(private val character: Char) : Icon {
    icon_test('\ue74a');

    override fun key(): String {
        return name.replace('_', '-')
    }

    override fun character(): Char {
        return character
    }
}

