package com.spaceo.afedyanov.space_otest.view.adapter

import android.util.SparseBooleanArray

open class BaseSelectableReycleAdaprer<T>(values: MutableList<T>): BaseRecycleAdapter<T>(values) {
    protected val selectedItems = SparseBooleanArray()

    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)
        } else {
            selectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }

    fun clearSelections() {
        selectedItems.clear()
        for (i in 0..itemCount - 1)
            notifyItemChanged(i)
    }

    fun getSelectedItemsCount(): Int {
        return selectedItems.size()
    }

    fun getSelectedItems(): MutableList<T> {
        val items: MutableList<T> = mutableListOf()
        for (i in 0..selectedItems.size() - 1) {
            items.add(values[selectedItems.keyAt(i)])
        }
        return items
    }
}