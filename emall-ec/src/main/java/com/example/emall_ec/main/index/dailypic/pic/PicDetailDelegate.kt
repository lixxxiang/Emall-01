package com.example.emall_ec.main.index.dailypic.pic

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.androidkun.xtablayout.XTabLayout
import com.bumptech.glide.Glide
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.util.dimen.DimenUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.index.dailypic.adapter.CommentListViewAdapter
import com.example.emall_ec.main.index.dailypic.adapter.DetailAdapter
import com.example.emall_ec.main.index.dailypic.data.CommonBean
import com.example.emall_ec.main.index.dailypic.data.GetArticleAttachBean
import com.example.emall_ec.main.index.dailypic.data.GetDailyPicDetailBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.comment.view.*
import kotlinx.android.synthetic.main.delegate_pic_detail.*
import kotlinx.android.synthetic.main.pic_detail_1.*
import java.util.*
import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.ui.progressbar.EmallProgressBar
import com.example.emall_core.util.view.ShareUtil
import com.example.emall_ec.database.DatabaseManager
import com.example.emall_ec.main.sign.SignInByTelDelegate
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.pic_detail_2.*
import kotlinx.android.synthetic.main.share.view.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.apache.cordova.*
import org.apache.cordova.engine.SystemWebViewEngine
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * Created by lixiang on 2018/3/29.
 */
class PicDetailDelegate : EmallDelegate(), CordovaInterface {


    private var adapter: DetailAdapter? = null
    var userId = String()
    var imageId = String()
    var getDailyPicDetailParams: WeakHashMap<String, Any>? = WeakHashMap()
    var getArticleAttachParams: WeakHashMap<String, Any>? = WeakHashMap()
    var cancelCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var addCollectionParams: WeakHashMap<String, Any>? = WeakHashMap()
    var downvoteParams: WeakHashMap<String, Any>? = WeakHashMap()
    var upvoteParams: WeakHashMap<String, Any>? = WeakHashMap()
    var submitCommentParams: WeakHashMap<String, Any>? = WeakHashMap()
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
    var mSharedPreferences: SharedPreferences? = null
    var isLogin: Boolean = false
    var flag = false
    var cordovaWebView: CordovaWebView? = null
    private val threadPool = Executors.newCachedThreadPool()
    protected var activityResultRequestCode: Int = 0
    protected var prefs = CordovaPreferences()
    protected var pluginEntries: ArrayList<PluginEntry>? = null
    protected var activityResultCallback1: CordovaPlugin? = null
    var bottomDialog : Dialog ?= null

    private val titleList = object : ArrayList<String>() {
        init {
            add("每日一图")
            add("发生位置")
        }
    }

    private val fragmentList = object : ArrayList<Fragment>() {
        init {
            add(ImagePage1Delegate())
            add(ImagePage2Delegate())
        }
    }

    fun create(): PicDetailDelegate? {

        return PicDetailDelegate()
    }

    override fun setLayout(): Any? {
        return R.layout.delegate_pic_detail
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onEnterAnimationEnd(saveInstanceState: Bundle?) {
        // 这里设置Listener、各种Adapter、请求数据等等
        getdata(imageId)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ApplySharedPref")
    override fun initial() {
        EmallProgressBar.showProgressbar(context)
        like.visibility = View.INVISIBLE
        imageId = arguments.getString("IMAGE_ID")
        isLogin = !DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()
        collect.setImageResource(R.drawable.collection)
        userId = if (isLogin)
            DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
        else
            "-100"
        pic_detail_2.visibility = View.INVISIBLE
        val parser = ConfigXmlParser()
        parser.parse(activity)
        cordovaWebView = CordovaWebViewImpl(SystemWebViewEngine(webview1))
        cordovaWebView!!.init(this, parser.pluginEntries, parser.preferences)
        webview1.loadUrl("file:///android_asset/www/index.html")
        adapter = DetailAdapter(childFragmentManager, titleList, fragmentList)
        viewpager.adapter = adapter
        setTabLayout()
        xTablayout.setupWithViewPager(viewpager)

        bottomDialog = Dialog(activity, R.style.BottomDialog)
        val contentView = LayoutInflater.from(activity).inflate(R.layout.comment, null)
        userParams!!["articleId"] = imageId
        userParams!!["userId"] = userId
        userParams!!["type"] = "1"
        mSharedPreferences = activity.getSharedPreferences("IMAGE_DETAIL", Context.MODE_PRIVATE)
        pic_detail_toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(pic_detail_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pic_detail_toolbar.setNavigationIcon(R.drawable.ic_back_small)
        api = WXAPIFactory.createWXAPI(activity, "wxd12cdf5edf0f42fd")
        pic_detail_toolbar.setNavigationOnClickListener {
            _mActivity.onBackPressed()
        }

        like.setOnClickListener {
            if (isLogin) {
                isLiked = if (!isLiked) {
                    like.setImageResource(R.drawable.like_highlight)
                    isLiked = true
                    upvote(imageId, userId, "1")
                    true
                } else {
                    like.setImageResource(R.drawable.like)
                    isLiked = false
                    downvote(imageId, userId, "1")
                    like_count.text = (upVoteAmount).toString()
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


        collect.setOnClickListener {
            if (isLogin) {
                isCollected = if (!isCollected) {
                    collect.setImageResource(R.drawable.collection_highlight)
                    addCollection(imageId, userId, "1")
                    isCollected = true
                    true
                } else {
                    collect.setImageResource(R.drawable.collection)
                    cancelCollection(imageId, userId, "1")
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

        comment_rl.setOnClickListener {
            if (isLogin) {
                bottomDialog!!.setContentView(contentView)
                val layoutParams = contentView.layoutParams
                layoutParams.width = resources.displayMetrics.widthPixels
                contentView.layoutParams = layoutParams
                bottomDialog!!.window!!.setGravity(Gravity.BOTTOM)
                bottomDialog!!.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
                bottomDialog!!.show()
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
            bottomDialog!!.hide()
        }

        contentView.release.setOnClickListener {
            if (!contentView.comment_area.text.toString().isEmpty()) {
                submitComment(userId, imageId, "1", contentView.comment_area.text.toString())
                bottomDialog!!.hide()
            } else
                Toast.makeText(activity, "评论不能为空", Toast.LENGTH_SHORT).show()
        }

//        scrollview.setOnTouchListener { p0, p1 ->
//            if (scrollview.getChildAt(0).height - scrollview.height
//                    == scrollview.scrollY) {
//                comment_listview.visibility = View.VISIBLE
//            }
//            false
//        }

        val contentView2 = LayoutInflater.from(activity).inflate(R.layout.share, null)

        repost.setOnClickListener {
            bottomDialog!!.setContentView(contentView2)
            val layoutParams = contentView2.layoutParams
            layoutParams.width = resources.displayMetrics.widthPixels
            contentView2.layoutParams = layoutParams
            bottomDialog!!.window!!.setGravity(Gravity.BOTTOM)
            bottomDialog!!.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
            bottomDialog!!.show()
        }

        contentView2.wechat.setOnClickListener {
            shareToWechat(SendMessageToWX.Req.WXSceneSession, imageId, getDailyPicDetailBean.data.imageName, getDailyPicDetailBean.data.richText1)
        }

        contentView2.moment.setOnClickListener {
            shareToWechat(SendMessageToWX.Req.WXSceneTimeline, imageId, getDailyPicDetailBean.data.imageName, getDailyPicDetailBean.data.richText1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showImage(url: String, index: String, picAmount: String) {
        EmallLogger.d(fullScreenImages)
        var showImageIntent = Intent(activity, ShowImageNewActivity::class.java)
        showImageIntent.putExtra("imageUrl", url)
        showImageIntent.putExtra("images", fullScreenImages)
        showImageIntent.putExtra("index", index)
        showImageIntent.putExtra("picAmount", picAmount)
//        when(index){
//            "0" ->  startActivity(showImageIntent, ActivityOptions.makeSceneTransitionAnimation(activity, picture_1,"P1").toBundle())
////            "1" ->  startActivity(showImageIntent, ActivityOptions.makeSceneTransitionAnimation(activity, picture_2,"P1").toBundle())
////            "2" ->  startActivity(showImageIntent, ActivityOptions.makeSceneTransitionAnimation(activity, picture_3,"shareNames").toBundle())
//        }
        showImageIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(showImageIntent)
        activity.overridePendingTransition(0, 0)
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
                            initComments(imageId, userId, "1")
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getdata(id: String?) {
        getDailyPicDetailParams!!["imageId"] = id
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getDailyPicDetail")
                .params(getDailyPicDetailParams!!)
                .success(object : ISuccess {
                    @SuppressLint("ApplySharedPref")
                    override fun onSuccess(response: String) {
                        getDailyPicDetailBean = Gson().fromJson(response, GetDailyPicDetailBean::class.java)

                        val imageDate = getDailyPicDetailBean.data.imageDate.substring(getDailyPicDetailBean.data.imageDate.length - 5, getDailyPicDetailBean.data.imageDate.length)
                        val editor = mSharedPreferences!!.edit()
                        editor.putString("imageName", getDailyPicDetailBean.data.imageName)
                        editor.putString("imageDate", imageDate)
                        editor.commit()
                        flag = true
                        initViews(getDailyPicDetailBean)
                        initComments(imageId, userId, "1")
//                        adapter = DetailAdapter(childFragmentManager, titleList, fragmentList)
//                        viewpager.adapter = adapter
//                        setTabLayout()
//                        xTablayout.setupWithViewPager(viewpager)
                        xTablayout!!.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: XTabLayout.Tab) {
                                viewpager.currentItem = tab.position
                                if (tab.position == 0) {
                                    EmallLogger.d(tab.position)
                                    relativeLayout.setBackgroundColor(Color.WHITE)
                                    comment_rl.visibility = View.VISIBLE
                                    repost.visibility = View.VISIBLE
                                    collect.visibility = View.VISIBLE
                                    comments.visibility = View.VISIBLE
                                    pic_detail_1.visibility = View.VISIBLE
                                    pic_detail_2.visibility = View.INVISIBLE
                                    relativeLayout.visibility = View.VISIBLE
                                    webview1.loadUrl("javascript:fly(\"" + getDailyPicDetailBean.data.latitude + "\",\"" + getDailyPicDetailBean.data.longitude + "\")")
                                } else {
                                    EmallLogger.d(tab.position)
                                    relativeLayout.setBackgroundColor(Color.BLACK)
                                    comment_rl.visibility = View.GONE
                                    repost.visibility = View.GONE
                                    collect.visibility = View.GONE
                                    comments.visibility = View.GONE
                                    pic_detail_1.visibility = View.INVISIBLE
                                    pic_detail_2.visibility = View.VISIBLE
                                    webview1.loadUrl("javascript:fly(\"" + getDailyPicDetailBean.data.latitude + "\",\"" + getDailyPicDetailBean.data.longitude + "\")")
                                }
                            }

                            override fun onTabUnselected(tab: XTabLayout.Tab) {
                            }

                            override fun onTabReselected(tab: XTabLayout.Tab) {
                            }
                        })
                        EmallProgressBar.hideProgressbar()
                        comment_listview.visibility = View.VISIBLE
                        like.visibility = View.VISIBLE

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
            picture_1.setOnClickListener {
                showImage(dailyPicDetailBean.data.image1FilePath, "0", imageCount.toString())
            }

        }
        if (dailyPicDetailBean.data.image2FilePath.isNotEmpty()) {
            fullScreenImages.add(dailyPicDetailBean.data.image2FilePath)

            imageCount++
            picture_2.maxHeight = DimenUtil().getScreenHeight()
            Glide.with(context)
                    .load(dailyPicDetailBean.data.image2FilePath)
                    .into(picture_2)
            picture_2.setOnClickListener {
                showImage(dailyPicDetailBean.data.image2FilePath, "1", imageCount.toString())
            }
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
            picture_3.setOnClickListener {
                showImage(dailyPicDetailBean.data.image3FilePath, "2", imageCount.toString())
            }
        } else {
            picture_3.visibility = View.GONE
        }
    }

    fun initComments(articleId: String, userId: String, type: String) {

        getArticleAttachParams!!["articleId"] = articleId
        getArticleAttachParams!!["userId"] = userId
        getArticleAttachParams!!["type"] = type
        EmallLogger.d(getArticleAttachParams!!)
        RestClient().builder()
                .url("http://202.111.178.10:28085/mobile/getArticleAttach")
                .params(getArticleAttachParams!!)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        EmallLogger.d(response)
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
                            EmallLogger.d("??")
                            collect.setImageResource(R.drawable.collection_highlight)
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
                            like.setImageResource(R.drawable.like_highlight)
                            like_count.text = (upVoteAmount + 1).toString()
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


    fun getData(): GetDailyPicDetailBean.DataBean? {
        if (getDailyPicDetailBean.data != null)
            return getDailyPicDetailBean.data
        else return null
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

    override fun onSupportVisible() {
        super.onSupportVisible()
        EmallLogger.d("onSupportVisible")
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (DatabaseManager().getInstance()!!.getDao()!!.loadAll().isEmpty()) {
            like.setImageResource(R.drawable.like)
            collect.setImageResource(R.drawable.collection)
            isLogin = false
        } else {
            userId = DatabaseManager().getInstance()!!.getDao()!!.loadAll()[0].userId
            initComments(imageId, userId, "1")
            isLogin = true
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        bottomDialog!!.dismiss()
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

    private var api: IWXAPI? = null

    fun shareToWechat(scene: Int, articleId: String, title: String, description: String) {
        val THUMB_SIZE = 150
        val mTargetScene = scene

        val webpage = WXWebpageObject()
        webpage.webpageUrl = "http://10.10.90.3:8092?id=$articleId"
        val msg = WXMediaMessage(webpage)
        msg.title = title
        msg.description = description
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.app_icon)
        val thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)
        bmp.recycle()
        msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true)

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = mTargetScene
        api!!.sendReq(req)
    }

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

}