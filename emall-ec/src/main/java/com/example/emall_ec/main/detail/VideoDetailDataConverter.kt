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
        val dataArray = JSON.parseObject(getJsonData()).getJSONObject("data")
        val duration = dataArray.getString("duration")
        val entity = MultipleItemEntity.builder()
                        .setField(VideoDetailFields.DURATION, duration)
                        .build()
        ENTITIES.add(entity)
        return ENTITIES
    }


}