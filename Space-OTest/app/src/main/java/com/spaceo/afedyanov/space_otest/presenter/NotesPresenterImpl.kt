package com.spaceo.afedyanov.space_otest.presenter

import android.app.Activity
import android.content.Intent
import com.spaceo.afedyanov.space_otest.appnavigation.NavigationConstants
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.model.storage.Storage
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.NotesPresenter
import com.spaceo.afedyanov.space_otest.view.viewinterface.NotesView

/**
 * Created by Alexandr on 07.06.2016.
 */
class NotesPresenterImpl(private val storage: Storage?): NotesPresenter {

    private var view: NotesView? = null

    override fun attachView(view: NotesView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
    override fun getNotes() {
        view?.setNotes(storage?.notesRepository?.getNotes())
    }

    override fun addNoteClick() {
        view?.openAddNoteScreen()
    }

    override fun removeNoteClick(note: Note) {
        storage?.notesRepository?.removeNote(note)
        view?.removeNote(note)
    }

    override fun removeNotesClick(notes: MutableList<Note>) {
        notes.forEach { removeNoteClick(it) }
    }

    override fun editNoteClick(note: Note) {
        view?.openEdinNoteScreen(note)
    }

    override fun checkNoteClick(note: Note, isChecked: Boolean) {
        note.isChecked = isChecked;
        storage?.notesRepository?.updateNote(note)
        view?.updateNote(note)
    }

    override fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NavigationConstants.Codes.ADD_NOTE_REQUEST) {
                val note = data?.getSerializableExtra(NavigationConstants.Keys.NOTE_KEY) as Note
                note._id = storage?.notesRepository?.saveNote(note)
                view?.addNote(note)
            }
            else if (requestCode == NavigationConstants.Codes.EDIT_NOTE_REQUEST) {
                val note = data?.getSerializableExtra(NavigationConstants.Keys.NOTE_KEY) as Note
                note._id = storage?.notesRepository?.saveNote(note)
                view?.updateNote(note)
            }
        }
    }
}