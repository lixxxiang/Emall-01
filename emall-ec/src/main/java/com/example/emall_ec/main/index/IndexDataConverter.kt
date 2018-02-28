package com.example.emall_ec.main.index

import com.example.emall_core.ui.recycler.DataConverter
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.alibaba.fastjson.JSON
import com.example.emall_core.R
import com.example.emall_core.ui.recycler.HomeRecyclerViewDataConverter
import com.example.emall_core.ui.recycler.test.App


/**
 * Created by lixiang on 17/02/2018.
 */
class IndexDataConverter : DataConverter() {
    override fun theThreeConvert(): ArrayList<MultipleItemEntity> {
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.THE_THREE_IMAGE_URL, "http://202.111.178.10:28085/upload/image/201711151645000412863_thumb.jpg")
                .setField(MultipleFields.SPAN_SIZE, 2)
                .setField(MultipleFields.ITEM_TYPE, 3)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }

    override fun horizontalScrollConvert(): ArrayList<MultipleItemEntity> {
        val entity = MultipleItemEntity.builder()
                .setField(MultipleFields.HORIZONTAL_SCROLL, getApps())
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
                    .setField(MultipleFields.HORIZONTAL_SCROLL, imageUrl1)
                    .setField(MultipleFields.CONTENT_DATE, contentDate)
                    .setField(MultipleFields.SPAN_SIZE, 1)
                    .setField(MultipleFields.ITEM_TYPE, 2)
                    .build()

            ENTITIES.add(entity)
        }
        return ENTITIES
    }


    private fun getApps(): List<App> {
        val apps = java.util.ArrayList<App>()
        apps.add(App("Google+", R.drawable.ic_google_48dp, 4.6f))
        apps.add(App("Gmail", R.drawable.ic_gmail_48dp, 4.8f))
        apps.add(App("Inbox", R.drawable.ic_inbox_48dp, 4.5f))
        apps.add(App("Google Keep", R.drawable.ic_keep_48dp, 4.2f))
        apps.add(App("Google Drive", R.drawable.ic_drive_48dp, 4.6f))
        apps.add(App("Hangouts", R.drawable.ic_hangouts_48dp, 3.9f))
        apps.add(App("Google Photos", R.drawable.ic_photos_48dp, 4.6f))
        apps.add(App("Messenger", R.drawable.ic_messenger_48dp, 4.2f))
        apps.add(App("Sheets", R.drawable.ic_sheets_48dp, 4.2f))
        apps.add(App("Slides", R.drawable.ic_slides_48dp, 4.2f))
        apps.add(App("Docs", R.drawable.ic_docs_48dp, 4.2f))
        apps.add(App("Google+", R.drawable.ic_google_48dp, 4.6f))
        apps.add(App("Gmail", R.drawable.ic_gmail_48dp, 4.8f))
        apps.add(App("Inbox", R.drawable.ic_inbox_48dp, 4.5f))
        apps.add(App("Google Keep", R.drawable.ic_keep_48dp, 4.2f))
        apps.add(App("Google Drive", R.drawable.ic_drive_48dp, 4.6f))
        apps.add(App("Hangouts", R.drawable.ic_hangouts_48dp, 3.9f))
        apps.add(App("Google Photos", R.drawable.ic_photos_48dp, 4.6f))
        apps.add(App("Messenger", R.drawable.ic_messenger_48dp, 4.2f))
        apps.add(App("Sheets", R.drawable.ic_sheets_48dp, 4.2f))
        apps.add(App("Slides", R.drawable.ic_slides_48dp, 4.2f))
        apps.add(App("Docs", R.drawable.ic_docs_48dp, 4.2f))
        return apps
    }



}