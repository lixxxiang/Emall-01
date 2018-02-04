package com.example.emall_core.util.storage

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alibaba.fastjson.JSON
import com.example.emall_core.app.Emall

/**
 * Created by lixiang on 2018/2/2.
 */
class EmallPreference  {
    private val PREFERENCES = PreferenceManager.getDefaultSharedPreferences(Emall().getApplicationContextName())
    private val APP_PREFERENCES_KEY = "profile"

    private fun getAppPreference(): SharedPreferences {
        return PREFERENCES
    }

    fun setAppProfile(`val`: String) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCES_KEY, `val`)
                .apply()
    }

    fun getAppProfile(): String? {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null)
    }

    fun getAppProfileJson(): com.alibaba.fastjson.JSONObject? {
        val profile = getAppProfile()
        return JSON.parseObject(profile)
    }

    fun removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCES_KEY)
                .apply()
    }

    fun clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply()
    }

    fun setAppFlag(key: String, flag: Boolean) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply()
    }

    fun getAppFlag(key: String): Boolean {
        return getAppPreference()
                .getBoolean(key, false)
    }

    fun addCustomAppProfile(key: String, `val`: String) {
        getAppPreference()
                .edit()
                .putString(key, `val`)
                .apply()
    }

    fun getCustomAppProfile(key: String): String {
        return getAppPreference().getString(key, "")
    }
}