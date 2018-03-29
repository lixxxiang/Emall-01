package com.example.emall_ec.main.index.dailypic.pic

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.androidkun.xtablayout.XTabLayout
import com.baidu.location.h.j.G
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.R
import com.example.emall_ec.main.index.dailypic.adapter.PicDetailAdapter
import com.example.emall_ec.main.index.dailypic.data.GetDailyPicDetailBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.delegate_pic_detail.*
import kotlinx.android.synthetic.main.pic_detail_1.*
import java.util.*

/**
 * Created by lixiang on 2018/3/29.
 */
class PicDetailDelegate : BottomItemDelegate() {

    private var adapter: PicDetailAdapter? = null
    private var imageId: String? = ""
    var getDailyPicDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getDailyPicDetailBean = GetDailyPicDetailBean()
    var fullScreenImages = arrayListOf<String>()
    var imageCount = 0
    private val titleList = object : ArrayList<String>() {
        init {
            add("每日一图")
            add("发生位置")
        }
    }

    private val fragmentList = object : ArrayList<Fragment>() {
        init {
            add(Page1Delegate())
            add(Page2Delegate())
        }
    }


    fun create(): PicDetailDelegate? {
        return PicDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_pic_detail
    }

    override fun initial() {
        imageId = arguments.getString("imageId")
        getdata(imageId)
        pic_detail_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(pic_detail_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pic_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small)
        pic_detail_toolbar.setNavigationOnClickListener {
            supportDelegate.pop()
        }
        adapter = PicDetailAdapter(activity.supportFragmentManager, titleList, fragmentList)
        viewpager.adapter = adapter
        setTabLayout()

    }

    private fun getdata(id: String?) {
        getDailyPicDetailParams!!["imageId"] = id
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getDailyPicDetail")
                .params(getDailyPicDetailParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        getDailyPicDetailBean = Gson().fromJson(response, GetDailyPicDetailBean::class.java)
                        initViews(getDailyPicDetailBean)
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {

                    }
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {

                    }
                })
                .build()
                .post()
    }

    private fun initViews(dailyPicDetailBean: GetDailyPicDetailBean) {
        if (dailyPicDetailBean.data.richText1 != null)
            description_1.text = dailyPicDetailBean.data.richText1
        if (dailyPicDetailBean.data.richText2 != null)
            description_2.text = dailyPicDetailBean.data.richText2
        if (dailyPicDetailBean.data.richText3 != null)
            description_3.text = dailyPicDetailBean.data.richText3


        if (dailyPicDetailBean.data.image1FilePath != null) {
            fullScreenImages.add(dailyPicDetailBean.data.image1FilePath)
            imageCount++
            picture_1.maxHeight = DimenUtil().getScreenHeight()
            Glide.with(context)
                    .load(dailyPicDetailBean.data.image1FilePath)
                    .into(picture_1)
        }
        if (dailyPicDetailBean.data.image2FilePath.isNotEmpty()) {
            fullScreenImages.add(dailyPicDetailBean.data.image2FilePath)
            imageCount++
            picture_2.maxHeight = DimenUtil().getScreenHeight()
            Glide.with(context)
                    .load(dailyPicDetailBean.data.image2FilePath)
                    .into(picture_2)
        } else {
            picture_2.visibility = View.GONE
        }
        if (dailyPicDetailBean.data.image3FilePath.isNotEmpty()) {
            fullScreenImages.add(dailyPicDetailBean.data.image3FilePath)
            imageCount++
            picture_3.maxHeight = DimenUtil().getScreenHeight()
            Glide.with(context)
                    .load(dailyPicDetailBean.data.image3FilePath)
                    .into(picture_3)
        } else {
            picture_3.visibility = View.GONE
        }
    }

    private fun setTabLayout() {
        xTablayout.setupWithViewPager(viewpager)
        xTablayout!!.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab) {
                viewpager.currentItem = tab.position
                if (tab.position == 0) {
                } else {
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab) {
            }

            override fun onTabReselected(tab: XTabLayout.Tab) {
            }
        })
    }
}