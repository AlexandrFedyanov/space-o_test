package com.spaceo.afedyanov.space_otest.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.listener.NotesAdapterItemsClickListener
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.adapter.viewholder.NoteViewHolder

class NotesAdapter(values: MutableList<Note>): BaseSelectableReycleAdaprer<Note>(values){

    private var listener: NotesAdapterItemsClickListener? = null

    override fun updateItem(item: Note) {
        var position = -1;
        for (i in 0..itemCount) {
            if (item._id == getItem(i)._id) {
                position = i;
                break
            }
        }
        values.removeAt(position)
        values.add(position, item)
        notifyItemChanged(position)
    }

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
            holder.stateImage.setImageResource(if (note.isChecked) R.drawable.ic_check_circle_white_48dp else R.drawable.ic_info_white_48dp)
            holder.noteName.setOnClickListener({ listener?.onNoteClick(note, position) })
            holder.noteName.setOnLongClickListener{
                listener?.onNoteLongClick(position)
                true
            }
            holder.stateCheckBox.setOnClickListener({ listener?.onNoteCheckClick(note, !note.isChecked) })
            holder.rootView.setBackgroundResource(if (selectedItems.get(position, false)) R.color.base_selector else R.color.transparent)
        }
    }
}