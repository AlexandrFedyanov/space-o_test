package com.retechlabs.ahold.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        private val DB_VERSION = 1

        init {
           // cupboard().register<Message>(Message::class.java)
        }
    }
}
