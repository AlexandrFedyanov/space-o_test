package com.spaceo.afedyanov.space_otest.model.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import nl.qbusict.cupboard.CupboardFactory.cupboard
import nl.qbusict.cupboard.DatabaseCompartment

class Storage(private val context: Context) {

    private lateinit var openHelper: CheckSQLiteOpenHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var databaseCompartment: DatabaseCompartment
    lateinit var notesRepository: NotesRepository
    lateinit var feedRecordsRepository: FeedRecordsRepository

    init {
        openHelper = CheckSQLiteOpenHelper(context)
        database = openHelper.writableDatabase
        databaseCompartment = cupboard().withDatabase(database)
        notesRepository = NotesRepository(databaseCompartment)
        feedRecordsRepository = FeedRecordsRepository(databaseCompartment)
    }

    companion object {
        private var instance: Storage? = null
        private val INIT_LOCK = Object()
        fun get(context: Context): Storage? {
            if (instance == null) {
                synchronized (INIT_LOCK) {
                    if (instance == null) {
                        instance = Storage(context)
                    }
                }
            }

            return instance
        }
    }

}