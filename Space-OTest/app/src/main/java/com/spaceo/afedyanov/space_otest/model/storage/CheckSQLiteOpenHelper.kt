package com.spaceo.afedyanov.space_otest.model.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import com.spaceo.afedyanov.space_otest.model.entity.Note
import nl.qbusict.cupboard.CupboardFactory.cupboard

class CheckSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, CheckSQLiteOpenHelper.DB_NAME, null, CheckSQLiteOpenHelper.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        cupboard().withDatabase(db).createTables()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        cupboard().withDatabase(db).upgradeTables()

    }

    companion object {
        private val DB_NAME = "space-o.db"
        private val DB_VERSION = 2

        init {
            cupboard().register<Note>(Note::class.java)
            cupboard().register<FeedRecord>(FeedRecord::class.java)
        }
    }
}
