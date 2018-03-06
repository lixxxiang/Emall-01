package com.example.emall_core.ui.recycler

import android.text.method.TextKeyListener.clear



/**
 * Created by lixiang on 17/02/2018.
 */
abstract class DataConverter {
    val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null

    abstract fun convert(): ArrayList<MultipleItemEntity>
    abstract fun bannerConvert(): ArrayList<MultipleItemEntity>
    abstract fun everyDayPicConvert(): ArrayList<MultipleItemEntity>
    abstract fun horizontalScrollConvert(): ArrayList<MultipleItemEntity>
    abstract fun theThreeConvert(): ArrayList<MultipleItemEntity>
    abstract fun guessLikeConvert(): ArrayList<MultipleItemEntity>


    fun setJsonData(json: String): DataConverter {
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