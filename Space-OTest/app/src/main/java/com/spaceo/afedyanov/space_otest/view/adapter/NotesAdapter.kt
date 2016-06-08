package com.spaceo.afedyanov.space_otest.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.listener.NotesAdapterItemsClickListener
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.adapter.viewholder.NoteViewHolder

class NotesAdapter(values: MutableList<Note>): BaseRecycleAdapter<Note>(values){

    private var listener: NotesAdapterItemsClickListener? = null

    fun setNotesClickListener(listener: NotesAdapterItemsClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return NoteViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_note_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NoteViewHolder) {
            val note = getItem(position)
            holder.noteName.text = note.name
            holder.stateCheckBox.isChecked = note.isChecked
            holder.noteName.setOnClickListener({ listener?.onNoteClick(note) })
            holder.stateCheckBox.setOnClickListener({ listener?.onNoteCheckClick(note, !note.isChecked) })
        }
    }
}