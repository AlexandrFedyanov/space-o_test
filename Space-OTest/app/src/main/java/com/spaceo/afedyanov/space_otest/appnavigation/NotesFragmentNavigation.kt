package com.spaceo.afedyanov.space_otest.appnavigation

import android.content.Intent
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.activity.NoteAddEditActivity
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment

/**
 * Created by Alexandr on 07.06.2016.
 */
fun NotesFragment.showCreateNote() {
    val intent = Intent(activity, NoteAddEditActivity::class.java)
    intent.action = NavigationConstants.Actions.ACTION_CREATE_NOTE
    startActivityForResult(intent, NavigationConstants.Codes.ADD_NOTE_REQUEST)
}

fun NotesFragment.showEditNote(note : Note) {
    val intent = Intent(activity, NoteAddEditActivity::class.java)
    intent.action = NavigationConstants.Actions.ACTION_EDIT_NOTE
    intent.putExtra(NavigationConstants.Keys.NOTE_KEY, note)
    startActivityForResult(intent, NavigationConstants.Codes.ADD_NOTE_REQUEST)
}