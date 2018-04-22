package com.example.emall_ec.main.index.dailypic.video

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.androidkun.xtablayout.XTabLayout
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.index.dailypic.adapter.CommentListViewAdapter
import com.example.emall_ec.main.index.dailypic.adapter.DetailAdapter
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.example.emall_ec.main.index.dailypic.data.GetArticleAttachBean
import com.example.emall_ec.main.index.dailypic.data.GetDailyPicDetailBean
import com.example.emall_ec.main.index.dailypic.data.GetPlanetEarthDetailBean
import com.example.emall_ec.main.index.dailypic.pic.ImagePage1Delegate
import com.example.emall_ec.main.index.dailypic.pic.ImagePage2Delegate
import com.example.emall_ec.main.sign.SignInByTelDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.comment.view.*
import kotlinx.android.synthetic.main.delegate_pic_detail.*
import kotlinx.android.synthetic.main.delegate_video_detail.*
import kotlinx.android.synthetic.main.pic_detail_1.*
import kotlinx.android.synthetic.main.video_detail_1.*
import kotlinx.android.synthetic.main.video_detail_2.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.apache.cordova.*
import org.apache.cordova.engine.SystemWebViewEngine
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class VideoDetailDelegate : EmallDelegate(), CordovaInterface {

    var cordovaWebView: CordovaWebView? = null
    private val threadPool = Executors.newCachedThreadPool()
    protected var activityResultRequestCode: Int = 0
    protected var prefs = CordovaPreferences()
    protected var pluginEntries: ArrayList<PluginEntry>? = null
    protected var activityResultCallback1: CordovaPlugin? = null
    var mSharedPreferences: SharedPreferences? = null
    var getPlanetEarthDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getArticleAttachParams: WeakHashMap<String, Any>? = WeakHashMap()
    var cancelCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var addCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var downvoteParams: WeakHashMap<String, Any>? = WeakHashMap()
    var upvoteParams: WeakHashMap<String, Any>? = WeakHashMap()
    var submitCommentParams: WeakHashMap<String, Any>? = WeakHashMap()
    var userParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getArticleAttachBean = GetArticleAttachBean()
    var upVoteAmount: Int = 0
    var comment_adapter: CommentListViewAdapter? = null
    var isLiked = false
    var isCollected = false
    var commonBean = CommonBean()
    var videoId = String()
    var isLogin: Boolean = false
    var userId = String()
    var getPlanetEarthDetailBean = GetPlanetEarthDetailBean()
    private var adapter: DetailAdapter? = null


    private val titleList = object : ArrayList<String>() {
        init {
            add("每日一图")
            add("发生位置")
        }
    }

    private val fragmentList = object : ArrayList<Fragment>() {
        init {
            add(VideoPage1Delegate())
            add(VideoPage2Delegate())
        }
    }


    fun create(): VideoDetailDelegate? {
        return VideoDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_video_detail
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getdata(videoId)

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initial() {
        EmallProgressBar.showProgressbar(context)
        imageView.visibility = View.INVISIBLE
        mask.visibility = View.INVISIBLE
        like_video.visibility = View.INVISIBLE

        videoId = arguments.getString("VIDEO_ID")
        isLogin = !DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()
        video_collect.setImageResource(R.drawable.collection)
        userId = if (isLogin)
            DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        else
            "-2"
        video_detail_2.visibility = View.INVISIBLE
        val parser = ConfigXmlParser()
        parser.parse(activity)
        cordovaWebView = CordovaWebViewImpl(SystemWebViewEngine(webview2)) as CordovaWebView?
        cordovaWebView!!.init(this, parser.pluginEntries, parser.preferences)
        webview2.loadUrl("file:///android_asset/www/index.html")
        adapter = DetailAdapter(childFragmentManager, titleList, fragmentList)
        video_viewpager.adapter = adapter
        setTabLayout()
        video_xTablayout.setupWithViewPager(video_viewpager)

        val bottomDialog = Dialog(activity, R.style.BottomDialog)
        val contentView = LayoutInflater.from(activity).inflate(R.layout.comment, null)

        mSharedPreferences = activity.getSharedPreferences("VIDEO_DETAIL", Context.MODE_PRIVATE)
        video_detail_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(video_detail_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        video_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small)
        video_detail_toolbar.setNavigationOnClickListener {
            _mActivity.onBackPressed()
        }

        like_video.setOnClickListener {
            if (isLogin) {
                isLiked = if (!isLiked) {
                    like_video.setImageResource(R.drawable.like_highlight)
                    isLiked = true
                    upvote(videoId, userId, "2")
                    true
                } else {
                    like_video.setImageResource(R.drawable.like)
                    isLiked = false
                    downvote(videoId, userId, "2")
                    like_count_video.text = (upVoteAmount).toString()
                    false
                }
            } else {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "DAILY_PIC")
                delegate.arguments = bundle
                start(delegate)
            }
        }


        video_collect.setOnClickListener {
            if (isLogin) {
                isCollected = if (!isCollected) {
                    video_collect.setImageResource(R.drawable.collection_highlight)
                    addCollection(videoId, userId, "2")
                    isCollected = true
                    true
                } else {
                    video_collect.setImageResource(R.drawable.collection)
                    cancelCollection(videoId, userId, "2")
                    isCollected = false
                    false
                }
            } else {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "DAILY_PIC")
                delegate.arguments = bundle
                start(delegate)
            }
        }


        comment_rl_video.setOnClickListener {
            if (isLogin) {
                bottomDialog.setContentView(contentView)
                val layoutParams = contentView.layoutParams
                layoutParams.width = resources.displayMetrics.widthPixels
                contentView.layoutParams = layoutParams
                bottomDialog.window!!.setGravity(Gravity.BOTTOM)
                bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
                bottomDialog.show()
                if (!contentView.comment_area.text.toString().isEmpty()) {
                    contentView.comment_area.setText("")
                }
            } else {
                val delegate: SignInByTelDelegate = SignInByTelDelegate().create()!!
                val bundle = Bundle()
                bundle.putString("PAGE_FROM", "DAILY_PIC")
                delegate.arguments = bundle
                start(delegate)
            }
        }

        contentView.cancel.setOnClickListener {
            bottomDialog.hide()
        }

        contentView.release.setOnClickListener {
            if (!contentView.comment_area.text.toString().isEmpty()) {
                submitComment(userId, videoId, "1", contentView.comment_area.text.toString())
                bottomDialog.hide()
            } else
                Toast.makeText(activity, "评论不能为空", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitComment(uId: String, aId: String, type: String, s: String) {
        submitCommentParams!!["userId"] = uId
        submitCommentParams!!["articleId"] = aId
        submitCommentParams!!["articleType"] = type
        submitCommentParams!!["content"] = s
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/submitComment")
                .params(submitCommentParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            initComments(videoId, userId, "2")
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

    private fun addCollection(articleId: String, userId: String, type: String) {
        addCollectionParams!!["articleId"] = articleId
        addCollectionParams!!["userId"] = userId
        addCollectionParams!!["type"] = type
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/addCollection")
                .params(addCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
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

    private fun cancelCollection(articleId: String, userId: String, type: String) {
        cancelCollectionParams!!["articleId"] = articleId
        cancelCollectionParams!!["userId"] = userId
        cancelCollectionParams!!["type"] = type
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/cancelCollection")
                .params(cancelCollectionParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
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


    private fun upvote(aid: String, uid: String, type: String) {
        upvoteParams!!["articleId"] = aid
        upvoteParams!!["userId"] = uid
        upvoteParams!!["type"] = type
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/upvote")
                .params(upvoteParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {
                            like_video.setImageResource(R.drawable.like_highlight)
                            like_count_video.text = (upVoteAmount + 1).toString()
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

    private fun downvote(aid: String, uid: String, type: String) {
        downvoteParams!!["articleId"] = aid
        downvoteParams!!["userId"] = uid
        downvoteParams!!["type"] = type
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/downvote")
                .params(downvoteParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        commonBean = Gson().fromJson(response, CommonBean::class.java)
                        if (commonBean.message == "success") {

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

    private fun getdata(id: String?) {
        getPlanetEarthDetailParams!!["videoId"] = id
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getPlanetEarthDetail")
                .params(getPlanetEarthDetailParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        getPlanetEarthDetailBean = Gson().fromJson(response, GetPlanetEarthDetailBean::class.java)

                        val videoDate = getPlanetEarthDetailBean.data.videoTime.substring(getPlanetEarthDetailBean.data.videoTime.length - 5, getPlanetEarthDetailBean.data.videoTime.length)
                        val editor = mSharedPreferences!!.edit()
                        editor.putString("videoName", getPlanetEarthDetailBean.data.videoName)
                        editor.putString("videoDate", videoDate)
                        editor.commit()
                        initViews(getPlanetEarthDetailBean)
                        initComments(videoId, userId, "2")

                        video_xTablayout!!.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: XTabLayout.Tab) {
                                video_viewpager.currentItem = tab.position
                                if (tab.position == 0) {
                                    EmallLogger.d(tab.position)
                                    relativeLayout_video.setBackgroundColor(Color.WHITE)
                                    comment_rl_video.visibility = View.VISIBLE
                                    repost_video.visibility = View.VISIBLE
                                    video_collect.visibility = View.VISIBLE
                                    video_comments.visibility = View.VISIBLE
                                    video_detail_1.visibility = View.VISIBLE
                                    video_detail_2.visibility = View.INVISIBLE
                                    relativeLayout_video.visibility = View.VISIBLE
                                    webview2.loadUrl("javascript:fly(\"" + getPlanetEarthDetailBean.data.latitude + "\",\"" + getPlanetEarthDetailBean.data.longitude + "\")")
                                } else {
                                    EmallLogger.d(tab.position)
                                    relativeLayout_video.setBackgroundColor(Color.BLACK)
                                    comment_rl_video.visibility = View.GONE
                                    repost_video.visibility = View.GONE
                                    video_collect.visibility = View.GONE
                                    video_comments.visibility = View.GONE
                                    video_detail_1.visibility = View.INVISIBLE
                                    video_detail_2.visibility = View.VISIBLE
                                    webview2.loadUrl("javascript:fly(\"" + getPlanetEarthDetailBean.data.latitude + "\",\"" + getPlanetEarthDetailBean.data.longitude + "\")")
                                }
                            }

                            override fun onTabUnselected(tab: XTabLayout.Tab) {
                            }

                            override fun onTabReselected(tab: XTabLayout.Tab) {
                            }

                        })
                        EmallProgressBar.hideProgressbar()
                        comment_listview_video.visibility = View.VISIBLE
                        imageView.visibility = View.VISIBLE
                        mask.visibility = View.VISIBLE
                        like_video.visibility = View.VISIBLE
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

    fun initComments(articleId: String, userId: String, type: String) {
        getArticleAttachParams!!["articleId"] = articleId
        getArticleAttachParams!!["userId"] = userId
        getArticleAttachParams!!["type"] = type
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getArticleAttach")
                .params(getArticleAttachParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        getArticleAttachBean = Gson().fromJson(response, GetArticleAttachBean::class.java)
                        if (getArticleAttachBean.data.comments != null) {
                            comment_adapter = CommentListViewAdapter(activity.applicationContext, getArticleAttachBean.data)
                            comment_listview_video.adapter = comment_adapter
                            if (comment_listview_video.headerViewsCount == 0) {
                                val headerView = layoutInflater.inflate(R.layout.comment_header, null)
                                comment_listview_video.addHeaderView(headerView)
                            }

                            comment_listview_video.dividerHeight = 0
                            comment_adapter!!.notifyDataSetChanged()
                            if (noComment_video.visibility == View.VISIBLE)
                                noComment_video.visibility = View.GONE
                        } else {
                            noComment_video.visibility = View.VISIBLE
                        }

                        like_count_video.text = getArticleAttachBean.data!!.upvoteAmount.toString()
                        video_comments.setMessageCount(getArticleAttachBean.data!!.commentAmount)
                        upVoteAmount = getArticleAttachBean.data!!.upvoteAmount
                        if (getArticleAttachBean.data!!.upvoteMark == 1) {
                            like_video.setImageResource(R.drawable.like_highlight)
                            isLiked = true
                            upVoteAmount--
                        }
                        if (getArticleAttachBean.data!!.collectionMark == 1) {
                            video_collect.setImageResource(R.drawable.collection_highlight)
                            isCollected = true
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


    private fun setTabLayout() {
        video_xTablayout.setupWithViewPager(video_viewpager)
        video_xTablayout!!.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab) {
                video_viewpager.currentItem = tab.position
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

    @SuppressLint("SetTextI18n")
    fun initViews(planetEarthDetailBean: GetPlanetEarthDetailBean) {
        if (planetEarthDetailBean.data.videoName != null)
            videodetailname.text = planetEarthDetailBean.data.videoName
        if (planetEarthDetailBean.data.thumbnailFilePath != null)
            Glide.with(context)
                    .load(planetEarthDetailBean.data.thumbnailFilePath)
                    .into(videodetailpic)
        if (planetEarthDetailBean.data.playCount != null)
            detailplayTimes.text = planetEarthDetailBean.data.playCount + getString(R.string.play_times)
        if (planetEarthDetailBean.data.videoDuration != null)
            detailduration.text = planetEarthDetailBean.data.videoDuration
    }

    override fun requestPermissions(p0: CordovaPlugin?, p1: Int, p2: Array<out String>?) {

    }

    override fun startActivityForResult(p0: CordovaPlugin?, p1: Intent?, p2: Int) {
        setActivityResultCallback(p0)
        try {
            startActivityForResult(p1, p2)
        } catch (e: RuntimeException) {
            activityResultCallback1 = null
            throw e
        }
    }

    override fun setActivityResultCallback(p0: CordovaPlugin?) {
        if (activityResultCallback1 != null) {
            activityResultCallback1!!.onActivityResult(activityResultRequestCode, Activity.RESULT_CANCELED, null)
        }
        activityResultCallback1 = p0
    }

    override fun onMessage(p0: String?, p1: Any?): Any? {
        if ("exit" == p0) {
            activity.finish()
        }
        return null
    }

    override fun getThreadPool(): ExecutorService {
        return threadPool

    }

    override fun hasPermission(p0: String?): Boolean {
        return false
    }

    override fun requestPermission(p0: CordovaPlugin?, p1: Int, p2: String?) {
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        EmallLogger.d("onSupportVisible")
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
            like_video.setImageResource(R.drawable.like)
            video_collect.setImageResource(R.drawable.collection)
            isLogin = false
        } else {
            userId = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
            initComments(videoId, userId, "2")
            isLogin = true
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}