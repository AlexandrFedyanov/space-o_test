package com.spaceo.afedyanov.space_otest.view.visualstates

import android.view.View
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by Alexandr on 07.06.2016.
 */

fun NotesFragment.setNoNotesState() {
    notesList.visibility = View.INVISIBLE
    noNotesText.visibility = View.VISIBLE
}

fun NotesFragment.setHasNotesState() {
    notesList.visibility = View.VISIBLE
    noNotesText.visibility = View.INVISIBLE
}