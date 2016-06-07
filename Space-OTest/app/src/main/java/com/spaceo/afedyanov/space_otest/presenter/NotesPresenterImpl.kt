package com.spaceo.afedyanov.space_otest.presenter

import android.content.Intent
import android.util.Log
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.NotesPresenter
import com.spaceo.afedyanov.space_otest.view.viewinterface.NotesView

/**
 * Created by Alexandr on 07.06.2016.
 */
class NotesPresenterImpl: NotesPresenter {

    private var view: NotesView? = null

    override fun attachView(view: NotesView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
    override fun getNotes() {
        val notes: MutableList<Note> = mutableListOf()
        for (i in 0..20) {
            val note = Note()
            note.isChecked = false
            note.name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. " + i
            notes.add(note)
        }
        view?.setNotes(notes)
    }

    override fun addNoteClick() {
       Log.d(NotesPresenterImpl::class.java.simpleName, "addNoteClick")
    }

    override fun removeNotesClick(notes: MutableList<Note>) {

    }

    override fun editNoteClick(note: Note) {

    }

    override fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }
}