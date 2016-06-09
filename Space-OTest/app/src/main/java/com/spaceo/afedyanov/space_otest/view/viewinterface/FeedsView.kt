package com.spaceo.afedyanov.space_otest.view.viewinterface

import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord

/**
 * Created by Alexandr on 09.06.2016.
 */
interface FeedsView {

    fun setFeeds(feeds: MutableList<FeedRecord>)

    fun setLoading()

    fun setRefreshing()

}