package com.example.emall_ec.main.me

import android.Manifest
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import kotlinx.android.synthetic.main.delegate_contact.*
import kotlinx.android.synthetic.main.delegate_me.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.widget.Toast


/**
 * Created by lixiang on 2018/4/12.
 */
class ContactDelegate : EmallDelegate() {

    var flag = false
    fun create(): ContactDelegate? {
        return ContactDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_contact
    }

    override fun initial() {
        contact_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(contact_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        contact_toolbar.setNavigationIcon(R.drawable.ic_back_small)

        if (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(contact_ll) > 0) {
            val rl = RelativeLayout(activity)
            val rlParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (DimenUtil().getScreenHeight() - SizeUtils.getMeasuredHeight(contact_ll) + DimenUtil().dip2px(context, 54F)))
            rl.layoutParams = rlParams
            rl.setBackgroundColor(Color.parseColor("#FFFFFF"))
            contact_ll.addView(rl, rlParams)
        }

        contact_tel_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/tel.ttf")
        contact_qq_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/qq.ttf")

        contact_toolbar.setNavigationOnClickListener {
            pop()
        }

        contact_tel.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("10010")
            builder.setPositiveButton(getString(R.string.call)) { dialog, _ ->

                if (flag){
                    val intent = Intent(Intent.ACTION_CALL)
                    val data = Uri.parse("tel:" + "10010")
                    intent.data = data
                    startActivity(intent)
                }else
                    handlePermisson()

                dialog.dismiss()

            }

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        contact_qq.setOnClickListener {
            Toast.makeText(activity, "TO DO", Toast.LENGTH_SHORT).show()
//            val url = "mqqwpa://im/chat?chat_type=wpa&uin=123456"
//            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

    }

    fun handlePermisson(){

        // 需要动态申请的权限
        val permission = Manifest.permission.CALL_PHONE

        //查看是否已有权限
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context,permission)

        if (checkSelfPermission  == PackageManager.PERMISSION_GRANTED) {
            //已经获取到权限  获取用户媒体资源

        }else{

            //没有拿到权限  是否需要在第二次请求权限的情况下
            // 先自定义弹框说明 同意后在请求系统权限(就是是否需要自定义DialogActivity)
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){

//                ("安卓就是流氓的获取了你的私人信息","温馨提示"){
//
//                    yesButton {
//                        // 点击同意 请求真的权限
//                        myRequestPermission()
//                    }
//
//                    noButton {
//                        //可以回退到上一个界面 也可以不做任何处理
//                    }
//                }.show()

            }else{
                myRequestPermission()
            }
        }
    }

    private fun myRequestPermission() {
        //可以添加多个权限申请
        val permissions = arrayOf(Manifest.permission.CALL_PHONE)
        requestPermissions(permissions,1)
    }

    /***
     * 权限请求结果  在Activity 重新这个方法 得到获取权限的结果  可以返回多个结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //是否获取到权限
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            TODO("你要实现的业务逻辑")
            flag = true
            val intent = Intent(Intent.ACTION_CALL)
            val data = Uri.parse("tel:" + "10010")
            intent.data = data
            startActivity(intent)

        }
    }
}