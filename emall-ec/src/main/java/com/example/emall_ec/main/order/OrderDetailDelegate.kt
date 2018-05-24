package com.example.emall_ec.main.order

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.emall_ec.main.bottom.BottomItemDelegate
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.api.ApiService
import com.example.emall_ec.api.NetUtils
import com.example.emall_ec.main.demand.data.FindOrderDetailByOrderIdBean
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.example.emall_ec.main.order.state.adapter.AllListAdapter
import com.example.emall_ec.main.order.state.adapter.AllListAdapter.programArray
import com.example.emall_ec.main.order.state.data.OrderDetail
import com.example.emall_ec.main.scanner.ScannerDelegate
import kotlinx.android.synthetic.main.delegate_order_detail.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import retrofit2.Retrofit
import java.text.DecimalFormat
import java.util.*


/**
 * Created by lixiang on 2018/3/6.
 */
class OrderDetailDelegate : BottomItemDelegate() {

    var deleteOrderParams: WeakHashMap<String, Any>? = WeakHashMap()
    var deleteOrderBean = CommonBean()
    var orderId = String()
    var flag = false
    internal var retrofit: Retrofit? = null
    var mSharedPreferences: SharedPreferences? = null
    internal var apiService: ApiService? = null
    fun create(): OrderDetailDelegate? {
        return OrderDetailDelegate()
    }

    override fun initial() {
        setSwipeBackEnable(false)
        mSharedPreferences = activity.getSharedPreferences("BACK_FROM", Context.MODE_PRIVATE)
        order_detail_tel_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/tel.ttf")
        order_detail_qq_tv.typeface = Typeface.createFromAsset(activity.assets, "iconfont/qq.ttf")
        order_detail_list_toolbar.title = getString(R.string.order_detail)
        (activity as AppCompatActivity).setSupportActionBar(order_detail_list_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (arguments.getString("FROM") != "PAYMENT") {
            val orderData = arguments.getParcelable<OrderDetail>("KEy")
            val index = arguments.getInt("INDEX")
            orderId = orderData.data[index].orderId
            initViews(orderData, index)
        } else {
            val orderData = arguments.getParcelable<FindOrderDetailByOrderIdBean>("KEy")
            initViews(orderData)
            orderId = orderData.data.orderId
        }

        EmallLogger.d(orderId)

        order_detail_list_toolbar.setNavigationOnClickListener {
            val editor = mSharedPreferences!!.edit()
            editor.putString("BACK_FROM", "ORDER_DETAIL")
            editor.commit()
            pop()
        }

        order_detail_delete_btn.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("确认删除订单？")
            builder.setPositiveButton(getString(R.string.confirm_2)) { dialog, _ ->
                EmallLogger.d(orderId)
                deleteOrderParams!!["orderId"] = orderId
                retrofit = NetUtils.getRetrofit()
                apiService = retrofit!!.create(ApiService::class.java)
                val call = apiService!!.deleteOrder(orderId)
                call.enqueue(object : retrofit2.Callback<CommonBean> {
                    override fun onResponse(call: retrofit2.Call<CommonBean>, response: retrofit2.Response<CommonBean>) {
                        if (response.body() != null) {
                            EmallLogger.d(response.body()!!)
                            deleteOrderBean = response.body()!!
                            if (deleteOrderBean.message == "success") {
                                val editor = mSharedPreferences!!.edit()
                                editor.putString("BACK_FROM", "ORDER_DETAIL_DELETE")
                                editor.commit()
                                pop()
                            }
                        } else {
                            EmallLogger.d("error")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<CommonBean>, throwable: Throwable) {}
                })
                dialog.dismiss()
            }

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()

        }

        order_detail_download_btn.setOnClickListener {
            val delegate: ProductDeliveryDelegate = ProductDeliveryDelegate().create()!!
            val bundle: Bundle? = Bundle()
            bundle!!.putString("PAGE_FROM", "ORDER_LIST")
            delegate.arguments = bundle
            start(delegate)
        }

        order_detail_tel_rl.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("10010")
            builder.setPositiveButton(getString(R.string.call)) { dialog, _ ->

                if (flag) {
                    val intent = Intent(Intent.ACTION_CALL)
                    val data = Uri.parse("tel:" + "10010")
                    intent.data = data
                    startActivity(intent)
                } else
                    handlePermisson()

                dialog.dismiss()

            }

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        order_detail_qq_rl.setOnClickListener {
            if (isQQClientAvailable(activity)) {
                // 跳转到客服的QQ
                val url = "mqqwpa://im/chat?chat_type=wpa&uin=1548806494"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
                if (isValidIntent(activity, intent)) {
                    startActivity(intent)
                }
            }
        }
    }

    private fun initViews(orderData: FindOrderDetailByOrderIdBean) {

        if (orderData.data.state != 4) {
            order_detail_download_btn.visibility = View.GONE
        }
        Glide.with(context)
                .load(orderData.data.details.imageDetailUrl)
                .into(order_detail_image_iv)

        order_detail_title_tv.text = AllListAdapter.typeArray[orderData.data.type]
        order_detail_time_tv.text = AllListAdapter.timeFormat(orderData.data.details.centerTime)
        EmallLogger.d(DecimalFormat("######0.00").format(orderData.data.payment))
        order_detail_price_tv.text = String.format("¥%f", DecimalFormat("######0.00").format(orderData.data.payment))

        order_detail_state_tv.text = stateFormat(orderData.data.state, orderData.data.planCommitTime)


        order_detail_id_tv.text = orderData.data.orderId
        println(orderData.data.orderId)
        order_detail_order_time_tv.text = orderData.data.commitTime
        EmallLogger.d(orderData.data.payMethod)
        order_detail_pay_method_tv.text = AllListAdapter.payMethodArray[orderData.data.payMethod]
        order_detail_origional_price_tv.text = String.format("¥%s", orderData.data.details.originalPrice)
        order_detail_current_price_tv.text = String.format("¥%s", orderData.data.details.salePrice)
        order_detail_final_price_tv.text = String.format("¥%s", orderData.data.payment)
        order_detail_discount_tv.text = discount(orderData.data.details.salePrice, orderData.data.payment)
    }

    private fun initViews(orderData: OrderDetail, index: Int) {
        if (orderData.data[index].state != 4) {
            order_detail_download_btn.visibility = View.GONE
        }
        if (orderData.data[index].details.imageDetailUrl == null) {
            order_detail_image_iv.setBackgroundResource(R.drawable.program)
        } else
            Glide.with(context)
                    .load(orderData.data[index].details.imageDetailUrl)
                    .into(order_detail_image_iv)

        order_detail_title_tv.text = AllListAdapter.typeArray[orderData.data[index].type]

        if (orderData.data[index].type != 2) {
            if (orderData.data[index].details.centerTime == null)
                order_detail_time_tv.text = AllListAdapter.timeFormat(orderData.data.get(index).details.startTime)
            else
                order_detail_time_tv.text = AllListAdapter.timeFormat(orderData.data.get(index).details.centerTime)
        } else {
            order_detail_time_tv.text = "类型：" + programArray[Integer.parseInt(orderData.data.get(index).details.productType)]
        }


        order_detail_price_tv.text = String.format("¥%s", DecimalFormat("######0.00").format(orderData.data.get(index).payment))
//        if (orderData.data[index].state == 4) {
        order_detail_state_tv.text = stateFormat(orderData.data[index].state, orderData.data[index].planCommitTime)
//        } else
//            order_detail_state_tv.text = stateArray[orderData.data[index].state]


        order_detail_id_tv.text = orderData.data[index].orderId
        println(orderData.data[index].orderId)
        order_detail_order_time_tv.text = orderData.data[index].commitTime
        EmallLogger.d(orderData.data[index].payMethod)

        order_detail_pay_method_tv.text = AllListAdapter.payMethodArray[orderData.data[index].payMethod]
        order_detail_origional_price_tv.text = String.format("¥%s", orderData.data[index].details.originalPrice)
        order_detail_current_price_tv.text = String.format("¥%s", orderData.data[index].details.salePrice)
        order_detail_final_price_tv.text = String.format("¥%s", DecimalFormat("######0.00").format(orderData.data[index].payment))
        order_detail_discount_tv.text = discount(orderData.data[index].details.originalPrice as String, orderData.data[index].payment)
    }

    private fun discount(salePrice: String, payment: Double): String {
        return String.format("-¥%s", DecimalFormat("######0.00").format(salePrice.toDouble() - payment))
    }

    private fun stateFormat(state: Int, planCommitTime: String): String {
        return if (state == 3) {
            String.format("%s：预计 %s 交付", AllListAdapter.stateArray[state], planCommitTime)
        } else {
            String.format("%s", AllListAdapter.stateArray[state])
        }
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_order_detail
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun handlePermisson() {

        // 需要动态申请的权限
        val permission = Manifest.permission.CALL_PHONE

        //查看是否已有权限
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)

        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取到权限  获取用户媒体资源

        } else {

            //没有拿到权限  是否需要在第二次请求权限的情况下
            // 先自定义弹框说明 同意后在请求系统权限(就是是否需要自定义DialogActivity)
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

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

            } else {
                myRequestPermission()
            }
        }
    }

    private fun myRequestPermission() {
        //可以添加多个权限申请
        val permissions = arrayOf(Manifest.permission.CALL_PHONE)
        requestPermissions(permissions, 1)
    }

    /***
     * 权限请求结果  在Activity 重新这个方法 得到获取权限的结果  可以返回多个结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //是否获取到权限
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            TODO("你要实现的业务逻辑")
            flag = true
            val intent = Intent(Intent.ACTION_CALL)
            val data = Uri.parse("tel:" + "10010")
            intent.data = data
            startActivity(intent)

        }
    }

    fun isQQClientAvailable(context: Context): Boolean {
        val packageManager = context.getPackageManager()
        val pinfo = packageManager.getInstalledPackages(0)
        if (pinfo != null) {
            for (i in pinfo!!.indices) {
                val pn = pinfo!!.get(i).packageName
                if (pn.equals("com.tencent.qqlite", ignoreCase = true) || pn.equals("com.tencent.mobileqq", ignoreCase = true)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断 Uri是否有效
     */
    fun isValidIntent(context: Context, intent: Intent): Boolean {
        val packageManager = context.getPackageManager()
        val activities = packageManager.queryIntentActivities(intent, 0)
        return !activities.isEmpty()
    }

    override fun onBackPressedSupport(): Boolean {
        val editor = mSharedPreferences!!.edit()
        editor.putString("BACK_FROM", "ORDER_DETAIL")
        editor.commit()
        pop()
        return true
    }
}