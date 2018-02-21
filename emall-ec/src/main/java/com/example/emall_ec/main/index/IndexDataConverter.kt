package com.example.emall_ec.main.index

import com.example.emall_core.ui.recycler.DataConverter
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.ItemType
import com.alibaba.fastjson.JSON
import com.example.emall_core.app.Emall
import com.example.emall_core.ui.progressbar.EmallProgressbar
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 17/02/2018.
 */
class IndexDataConverter : DataConverter() {

    override fun bannerConvert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
//        val bannerImages: MutableList<String>? = mutableListOf()
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val bannerImageUrl = data.getString("imageUrl")
            val bannerLink = data.getString("link")
//            bannerImages!!.add(bannerImageUrl)
            val entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.BANNERS_COUNT, size)
                    .setField(MultipleFields.BANNERS, bannerImageUrl)
                    .setField(MultipleFields.BANNERS_LINK, bannerLink)
                    .setField(MultipleFields.ITEM_TYPE,1)
                    .build()

            ENTITIES.add(entity)

        }
        return ENTITIES
    }


    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("MixedContentList")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val imageUrl1 = data.getString("thumbnail1Path")
            val contentDate = data.getString("contentDate")

//            val theThreeImages: MutableList<String>? = mutableListOf()
//            theThreeImages!!.add(imageUrl)

//            val banners = data.getJSONArray("banners")

//            val bannerImages: MutableList<String>? = null
//            var type = 0
//            if (imageUrl == null && text != null) {
//            type = ItemType.BANNER
//            } else if (imageUrl != null && text == null) {
//                type = ItemType.THE_THREE
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
//                }
//            }

            val entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.THE_THREE_IMAGE_URL1, imageUrl1)
                    .setField(MultipleFields.CONTENT_DATE, contentDate)
                    .setField(MultipleFields.ITEM_TYPE,2)
                    .build()

            ENTITIES.add(entity)
        }
        return ENTITIES
    }


}