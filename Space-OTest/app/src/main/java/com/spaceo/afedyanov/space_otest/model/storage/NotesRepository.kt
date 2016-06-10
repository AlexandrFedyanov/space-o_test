package com.spaceo.afedyanov.space_otest.model.storage

import com.spaceo.afedyanov.space_otest.model.entity.Note
import nl.qbusict.cupboard.DatabaseCompartment

class NotesRepository(private val databaseCompartment: DatabaseCompartment) {

    fun saveNote(note: Note): Long {
        return databaseCompartment.put<Note>(note)
    }

    fun getNotes(): MutableList<Note> {
        return databaseCompartment.query<Note>(Note::class.java).list()
    }

    fun removeNote(note: Note) {
        databaseCompartment.delete<Note>(note)
    }

    fun updateNote(note: Note) {
        databaseCompartment.put<Note>(note)
    }
}