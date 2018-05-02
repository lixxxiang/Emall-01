package com.example.emall_ec.main.me.setting

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import com.example.emall_ec.main.me.setting.adapter.SettingAdapter
import kotlinx.android.synthetic.main.delegate_invoice.*
import kotlinx.android.synthetic.main.delegate_setting.*
import android.widget.Toast
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatTextView
import android.widget.TextView
import com.example.emall_core.util.view.CacheUtil
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.program.adapter.ProgramParamsTypeAdapter
import com.example.emall_ec.main.sign.ModifyPasswordDelegate
import com.example.emall_ec.main.sign.SignInByTelDelegate
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_me.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 2018/4/3.
 */
class SettingDelegate : BottomItemDelegate() {

    var titleList: MutableList<Int>? = mutableListOf()
    var index = 0
    var tel = String()
    fun create(): SettingDelegate? {
        return SettingDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_setting
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setting_toolbar.title = getString(R.string.setting)
        (activity as AppCompatActivity).setSupportActionBar(setting_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setting_toolbar.setNavigationOnClickListener {

            pop()
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        titleList!!.add(R.string.account_privacy_setting)
        titleList!!.add(R.string.clear_cache)
        titleList!!.add(R.string.no_wifi_play_noti)

        val adapter = SettingAdapter(context, titleList, index)
        setting_lv.adapter = adapter

        setting_lv.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 2) {
                val items = arrayOf(getString(R.string.alert_every_time), getString(R.string.alert_once))
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(getString(R.string.no_wifi_play_noti))
                        .setSingleChoiceItems(items, index, { _, which ->
                            index = which
                        })
                builder.setPositiveButton(getString(R.string.confirm_2)) { dialog, _ ->
                    val tv = setting_lv.getChildAt(2).findViewById<AppCompatTextView>(R.id.detail_tv)
                    tv.text = items[index]
                    dialog.dismiss()
                }

                builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }

                builder.create().show()
            } else if (i == 0) {
                if (!DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
                    start(AccountPrivacySettingsDelegate().create())
                } else {
                    val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                    val bundle = Bundle()
                    bundle.putString("PAGE_FROM", "SETTING")
                    delegate.arguments = bundle
                    start(delegate)
                }
            } else if (i == 1) {
                CacheUtil.clearAllCache(context)
                setting_lv.getChildAt(1).findViewById<AppCompatTextView>(R.id.detail_tv).text = getString(R.string.no_cache)
                Toast.makeText(activity, "清理成功", Toast.LENGTH_SHORT).show()
            }
        }

        setting_log_out.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("确认登出吗？")
            builder.setPositiveButton(getString(R.string.confirm_2)) { dialog, _ ->
                DatabaseManager().getInstance()!!.getDao()!!.deleteAll()
                dialog.dismiss()
            }

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}