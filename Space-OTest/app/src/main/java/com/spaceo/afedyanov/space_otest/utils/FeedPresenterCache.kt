package com.spaceo.afedyanov.space_otest.utils

import android.support.v4.util.SimpleArrayMap
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.FeedsPresenter
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.PresenterFactory

/**
 * Created by Alexandr on 09.06.2016.
 */
class FeedPresenterCache private constructor() {

    private object Holder { val INSTANCE = FeedPresenterCache() }

    private val presenters: SimpleArrayMap<String, FeedsPresenter> = SimpleArrayMap()

    companion object {
        val instance: FeedPresenterCache by lazy { Holder.INSTANCE }
    }

    fun getPresenter(tag: String, feedPresenterFactory: PresenterFactory<FeedsPresenter>): FeedsPresenter {
        var presenter: FeedsPresenter?
        presenter = presenters.get(tag)
        if (presenter == null) {
            presenter = feedPresenterFactory.createPresenter()
            presenters.put(tag, presenter)
        }
        return presenter
    }

    fun removePresenter(tag: String) {
        presenters.remove(tag)
    }
}