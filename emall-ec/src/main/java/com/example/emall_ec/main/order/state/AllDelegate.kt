package com.example.emall_ec.main.order.state

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.emall_core.delegates.EmallDelegate
import com.example.emall_core.net.RestClient
import com.example.emall_core.net.callback.ISuccess
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleRecyclerAdapter
import com.example.emall_core.util.file.FileUtil
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.R
import com.example.emall_ec.main.EcBottomDelegate
import com.example.emall_ec.main.detail.VideoDetailDataConverter
import com.example.emall_ec.main.index.IndexItemClickListener
import com.example.emall_ec.main.index.VideoDetailFields
import kotlinx.android.synthetic.main.delegate_all.*
import kotlinx.android.synthetic.main.delegate_index.*
import kotlinx.android.synthetic.main.delegate_video_goods_detail.*

/**
 * Created by lixiang on 2018/3/5.
 */
class AllDelegate : EmallDelegate() {

    private var adapter: OrderAdapter? = null
    override fun setLayout(): Any? {
        return R.layout.delegate_all
    }

    override fun initial() {
//        data("http://10.10.90.11:8086/global/order/findOrderListByUserId")
        val url: String = if (FileUtil.checkEmulator()) {
            "http://10.0.2.2:3035/data"
        } else {
            "http://192.168.1.36:3035/data"
        }
        data(url)
        initRefreshLayout()
        initRecyclerView()
    }

    fun data(url : String){
        RestClient().builder()
                .url(url)
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
//                        val bannerSize = BANNER_CONVERTER.setJsonData(response).bannerConvert().size
//                        for (i in 0 until bannerSize) {
//                            data!!.add(BANNER_CONVERTER.setJsonData(response).bannerConvert()[i])
////                            EmallLogger.d( BANNER_CONVERTER.setJsonData(response).bannerConvert()[i].getField(MultipleFields.BANNERS_LINK))
//
//                        }
////                        EmallLogger.d("BANNERDATA", data!!)
//                        data!!.add(EVERY_DAY_PIC_CONVERTER.everyDayPicConvert()[0])
//                        EmallLogger.d(HORIZONTAL_SCROLL_CONVERTER.horizontalScrollConvert()[0].getField(MultipleFields.HORIZONTAL_SCROLL))
//                        data!!.add(HORIZONTAL_SCROLL_CONVERTER.horizontalScrollConvert()[0])
//                        data!!.add(THE_THREE_CONVERTER.theThreeConvert()[0])
//                        mAdapter = MultipleRecyclerAdapter.create(data)
//                        mAdapter!!.setOnLoadMoreListener(this@RefreshHandler, RECYCLERVIEW)
//                        RECYCLERVIEW.adapter = mAdapter
//                        BEAN.addIndex()


                        val data = OrderDataConverter().setJsonData(response).convert()
                        println(data)
//                        EmallLogger.d(bannerSize[0].getField(VideoDetailFields.DURATION))
//                        DATA = bannerSize

//                        Glide.with(context)
//                                .load(DATA!![0].getField(VideoDetailFields.IMAGEDETAILURL))
//                                .into(video_goods_detail_title_image)
                    }
                })
                .build()
                .get()
    }

    fun initRefreshLayout() {
        all_srl.setColorSchemeColors(Color.parseColor("#b80017"))
        all_srl.setProgressViewOffset(true, 120, 300)
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        all_rv.layoutManager = manager

//        adapter = OrderAdapter(R.layout.item_order, datas)
//        recycler_view_index.adapter = adapter
    }
}