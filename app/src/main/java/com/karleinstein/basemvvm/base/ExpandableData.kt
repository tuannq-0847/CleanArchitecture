package com.karleinstein.basemvvm.base

import android.util.Log

data class ExpandableData<CI, GI>(
    val groupItem: GroupItem<GI>,
    val childItems: ChildItems<CI>
)

sealed class ExpandableItem

data class GroupItem<GI>(val input: GI) : ExpandableItem()

data class ChildItem<CI>(val input: CI, var isExpand: Boolean) : ExpandableItem()

class ChildItems<CI>(vararg val input: CI) : ExpandableItem()

fun <CI, GI> List<ExpandableData<CI, GI>>.convertToFlatList(): List<ExpandableItem> {

    val result: MutableList<ExpandableItem> = mutableListOf()

    forEach { data ->
        result.add(data.groupItem)
        if (data.childItems.input.isNotEmpty()) {
            data.childItems.input.forEach {
                result.add(ChildItem(it, false))
            }
        }
    }
    return result
}

fun List<ExpandableItem>.createNewInstance(): List<ExpandableItem> {

    return map {
        if (it is ChildItem<*>) {
            ChildItem(it.input, it.isExpand)
        } else GroupItem((it as GroupItem<*>).input)
    }
}

fun List<ExpandableItem>.setStateChildView(isExpand: Boolean, position: Int): List<ExpandableItem> {
    Log.d("TAG", "setStateChildView: $isExpand")
    val result = this.toMutableList()
    var endPosition = position
    endPosition++
    if (endPosition >= this.size) {
        return result
    }
    while (result[endPosition] !is GroupItem<*>) {
        if (result[endPosition] is ChildItem<*>) {
            result[endPosition] =
                ChildItem(input = (result[endPosition] as ChildItem<*>).input, isExpand = isExpand)
        }
        endPosition++
        if (endPosition >= this.size) {
            return result
        }
    }
    return result
}

object ExpandableDiffUtil {

    fun areItemsTheSame(oldData: ExpandableItem, newData: ExpandableItem): Boolean {
        return if (oldData is ChildItem<*> && newData is ChildItem<*>) {
            Log.d("areItemsTheSame", "old: $oldData new: $newData")
            oldData.isExpand == newData.isExpand
        } else false
    }

    fun areContentTheSame(oldData: ExpandableItem, newData: ExpandableItem): Boolean {
        return if (oldData is ChildItem<*> && newData is ChildItem<*>) {
            Log.d("areContentTheSame", "old: $oldData new: $newData")
            oldData == newData
        } else false
    }
}
