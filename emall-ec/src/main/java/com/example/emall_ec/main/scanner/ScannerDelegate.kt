package com.example.emall_ec.main.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_ec.R
import kotlinx.android.synthetic.main.delegate_scanner.*
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView



/**
 * Created by lixiang on 2018/3/22.
 */

class ScannerDelegate : BottomItemDelegate() {

    fun create(): ScannerDelegate?{
        return ScannerDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_scanner
    }
    override fun initial() {
        handlePermisson()
        scannerView.setResultHandler(mResultHandler)
    }

    private val mResultHandler = object : ZXingScannerView.ResultHandler {
        override fun handleResult(result: Result) {
            scannerView.resumeCameraPreview(this) //重新进入扫描二维码
            Toast.makeText(context, "内容=" + result.text + ",格式=" + result.barcodeFormat.toString(), Toast.LENGTH_SHORT).show()
        }
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

        // 需要动态申请的权限
        val permission = Manifest.permission.CAMERA

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
        val permissions = arrayOf(Manifest.permission.CAMERA)
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

        }
    }


}