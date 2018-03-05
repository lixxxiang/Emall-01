package com.example.emall_ec.main.order.state

import com.alibaba.fastjson.JSON
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_ec.main.detail.DetailDataConverter
import com.example.emall_ec.main.index.VideoDetailFields

/**
 * Created by lixiang on 2018/3/5.
 */
abstract class OrderDataConverter {
    val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null

    fun convert(): ArrayList<MultipleItemEntity>{
        val dataArray = JSON.parseObject(getJsonData()).getJSONObject("data")
        val duration = dataArray.getString("duration")
        val entity = MultipleItemEntity.builder()
                .setField(VideoDetailFields.DURATION, duration)
                .setField(VideoDetailFields.IMAGEDETAILURL, dataArray.getString("imageDetailUrl"))
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