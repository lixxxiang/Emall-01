package com.example.emall_ec.main.index

import com.example.emall_core.ui.recycler.DataConverter
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.alibaba.fastjson.JSON
import com.example.emall_core.ui.recycler.data.GuessLikeBean
import com.example.emall_core.ui.recycler.data.TheThreeBean
import com.example.emall_core.ui.recycler.data.UnitBean
import com.example.emall_core.util.log.EmallLogger


/**
 * Created by lixiang on 17/02/2018.
 */
class IndexDataConverter : DataConverter() {

    var unit: MutableList<UnitBean>? = mutableListOf()
    var theThree: MutableList<TheThreeBean>? = mutableListOf()
    var guessLike: MutableList<GuessLikeBean>? = mutableListOf()

    override fun guessLikeConvert(): ArrayList<MultipleItemEntity> {
        var jsonObject = JSON.parseObject(getJsonData()).getJSONArray("data").getJSONObject(2).getJSONArray("pieces")
        var size = jsonObject.size
        var dataType: String?
        var imageUrl: String?
        var posTitle: String?
        var posDescription: String?
        var price: String?
        for (i in 0 until size) {
            dataType = jsonObject.getJSONObject(i).getString("dataType")
            imageUrl = jsonObject.getJSONObject(i).getString("imageUrl")
            posTitle = jsonObject.getJSONObject(i).getString("posTitle")
            posDescription = jsonObject.getJSONObject(i).getString("posDescription")
            price = jsonObject.getJSONObject(i).getString("price")
            guessLike!!.add(GuessLikeBean(dataType, imageUrl, posTitle, posDescription, price))
        }
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.GUESS_LIKE, guessLike!!)
                .setField(MultipleFields.SPAN_SIZE, 2)
                .setField(MultipleFields.ITEM_TYPE, 4)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }

    override fun theThreeConvert(): ArrayList<MultipleItemEntity> {
        var jsonObject = JSON.parseObject(getJsonData()).getJSONArray("data").getJSONObject(1).getJSONArray("pieces")
        var size = jsonObject.size
        var imageUrl: String?
        var link: String?
        var type: String?
        for (i in 0 until size) {
            imageUrl = jsonObject.getJSONObject(i).getString("imageUrl")
            link = jsonObject.getJSONObject(i).getString("link")
            type = jsonObject.getJSONObject(i).getString("dataType")
            theThree!!.add(TheThreeBean(imageUrl, type, link))
        }
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.THE_THREE, theThree!!)
                .setField(MultipleFields.SPAN_SIZE, 2)
                .setField(MultipleFields.ITEM_TYPE, 3)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }


    override fun horizontalScrollConvert(): ArrayList<MultipleItemEntity> {
        var jsonObject = JSON.parseObject(getJsonData()).getJSONArray("data").getJSONObject(0).getJSONArray("pieces")
        var size = jsonObject.size
        var title: String?
        var detail: String?
        var imageUrl: String?
        var link: String?
        var type: String?
        for (i in 0 until size) {
            title = jsonObject.getJSONObject(i).getString("posTitle")
            detail = jsonObject.getJSONObject(i).getString("posDescription")
            imageUrl = jsonObject.getJSONObject(i).getString("imageUrl")
            link = jsonObject.getJSONObject(i).getString("link")
            type = jsonObject.getJSONObject(i).getString("dataType")
            unit!!.add(UnitBean(title, detail, imageUrl, type, link))
        }
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.HORIZONTAL_SCROLL, unit!!)
                .setField(MultipleFields.SPAN_SIZE, 2)
                .setField(MultipleFields.ITEM_TYPE, 2)
                .build()

        ENTITIES.add(entity)
        return ENTITIES
    }

    override fun everyDayPicConvert(): ArrayList<MultipleItemEntity> {
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.EVERY_DAY_PIC_TITLE, "title")
                .setField(MultipleFields.SPAN_SIZE, 2)
                .setField(MultipleFields.ITEM_TYPE, 1)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }

    override fun bannerConvert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        val size = dataArray.size
        var entity = MultipleItemEntity.builder().build()
        val bannerImages: MutableList<String>? = mutableListOf()
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val bannerImageUrl = data.getString("imageUrl")
            val bannerLink = data.getString("link")
            bannerImages!!.add(bannerImageUrl)
            entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.BANNERS_COUNT, size)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .setField(MultipleFields.BANNERS_LINK, bannerLink)
                    .setField(MultipleFields.SPAN_SIZE, 2)
                    .setField(MultipleFields.ITEM_TYPE, 0)
                    .build()


        }
        ENTITIES.add(entity)

        return ENTITIES
    }


    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("MixedContentList")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val imageUrl1 = data.getString("thumbnail1Path")
            val contentDate = data.getString("contentDate")
            val entity = MultipleItemEntity.builder()
//                    .setField(MultipleFields.HORIZONTAL_SCROLL_TITLE, imageUrl1)
                    .setField(MultipleFields.CONTENT_DATE, contentDate)
                    .setField(MultipleFields.SPAN_SIZE, 1)
                    .setField(MultipleFields.ITEM_TYPE, 2)
                    .build()

            ENTITIES.add(entity)
        }
        return ENTITIES
    }
}