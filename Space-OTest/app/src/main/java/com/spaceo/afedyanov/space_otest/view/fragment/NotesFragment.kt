package com.spaceo.afedyanov.space_otest.view.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.View
import com.spaceo.afedyanov.space_otest.listener.NotesFragmentListener

/**
 * Created by Alexandr on 06.06.2016.
 */
class NotesFragment: Fragment() {

    private var listener: NotesFragmentListener? = null

    companion object {
        fun newInstance() : NotesFragment {
            return NotesFragment()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

    fun setListener(listener: NotesFragmentListener) {
        this.listener = listener;
    }
}