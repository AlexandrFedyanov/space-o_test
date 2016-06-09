package com.spaceo.afedyanov.space_otest.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.listener.NotesAdapterItemsClickListener
import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.adapter.viewholder.FeedViewHolder
import com.spaceo.afedyanov.space_otest.view.adapter.viewholder.NoteViewHolder

class FeedsAdapter(values: MutableList<FeedRecord>): BaseSelectableReycleAdaprer<FeedRecord>(values){


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return FeedViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_feed_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is FeedViewHolder) {
            val feed = getItem(position)
            holder.feedText.text = feed.text
            holder.feedDate.text = feed.date
        }
    }
}