package com.spaceo.afedyanov.space_otest.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.*
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.appnavigation.showCreateNote
import com.spaceo.afedyanov.space_otest.appnavigation.showEditNote
import com.spaceo.afedyanov.space_otest.listener.NotesAdapterItemsClickListener
import com.spaceo.afedyanov.space_otest.model.entity.Note
import com.spaceo.afedyanov.space_otest.model.storage.Storage
import com.spaceo.afedyanov.space_otest.presenter.NotesPresenterImpl
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.NotesPresenter
import com.spaceo.afedyanov.space_otest.view.adapter.NotesAdapter
import com.spaceo.afedyanov.space_otest.view.viewinterface.NotesView
import com.spaceo.afedyanov.space_otest.view.visualstates.setHasNotesState
import com.spaceo.afedyanov.space_otest.view.visualstates.setNoNotesState
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by Alexandr on 06.06.2016.
 */
class NotesFragment: BaseFragment(), NotesView, ActionMode.Callback {

    private lateinit var adapter: NotesAdapter
    private lateinit var presenter: NotesPresenter
    private var actionMode: ActionMode? = null
    private var actionModeMenu: Menu? = null

    companion object {
        fun newInstance() : NotesFragment {
            return NotesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.setActivityResult(requestCode, resultCode, data)
    }

    override fun setupLayout() {
        presenter = NotesPresenterImpl(Storage.get(activity))
        presenter.attachView(this)
        adapter = NotesAdapter(mutableListOf())
        adapter.setNotesClickListener(object: NotesAdapterItemsClickListener {
            override fun onNoteClick(note: Note, position: Int) {
                if (actionMode != null) {
                    toggleSelection(position)
                } else
                    presenter.editNoteClick(note)
            }

            override fun onNoteLongClick(position: Int) {
                if (actionMode != null) {
                    return
                }
                actionMode = activity.startActionMode(this@NotesFragment)
                toggleSelection(position)
            }

            override fun onNoteCheckClick(note: Note, isChecked: Boolean) {
                presenter.checkNoteClick(note, isChecked)
            }
        })
        notesList.adapter = adapter
        presenter.getNotes()
    }

    override fun scrollContentToTop() {
        notesList.scrollToPosition(0)
    }

    override fun setNotes(notes: MutableList<Note>?) {
        notes ?: return
        adapter.clear()
        adapter.setData(notes)
        refreshNotesVisualState()
    }

    override fun addNote(note: Note) {
        adapter.add(note)
        refreshNotesVisualState()
    }

    override fun updateNote(note: Note) {
        adapter.updateItem(note)
    }

    override fun removeNote(note: Note) {
        adapter.remove(note)
        refreshNotesVisualState()
    }

    override fun removeNotes(notes: MutableList<Note>) {
        notes.forEach { adapter.remove(it) }
        refreshNotesVisualState()
    }

    fun refreshNotesVisualState() {
        if (adapter.itemCount == 0) {
            setNoNotesState()
        } else
            setHasNotesState()
    }

    fun onAddNoteClicked() {
        presenter.addNoteClick()
    }

    override fun openAddNoteScreen() {
        showCreateNote()
    }

    override fun openEdinNoteScreen(note: Note) {
        showEditNote(note)
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.menu_notes, menu)
        actionModeMenu = menu
        return true
    }


    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        val selectedNotes = adapter.getSelectedItems()
        when (item.itemId) {
            R.id.action_delete -> {
                presenter.removeNotesClick(selectedNotes)
                actionMode?.finish()
            }
            R.id.action_edit -> {
                presenter.editNoteClick(selectedNotes[0])
                actionMode?.finish()
            }
        }
        return false
    }

    private fun toggleSelection(idx: Int) {
        adapter.toggleSelection(idx)
        if (adapter.getSelectedItemsCount() == 0)
            actionMode?.finish()
        else {
            actionModeMenu?.findItem(R.id.action_edit)?.isVisible = adapter.getSelectedItemsCount() == 1
            val title = getString(R.string.selected_count, adapter.getSelectedItemsCount())
            actionMode?.title = title
        }
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        this.actionMode = null
        adapter.clearSelections()
    }
}