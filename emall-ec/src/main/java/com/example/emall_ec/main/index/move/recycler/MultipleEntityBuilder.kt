package com.example.emall_ec.main.index.move.recycler


import com.example.emall_ec.main.index.move.recycler.MultipleItemEntity


/**
 * Created by lixiang on 17/02/2018.
 */
class MultipleEntityBuilder {
    private val FIELDS : LinkedHashMap<Any, Any> = LinkedHashMap()

    fun MultipleEntityBuilder(){
        //先清除之前的数据
        FIELDS.clear()
    }

    fun setItemType(itemType: Int): MultipleEntityBuilder {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType)
        return this
    }

    fun setField(key: Any, value: Any): MultipleEntityBuilder {
        FIELDS.put(key, value)
        return this
    }

    fun setFields(map: LinkedHashMap<*, *>): MultipleEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}