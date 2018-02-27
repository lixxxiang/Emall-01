//package com.example.emall_core.ui.progressbar
//
//import android.content.Context
//import android.support.v7.app.AppCompatDialog
//import android.view.Gravity
//import com.example.emall_core.R
//import com.example.emall_core.util.dimen.DimenUtil
//import com.example.emall_core.util.log.EmallLogger
//
///**
// * Created by lixiang on 2018/1/30.
// */
//class EmallProgressbar {
//    val LOADER_SIZE_SCALE = 8
//    private val LOADERS = mutableListOf<AppCompatDialog>()
//    var dialog: AppCompatDialog? = null
//    fun showProgressbar(context: Context){
//        val progressbar = ProgressbarCreator().creator(context)
//        dialog = AppCompatDialog(context, R.style.dialog)
//        dialog!!.setContentView(progressbar)
//
//        val deviceWidth = DimenUtil().getScreenWidth()
//        val deviceHeight = DimenUtil().getScreenHeight()
//
//        val dialogWindow = dialog!!.window
//        if (dialogWindow != null) {
//            val lp = dialogWindow.attributes
//            lp.width = deviceWidth / LOADER_SIZE_SCALE
//            lp.height = deviceHeight / LOADER_SIZE_SCALE
//            lp.gravity = Gravity.CENTER
//        }
//
//        LOADERS.add(dialog!!)
//        EmallLogger.d("in show" + LOADERS)
//        dialog!!.show()
//    }
//
//    fun hideProgressbar(){
//        EmallLogger.d("in hide" + LOADERS)
//
//        dialog!!.hide()
////        for (dialog in LOADERS) {
////                if (dialog.isShowing) {
////                    dialog.cancel()
////            dialog.hide()
////                }
////        }
//    }
//}