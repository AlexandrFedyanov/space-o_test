package com.spaceo.afedyanov.space_otest.view.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.spaceo.afedyanov.space_otest.R

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val noteName: TextView
    val stateCheckBox: CheckBox
    val stateImage: ImageView

    init {
        noteName = view.findViewById(R.id.noteName) as TextView
        stateCheckBox = view.findViewById(R.id.stateCheckBox) as CheckBox
        stateImage = view.findViewById(R.id.stateImage) as ImageView
    }
}