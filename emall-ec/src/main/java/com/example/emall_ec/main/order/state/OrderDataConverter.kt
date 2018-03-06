package com.example.emall_ec.main.order.state

import com.alibaba.fastjson.JSON
import com.example.emall_core.app.Emall
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.detail.DetailDataConverter
import com.example.emall_ec.main.index.VideoDetailFields

/**
 * Created by lixiang on 2018/3/5.
 */
class OrderDataConverter {
    val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null
    var imageDetailUrl : MutableList<String> ?= mutableListOf()
    fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        (0 until size)
                .map { dataArray.getJSONObject(it).getJSONObject("details") }
                .forEach { imageDetailUrl!!.add(it.getString("imageDetailUrl")) }

//        for (i in 0 until size) {
//            val details = dataArray.getJSONObject(i).getJSONObject("details")
//            imageDetailUrl!!.add(details.getString("imageDetailUrl"))
//        }

        val entity = MultipleItemEntity.builder()
                .setField(OrderFeilds.IMAGEDETAILURL, imageDetailUrl!!)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
//                .setField(VideoDetailFields.DURATION, duration)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }

    fun setJsonData(json: String): OrderDataConverter {
        this.mJsonData = json
        return this
    }

    fun getJsonData(): String {
        if (mJsonData == null || mJsonData!!.isEmpty()) {
            throw NullPointerException("DATA IS NULL!")
        }
        return mJsonData as String
    }

    fun clearData() {
        ENTITIES.clear()
    }
}