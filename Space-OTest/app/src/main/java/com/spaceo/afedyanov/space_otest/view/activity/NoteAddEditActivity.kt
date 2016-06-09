package com.spaceo.afedyanov.space_otest.view.activity

import android.content.Intent
import android.os.Bundle
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.NavigationConstants
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.view.visualstates.showSaveEmptyNoteDialog
import com.spaceo.afedyanov.space_otest.view.visualstates.showSaveExitDialog
import kotlinx.android.synthetic.main.content_note_add_edit.*

class NoteAddEditActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_edit)
        setResult(RESULT_CANCELED)
    }

    override fun setupLayout() {
        title = getString(R.string.select_language)
        saveButton.setOnClickListener({ checkAndSaveNote() })
        cancelButton.setOnClickListener({ finish() })
        noteNameEditText.setText(getCurrentNote().name)
    }

    fun getCurrentNote(): Note {
        if (intent.action.equals(NavigationConstants.Actions.ACTION_CREATE_NOTE)) {
            return Note()
        } else
            return intent.getSerializableExtra(NavigationConstants.Keys.NOTE_KEY) as Note
    }

    fun checkAndSaveNote() {
        if (noteNameEditText.text.toString().trim().isNullOrEmpty())
            showSaveEmptyNoteDialog()
        else
            finishWithOkResult()
    }

    fun finishWithOkResult() {
        val resultIntent = Intent()
        val note = getCurrentNote()
        note.name = noteNameEditText.text.toString()
        resultIntent.putExtra(NavigationConstants.Keys.NOTE_KEY, note)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    fun hasChanges(): Boolean {
        return !noteNameEditText.text.toString().equals(getCurrentNote().name)
    }

    override fun onBackPressed() {
        if (hasChanges())
            showSaveExitDialog()
        else
            super.onBackPressed()
    }
}
