package com.example.emall_ec.main.special

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference

/**
 * Created by lixiang on 2018/3/13.
 */

class SpecialItemEntity internal constructor(fields: LinkedHashMap<Any, Any>) : MultiItemEntity {

    private val ITEM_QUEUE : ReferenceQueue<LinkedHashMap<Any, Any>> = ReferenceQueue()
    private val MULTIPLE_FIELDS :LinkedHashMap<Any, Any> = LinkedHashMap()
    private val FIELDS_REFERENCE : SoftReference<LinkedHashMap<Any, Any>> = SoftReference(MULTIPLE_FIELDS, ITEM_QUEUE)

    val fields: LinkedHashMap<*, *>
        get() = FIELDS_REFERENCE.get()!!

    init {
        FIELDS_REFERENCE.get()!!.putAll(fields)
    }

    override fun getItemType(): Int {
        return FIELDS_REFERENCE.get()!![SpecialMultipleFields.ITEM_TYPE] as Int
    }

    fun <T> getField(key: Any): T {
        return FIELDS_REFERENCE.get()!![key] as T
    }

    fun setField(key: Any, value: Any): SpecialItemEntity {
        FIELDS_REFERENCE.get()!!.put(key, value)
        return this
    }

    companion object {

        fun builder(): SpecialMutipleEntityBuilder {
            return SpecialMutipleEntityBuilder()
        }
    }
}
