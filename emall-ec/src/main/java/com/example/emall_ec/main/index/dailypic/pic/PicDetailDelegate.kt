package com.example.emall_ec.main.index.dailypic.pic

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.androidkun.xtablayout.XTabLayout
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.bottom.BottomItemDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_ec.R
import com.example.emall_ec.main.index.dailypic.adapter.CommentListViewAdapter
import com.example.emall_ec.main.index.dailypic.adapter.PicDetailAdapter
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.example.emall_ec.main.index.dailypic.data.GetArticleAttachBean
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
    var userParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getDailyPicDetailBean = GetDailyPicDetailBean()
    var getArticleAttachBean = GetArticleAttachBean()
    var commonBean = CommonBean()
    var fullScreenImages = arrayListOf<String>()
    var imageCount: Int = 0
    var upVoteAmount: Int = 0
    var comment_adapter: CommentListViewAdapter? = null
    var isLiked = false
    var isCollected = false
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

        like.setOnClickListener {
            //            if (sp.getString("userId", null) != null) {
            isLiked = if (!isLiked) {
                like.setImageResource(R.drawable.like_highlight)
                upvote()
                true
            } else {
                like.setImageResource(R.drawable.like)
                downvote()
                false
            }
//            } else {
//                tapToLike = 1
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
        }

        collect.setOnClickListener {
            //            if (sp.getString("userId", null) != null) {
            isCollected = if (!isCollected) {
                addCollection()
                true
            } else {
                cancelCollection()
                false
            }
//            } else {
//                tapToCollect = 1
//                startActivity(Intent(this, LoginActivity::class.java))
//            }
        }
    }

    private fun addCollection() {
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/addCollection")
                .params(userParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            collect.setImageResource(R.drawable.collection_highlight)
                            Toast.makeText(activity, "已收藏", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .build()
                .post()
    }

    private fun cancelCollection() {
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/cancelCollection")
                .params(userParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            collect.setImageResource(R.drawable.collection)
                            Toast.makeText(activity, "已取消", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .build()
                .post()
    }


    private fun upvote() {

        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/upvote")
                .params(userParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            like.setImageResource(R.drawable.like_highlight)
                            like_count.text = (upVoteAmount + 1).toString()
                            isLiked = true
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .build()
                .post()
    }

    private fun downvote() {
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/downvote")
                .params(userParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success")
                            like_count.text = (upVoteAmount).toString()
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .build()
                .post()
    }


    private fun getdata(id: String?) {
        getDailyPicDetailParams!!["imageId"] = id
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getDailyPicDetail")
                .params(getDailyPicDetailParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        getDailyPicDetailBean = Gson().fromJson(response, GetDailyPicDetailBean::class.java)
                        /**
                         * test
                         */
                        userParams!!["articleId"] = imageId
                        userParams!!["userId"] = "22"
                        userParams!!["type"] = "1"


                        initViews(getDailyPicDetailBean)
                        initComments()
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
                })
                .build()
                .post()
    }


//    fun getData(): GetDailyPicDetailBean.DataBean? = getDailyPicDetailBean.data

    fun getData(): GetDailyPicDetailBean.DataBean? {
        if (getDailyPicDetailBean.data != null)
            return getDailyPicDetailBean.data
        else return null
    }

    fun initComments() {


        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getArticleAttach")
                .params(userParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        getArticleAttachBean = Gson().fromJson(response, GetArticleAttachBean::class.java)
                        if (getArticleAttachBean.data.comments != null) {
                            comment_adapter = CommentListViewAdapter(activity.applicationContext, getArticleAttachBean.data)
                            comment_listview.adapter = comment_adapter
                            if (comment_listview.headerViewsCount == 0) {
                                val headerView = layoutInflater.inflate(R.layout.comment_header, null)
                                comment_listview.addHeaderView(headerView)
                            }

                            comment_listview.dividerHeight = 0
                            comment_adapter!!.notifyDataSetChanged()
                            if (noComment.visibility == View.VISIBLE)
                                noComment.visibility = View.GONE
                        } else {
                            /**
                             * no comments
                             */
                            noComment.visibility = View.VISIBLE
                        }

                        like_count.text = getArticleAttachBean.data!!.upvoteAmount.toString()
                        comments.setMessageCount(getArticleAttachBean.data!!.commentAmount)
                        upVoteAmount = getArticleAttachBean.data!!.upvoteAmount
                        if (getArticleAttachBean.data!!.upvoteMark == 1) {
                            like.setImageResource(R.drawable.like_highlight)
                            isLiked = true
                            upVoteAmount--
                        }
                        if (getArticleAttachBean.data!!.collectionMark == 1) {
                            collect.setImageResource(R.drawable.collection_highlight)
//                            isCollected = true
                        }
                    }
                })
                .failure(object : IFailure {
                    override fun onFailure() {}
                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {}
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