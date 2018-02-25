package com.example.emall_core.app

import android.os.Handler
import com.joanzapata.iconify.IconFontDescriptor
import com.joanzapata.iconify.Iconify
import com.orhanobut.logger.AndroidLogAdapter
//import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import java.util.*
import kotlin.collections.ArrayList




/**
 * Created by lixiang on 2018/1/22.
 */
class Configurator {
    val EMALL_CONFIGS: HashMap<String, Any> = HashMap()
    val ICONS:ArrayList<IconFontDescriptor> = ArrayList()
    val INTERCEPTORS:ArrayList<Interceptor> = ArrayList()
    private val HANDLER = Handler()


    init {
        EMALL_CONFIGS.put(ConfigKeys.CONFIG_READY.toString(), false)
        EMALL_CONFIGS.put(ConfigKeys.HANDLER.toString(), HANDLER)

    }

    private object Holder {
        val INSTANCE = Configurator()
    }

    fun configure() {
        Logger.addLogAdapter(AndroidLogAdapter())
        initIcons()
        EMALL_CONFIGS.put(ConfigKeys.CONFIG_READY.toString(), true)
    }

    fun getInstance(): Configurator {
        return Holder.INSTANCE
    }

    fun getEmallConfigs(): HashMap<String, Any> {
        return EMALL_CONFIGS
    }

    fun withApiHost(host: String): Configurator {
        EMALL_CONFIGS.put(ConfigKeys.API_HOST.toString(), host)
        return this
    }

    fun initIcons(){
        if(ICONS.size > 0){
            val initializer : Iconify.IconifyInitializer = Iconify.with(ICONS[0])
            for (i in 1 until ICONS.size) {
                initializer.with(ICONS[i])
            }
        }
    }

    fun withIcon(descriptor: IconFontDescriptor): Configurator {
        ICONS.add(descriptor)
        return this
    }

    fun withInterceptor(interceptor: Interceptor): Configurator{
        INTERCEPTORS.add(interceptor)
        EMALL_CONFIGS.put(ConfigKeys.INTERCEPTOR.toString(), INTERCEPTORS)
        return this
    }

    fun withInterceptors(interceptors : ArrayList<Interceptor>): Configurator{
        INTERCEPTORS.addAll(interceptors)
        EMALL_CONFIGS.put(ConfigKeys.INTERCEPTOR.toString(), INTERCEPTORS)
        return this
    }


    fun checkConfiguration() {
        val isReady = EMALL_CONFIGS[ConfigKeys.CONFIG_READY.toString()] as Boolean
        if (!isReady) {
            throw RuntimeException("Configuration is not ready,call configure")
        }
    }

    fun <T> getConfiguration(key: Any): T? {
        checkConfiguration()
        return EMALL_CONFIGS[key] as T?
    }
}