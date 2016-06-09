package com.spaceo.afedyanov.space_otest.view.visualstates

import android.view.View
import com.spaceo.afedyanov.space_otest.view.fragment.FeedsFragment
import com.spaceo.afedyanov.space_otest.view.fragment.NotesFragment
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * Created by Alexandr on 07.06.2016.
 */

fun FeedsFragment.setNoFeedsState() {
    feedsList.visibility = View.INVISIBLE
    noFeedText.visibility = View.VISIBLE
    progress.visibility = View.INVISIBLE
}

fun FeedsFragment.setHasFeedsState() {
    feedsList.visibility = View.VISIBLE
    noFeedText.visibility = View.INVISIBLE
    progress.visibility = View.INVISIBLE
}

fun FeedsFragment.setLoadingState() {
    feedsList.visibility = View.INVISIBLE
    noFeedText.visibility = View.INVISIBLE
    progress.visibility = View.VISIBLE
}