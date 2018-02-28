package com.example.emall_ec.main.detail

import com.alibaba.fastjson.JSON
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.index.VideoDetailFields

/**
 * Created by lixiang on 2018/2/28.
 */
class VideoDetailDataConverter : DetailDataConverter(){
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        EmallLogger.d(dataArray)
//        val entity = MultipleItemEntity.builder()
//                        .setField(VideoDetailFields.CLOUD, )
//                        .setField(VideoDetailFields.SPAN_SIZE, 2)
//                        .setField(VideoDetailFields.ITEM_TYPE, 2)
//                        .build()
    }


}