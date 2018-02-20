package com.example.emall_ec.main.index

import com.example.emall_core.ui.recycler.DataConverter
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.ItemType
import com.alibaba.fastjson.JSON
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 17/02/2018.
 */
class IndexDataConverter : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)

            val imageUrl = data.getString("thumbnail1Path")
            val text = data.getString("contentName")
            val spanSize = data.getString("type")
            val id = data.getString("contentId")


            val banners = data.getJSONArray("banners")

            val bannerImages: MutableList<String>? = null
            var type = 0
//            if (imageUrl == null && text != null) {
//                type = ItemType.BANNER
//            } else if (imageUrl != null && text == null) {
//                type = ItemType.IMAGE
//            } else if (imageUrl != null) {
//                type = ItemType.TEXT_IMAGE
//            }
//            else if (banners != null) {
//                type = ItemType.BANNER
//                //Banner的初始化
//                val bannerSize = banners.size
//                for (j in 0 until bannerSize) {
//                    val banner = banners.getString(j)
//                    bannerImages!!.add(banner)
//
//                }
//            }

            val entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, Integer.parseInt(spanSize))
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGE_URL, imageUrl)
//                    .setField(MultipleFields.BANNERS, bannerImages!!)
                    .build()

            ENTITIES.add(entity)

        }

        return ENTITIES
    }

    /**
     * 在这重写一个convert方法！！！！
     */
    override fun bannerConvert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until (size - 1)) {
            val data = dataArray.getJSONObject(i)

            val bannerImageUrl = data.getString("imageUrl")
//            EmallLogger.d(bannerImageUrl)
            val bannerImages: MutableList<String>? = mutableListOf()
            bannerImages!!.add(bannerImageUrl)
            val entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.IMAGE_URL, bannerImageUrl)
                    .build()

            ENTITIES.add(entity)

        }
        EmallLogger.d(ENTITIES[0].getField(MultipleFields.IMAGE_URL))

        return ENTITIES
    }
}