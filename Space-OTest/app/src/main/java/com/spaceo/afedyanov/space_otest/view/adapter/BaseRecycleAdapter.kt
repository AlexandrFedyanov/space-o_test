package com.spaceo.afedyanov.space_otest.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class BaseRecycleAdapter<T>(protected var values : MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(items: MutableList<T>) {
        this.values = items
    }

    fun clear() {
        this.values = mutableListOf()
        notifyDataSetChanged()
    }

    open fun add(item : T) {
        values.add(item)
        notifyItemInserted(itemCount - 1)
    }

    open fun addAll(items: MutableList<T>) {
        items.forEach { add(it) }
    }

    open fun addAllToStart(items: MutableList<T>) {
        items.forEach { addToStart(it) }
    }

    open fun addToStart(item: T) {
        values.add(0, item)
        notifyItemInserted(0)
    }

    open fun updateItem(item: T) {
        var position = -1;
        for (i in 0..itemCount) {
            if (item == getItem(i)) {
                position = i;
                break
            }
        }
        values.removeAt(position)
        values.add(position, item)
        notifyItemChanged(position)
    }

    fun remove(item : T) {
        val position = values.indexOf(item)
        values.remove(item)
        notifyItemRemoved(position)

        for (i in 0..itemCount - 1) {
            notifyItemChanged(i)
        }
    }

    fun getItem(position: Int) : T = values[position]

    override fun getItemCount(): Int {
        return values.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) { }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? = super.createViewHolder(parent, viewType)

}