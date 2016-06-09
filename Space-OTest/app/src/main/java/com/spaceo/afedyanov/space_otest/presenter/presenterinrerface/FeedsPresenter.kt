package com.spaceo.afedyanov.space_otest.presenter.presenterinrerface

import com.spaceo.afedyanov.space_otest.view.viewinterface.FeedsView

/**
 * Created by Alexandr on 09.06.2016.
 */
interface FeedsPresenter {

    fun attachView(view: FeedsView)

    fun detachView()

    fun getFeeds()

    fun refreshFeeds()
}