package com.example.emall_ec.main.special

/**
 * Created by lixiang on 2018/3/13.
 */
class SpecialMutipleEntityBuilder {
    private val FIELDS : LinkedHashMap<Any, Any> = LinkedHashMap()

    fun SpecialMutipleEntityBuilder(){
        //先清除之前的数据
        FIELDS.clear()
    }

    fun setItemType(itemType: Int): SpecialMutipleEntityBuilder {
        FIELDS.put(SpecialMultipleFields.ITEM_TYPE, itemType)
        return this
    }

    fun setField(key: Any, value: Any): SpecialMutipleEntityBuilder {
        FIELDS.put(key, value)
        return this
    }

    fun setFields(map: LinkedHashMap<*, *>): SpecialMutipleEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): SpecialItemEntity {
        return SpecialItemEntity(FIELDS)
    }
}