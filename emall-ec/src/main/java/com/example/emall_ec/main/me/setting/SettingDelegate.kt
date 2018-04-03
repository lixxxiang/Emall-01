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
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatTextView
import android.widget.TextView
import com.example.emall_ec.main.program.adapter.ProgramParamsTypeAdapter


/**
 * Created by lixiang on 2018/4/3.
 */
class SettingDelegate : BottomItemDelegate() {

    var titleList: MutableList<Int>? = mutableListOf()
    var index = 0
    fun create(): SettingDelegate? {
        return SettingDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_setting
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setting_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(setting_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        titleList!!.add(R.string.account_privacy_setting)
        titleList!!.add(R.string.clear_cache)
        titleList!!.add(R.string.no_wifi_play_noti)

        val adapter = SettingAdapter(context, titleList, index)
        setting_lv.adapter = adapter

        setting_lv.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 2) {
                val items = arrayOf("每次提醒", "提醒一次")
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("非WiFi网络播放提醒")
                        .setSingleChoiceItems(items, index, DialogInterface.OnClickListener { dialog, which ->
                            index = which
                        })
                builder.setPositiveButton("确定") { dialog, which ->
                    val tv = setting_lv.getChildAt(2).findViewById<AppCompatTextView>(R.id.detail_tv)
                    tv.text = items[index]
                    dialog.dismiss()

                }

                builder.setNegativeButton("取消"){dialog, which ->
                    dialog.dismiss()
                }

                builder.create().show()

            }
        }

    }
}