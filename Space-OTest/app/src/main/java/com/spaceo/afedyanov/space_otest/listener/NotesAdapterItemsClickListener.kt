package com.spaceo.afedyanov.space_otest.listener

import com.spaceo.afedyanov.space_otest.model.entity.Note

interface NotesAdapterItemsClickListener {
    fun onNoteClick(note: Note)
    fun onNoteCheckClick(note: Note, isChecked: Boolean)
}