package com.karleinstein.basemvvm

import android.util.Log

data class ExpandableData<CI, GI>(
    val groupItem: GroupItem<GI>,
    val listChildItem: ListChildItem<CI>
)

sealed class ExpandableItem

data class GroupItem<GI>(val input: GI) : ExpandableItem()

data class ChildItem<CI>(val input: CI, var isExpand: Boolean) : ExpandableItem()

data class ListChildItem<CI>(val input: List<CI>) : ExpandableItem()


fun <CI, GI> List<ExpandableData<CI, GI>>.convertToFlatList(): List<ExpandableItem> {

    val result: MutableList<ExpandableItem> = mutableListOf()

    forEach { data ->
        result.add(data.groupItem)
        if (data.listChildItem.input.isNotEmpty()) {
            data.listChildItem.input.forEach {
                result.add(ChildItem(it, false))
            }
        }
    }
    return result
}

fun List<ExpandableItem>.setStateChildView(isExpand: Boolean, position: Int): List<ExpandableItem> {
    Log.d("TAG", "setStateChildView: $isExpand")
    val result = this
    var endPosition = position
    endPosition++
    if (endPosition >= this.size) {
        return result
    }
    while (result[endPosition] !is GroupItem<*>) {
        if (result[endPosition] is ChildItem<*>) {
            (result[endPosition] as ChildItem<*>).isExpand = isExpand
        }
        endPosition++
        if (endPosition >= this.size) {
            return result
        }
    }
    return result
}
