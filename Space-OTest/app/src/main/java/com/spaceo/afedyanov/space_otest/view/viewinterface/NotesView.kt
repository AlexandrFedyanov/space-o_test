package com.spaceo.afedyanov.space_otest.view.viewinterface

import com.spaceo.afedyanov.space_otest.model.entity.Note

/**
 * Created by Alexandr on 07.06.2016.
 */
interface NotesView {

    fun setNotes(notes: MutableList<Note>)

    fun addNote(note: Note)

    fun removeNote(note: Note)

    fun removeNotes(notes: MutableList<Note>)

    fun openEdinNoteScreen(note: Note)

    fun openAddNoteScreen()
}