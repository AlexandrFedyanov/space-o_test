package com.spaceo.afedyanov.space_otest.view.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.spaceo.afedyanov.space_otest.R

class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val feedText: TextView
    val feedDate: TextView

    init {
        feedText = view.findViewById(R.id.feedText) as TextView
        feedDate = view.findViewById(R.id.feedDate) as TextView
    }
}