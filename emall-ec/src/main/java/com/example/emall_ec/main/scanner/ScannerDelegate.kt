package com.example.emall_ec.main.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_scanner.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by lixiang on 2018/3/22.
 */

class ScannerDelegate : BottomItemDelegate() {

    private var uuid = String()
    private var flag = false
    fun create(): ScannerDelegate?{
        return ScannerDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_scanner
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        Handler().post({
            scannerView.visibility = View.VISIBLE
        })
        handlePermisson()
        scannerView.setResultHandler(mResultHandler)
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        flag = true
        scan_toolbar.title = getString(R.string.scan_loggin)
        (activity as AppCompatActivity).setSupportActionBar(scan_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        scan_toolbar.setNavigationOnClickListener {
            pop()
        }

    }

    private val mResultHandler = object : ZXingScannerView.ResultHandler {
        override fun handleResult(result: Result) {
            scannerView.resumeCameraPreview(this)
            uuid = getUuid(result.text)
            if (!uuid.isEmpty() && flag){
                flag = false
                val delegate: ConfirmLoginDelegate = ConfirmLoginDelegate().create()!!
                val bundle: Bundle? = Bundle()
                bundle!!.putString("UUID", uuid)
                delegate.arguments = bundle
                start(delegate)
            }
        }
    }

    private fun getUuid(text: String): String {
        val temp = text.split("uuid\":\"")[1]
        return temp.substring(0, temp.length - 2)
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(mResultHandler)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    fun handlePermisson(){
        val permission = Manifest.permission.CAMERA
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context,permission)
        if (checkSelfPermission  == PackageManager.PERMISSION_GRANTED) {
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
            }else{
                myRequestPermission()
            }
        }
    }

    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        requestPermissions(permissions,1)
    }

    /***
     * 权限请求结果  在Activity 重新这个方法 得到获取权限的结果  可以返回多个结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        flag = true
    }

}