package com.spaceo.afedyanov.space_otest.view.visualstates

import android.support.v7.app.AlertDialog
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.view.activity.NoteAddEditActivity

fun NoteAddEditActivity.showSaveExitDialog() {
    AlertDialog.Builder(this).setMessage(R.string.exit_dialog_message)
            .setPositiveButton(R.string.exit_dialog_ok, { dialogInterface, i -> checkAndSaveNote()})
            .setNegativeButton(R.string.exit_dialog_cancel, { dialogInterface, i -> finish()})
            .setCancelable(false)
            .show()
}

fun NoteAddEditActivity.showSaveEmptyNoteDialog() {
    AlertDialog.Builder(this).setMessage(R.string.empty_save_dialog_message)
            .setPositiveButton(R.string.empty_dialog_ok, { dialogInterface, i -> finishWithOkResult()})
            .setNegativeButton(R.string.empty_dialog_cancel, { dialogInterface, i -> dialogInterface.dismiss()})
            .setCancelable(false)
            .show()
}