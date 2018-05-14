package com.example.emall_ec.main.detail

import com.example.emall_ec.main.index.move.recycler.MultipleItemEntity

/**
 * Created by lixiang on 2018/2/28.
 */
abstract class DetailDataConverter {
    val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null

    abstract fun convert(): ArrayList<MultipleItemEntity>

    fun setJsonData(json: String): DetailDataConverter {
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