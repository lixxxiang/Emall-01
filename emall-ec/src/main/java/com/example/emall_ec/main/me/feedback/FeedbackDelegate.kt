package com.example.emall_ec.main.me.feedback

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.order.Find_tab_Adapter
import com.example.emall_ec.main.sign.SignHandler
import com.example.emall_ec.main.sign.data.CheckMessageBean
import com.example.emall_ec.main.sign.data.CommonBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_feedback.*
import kotlinx.android.synthetic.main.delegate_my_opinion.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

class FeedbackDelegate : EmallDelegate() {
    var listTitle: MutableList<String>? = mutableListOf()
    var listFragment: MutableList<Fragment>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null
    private var supportParams: WeakHashMap<String, Any> ?= WeakHashMap()
    var commonBean = CommonBean()
    var toast: Toast ?= null
    fun create(): FeedbackDelegate? {
        return FeedbackDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_feedback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);//加上这句话，menu才会显示出来
    }

    override fun initial() {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        feedback_toolbar.title = getString(R.string.feedback)
        (activity as AppCompatActivity).setSupportActionBar(feedback_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        feedback_toolbar.setNavigationOnClickListener {
            pop()
        }
        feedback_toolbar.inflateMenu(R.menu.feedback_menu)
        feedback_toolbar.setOnMenuItemClickListener { item ->
            when(item!!.itemId){
                R.id.menu_edit -> {
                    KeyboardUtils.hideSoftInput(activity)
                    EmallLogger.d(activity.getSharedPreferences("OPINION", Context.MODE_PRIVATE).getString("opinion",""))
                    support(activity.getSharedPreferences("OPINION", Context.MODE_PRIVATE).getString("opinion",""))
                }
            }
            true
        }
    }

    private fun support(string: String) {
        supportParams!!["content"] = string
        supportParams!!["userId"] = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId

        RestClient().builder()
                .url("http://59.110.161.48:8023/support.do")
                .params(supportParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.meta == "success") {
                            /**
                             * success
                             */

                            val snackBar = Snackbar.make(view!!, "提交成功", Snackbar.LENGTH_SHORT)
                            snackBar.show()
                            my_opinion_edt.setText("")

                        } else {
                            if (toast != null) {
                                toast!!.setText(getString(R.string.wrong_vcode))
                                toast!!.duration = Toast.LENGTH_SHORT
                                toast!!.show()
                            } else {
                                toast = Toast.makeText(activity, getString(R.string.wrong_vcode), Toast.LENGTH_SHORT)
                                toast!!.show()
                            }
                        }
                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .build()
                .post()
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        initControls()

    }

    private fun initControls() {

        val myOpinionDelegate = MyOpinionDelegate()
        val commonProblemDelegate = CommonProblemDelegate()
        listFragment!!.add(myOpinionDelegate)
        listFragment!!.add(commonProblemDelegate)

        listTitle!!.add(getString(R.string.my_opinion))
        listTitle!!.add(getString(R.string.common_question))

        feedback_tl.tabMode = TabLayout.MODE_FIXED
        feedback_tl.addTab(feedback_tl.newTab().setText(listTitle!![0]))
        feedback_tl.addTab(feedback_tl.newTab().setText(listTitle!![1]))

        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        feedback_vp.adapter = fAdapter
        feedback_tl.setupWithViewPager(feedback_vp)
        feedback_vp.offscreenPageLimit = 2
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity.menuInflater.inflate(R.menu.feedback_menu, menu)

    }
}