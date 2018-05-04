package com.example.emall_ec.main.bottom

/**
 * Created by lixiang on 15/02/2018.
 */
class ItemBuilder {
    val ITEMS: LinkedHashMap<BottomTabBean, BottomItemDelegate>? = LinkedHashMap()

    fun builder(): ItemBuilder? {
        return ItemBuilder()
    }

    fun addItem(bean : BottomTabBean, delegate: BottomItemDelegate):ItemBuilder?{
        ITEMS!!.put(bean, delegate)
        return this
    }

    fun addItems(items : LinkedHashMap<BottomTabBean, BottomItemDelegate>): ItemBuilder? {
        ITEMS!!.putAll(items)
        return this
    }

    fun build(): LinkedHashMap<BottomTabBean, BottomItemDelegate>? {
        return ITEMS
    }
}