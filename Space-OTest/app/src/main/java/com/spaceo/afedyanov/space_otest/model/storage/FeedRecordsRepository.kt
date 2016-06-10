package com.spaceo.afedyanov.space_otest.model.storage

import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import nl.qbusict.cupboard.DatabaseCompartment

class FeedRecordsRepository(private val databaseCompartment: DatabaseCompartment) {

    fun getCachedFeeds(): MutableList<FeedRecord> {
        return databaseCompartment.query<FeedRecord>(FeedRecord::class.java).list()
    }

    fun setCachedFeeds(feeds: MutableList<FeedRecord>) {
        clearCachedFeeds()
        databaseCompartment.put(feeds)
    }

    fun clearCachedFeeds() {
        databaseCompartment.delete(FeedRecord::class.java, "_id > ?", "0")
    }
}