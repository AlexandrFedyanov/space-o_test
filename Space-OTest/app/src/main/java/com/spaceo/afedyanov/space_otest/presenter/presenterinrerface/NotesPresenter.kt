package com.spaceo.afedyanov.space_otest.presenter.presenterinrerface

import android.content.Intent
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.viewinterface.NotesView

/**
 * Created by Alexandr on 07.06.2016.
 */
interface NotesPresenter {

    fun attachView(view: NotesView)

    fun detachView()

    fun getNotes()

    fun addNoteClick()

    fun removeNotesClick(notes: MutableList<Note>)

    fun editNoteClick(note: Note)

    fun setActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}