package com.example.emall_ec.main.special

import com.alibaba.fastjson.JSON
import com.example.emall_core.ui.recycler.MultipleFields
import com.example.emall_core.ui.recycler.MultipleItemEntity
import com.example.emall_core.ui.recycler.data.GuessLikeBean
import com.example.emall_core.ui.recycler.data.UnitBean
import com.example.emall_core.util.log.EmallLogger
import com.example.emall_ec.main.special.beans.SpecialHorizontalBean
import com.example.emall_ec.main.special.beans.SpecialVerticalBean

/**
 * Created by lixiang on 2018/3/12.
 */
class SpecialDataConverter {
    val ENTITIES: ArrayList<SpecialItemEntity> = ArrayList()
    var horizontalData : MutableList<SpecialHorizontalBean>? = mutableListOf()
    var verticalData : MutableList<SpecialVerticalBean>? = mutableListOf()

    private var mJsonData: String? = null

    fun horizontalConvert(): ArrayList<SpecialItemEntity>{
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
            horizontalData!!.add(SpecialHorizontalBean(title, detail, imageUrl, type, link))
        }
        val entity = SpecialItemEntity.builder()
                .setField(SpecialMultipleFields.HORIZONTAL, horizontalData!!)
                .setField(SpecialMultipleFields.SPAN_SIZE, 2)
                .setField(SpecialMultipleFields.ITEM_TYPE, 0)
                .build()

        ENTITIES.add(entity)
        var list:MutableList<SpecialHorizontalBean> = ENTITIES[0].getField(SpecialMultipleFields.HORIZONTAL)
        return ENTITIES
    }
    fun verticalConvert(): ArrayList<SpecialItemEntity>{
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
            verticalData!!.add(SpecialVerticalBean(dataType, imageUrl, posTitle, posDescription, price))
        }
        val entity = SpecialItemEntity.builder()
                .setField(SpecialMultipleFields.VERTICAL, verticalData!!)
                .setField(SpecialMultipleFields.SPAN_SIZE, 2)
                .setField(SpecialMultipleFields.ITEM_TYPE, 1)
                .build()
        ENTITIES.add(entity)
        return ENTITIES
    }


    fun setJsonData(json: String): SpecialDataConverter {
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