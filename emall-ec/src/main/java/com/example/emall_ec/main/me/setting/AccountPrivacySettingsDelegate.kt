package com.example.emall_ec.main.me.setting

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.me.setting.adapter.AccountPrivacySettingsAdapter
import com.example.emall_ec.main.me.setting.adapter.SettingAdapter
import kotlinx.android.synthetic.main.delegate_account_privacy_settings.*
import kotlinx.android.synthetic.main.delegate_setting.*

/**
 * Created by lixiang on 2018/4/3.
 */
class AccountPrivacySettingsDelegate : EmallDelegate() {

    var titleList: MutableList<Int>? = mutableListOf()
    var index = 0
    var tel = String()

    fun create(): AccountPrivacySettingsDelegate? {
        return AccountPrivacySettingsDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_account_privacy_settings
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        account_privacy_settings_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(account_privacy_settings_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tel = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userTelephone

        account_privacy_settings_toolbar.setNavigationOnClickListener {
            pop()
        }
        titleList!!.add(R.string.modify_tel)
        titleList!!.add(R.string.modify_pwd)

        val adapter = AccountPrivacySettingsAdapter(titleList, context, index, String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11)))
        account_privacy_settings_lv.adapter = adapter


        account_privacy_settings_lv.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {
                start(ModifyTelDelegate().create())
            }
        }
    }
}