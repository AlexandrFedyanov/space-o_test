package com.spaceo.afedyanov.space_otest.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.adapter.viewholder.NoteViewHolder

class NotesAdapter(values: MutableList<Note>): BaseRecycleAdapter<Note>(values){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return NoteViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val noteHolder = holder as NoteViewHolder
        val note = getItem(position)
        holder.noteName.text = note.name
        holder.stateCheckBox.isChecked = note.isChecked
    }
}