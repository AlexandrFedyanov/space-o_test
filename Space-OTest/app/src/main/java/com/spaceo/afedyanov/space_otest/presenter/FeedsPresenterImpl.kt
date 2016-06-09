package com.spaceo.afedyanov.space_otest.presenter

import com.spaceo.afedyanov.space_otest.model.api.ApiFacade
import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import com.spaceo.afedyanov.space_otest.model.entity.FeedResponse
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.FeedsPresenter
import com.spaceo.afedyanov.space_otest.utils.FeedPresenterCache
import com.spaceo.afedyanov.space_otest.view.viewinterface.FeedsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexandr on 09.06.2016.
 */
class FeedsPresenterImpl(private val tag:String): FeedsPresenter {

    private val apiFacade = ApiFacade()
    private var isLoading = false
    private var isRefreshing = false
    private var feedRecords: MutableList<FeedRecord> = mutableListOf()

    private var view: FeedsView? = null

    override fun attachView(view: FeedsView) {
        this.view = view
        if (isLoading) view.setLoading()
        if (isRefreshing) view.setRefreshing()
    }

    override fun detachView() {
       view = null
    }

    override fun getFeeds() {
        if (feedRecords.size > 0) {
            view?.setFeeds(feedRecords)
            if (isLoading) view?.setLoading()
            if (isRefreshing) view?.setRefreshing()
        } else if (!isLoading && !isRefreshing) {
            isLoading = true
            view?.setLoading()
            loadFeeds()
        }

    }

    override fun refreshFeeds() {
        if (!isLoading && !isRefreshing) {
            isRefreshing = true
            view?.setRefreshing()
            loadFeeds()
        }
    }

    private fun loadFeeds() {
        apiFacade.apiFeedService.getFeeds().enqueue(object: Callback<FeedResponse?> {
            override fun onResponse(call: Call<FeedResponse?>?, response: Response<FeedResponse?>?) {
                if (response != null && response.isSuccessful
                        && response.body()!= null && response.body()!!.feedRecords != null) {
                    feedRecords = response.body()!!.feedRecords
                } else
                    feedRecords = mutableListOf()
                view?.setFeeds(feedRecords)
                isLoading = false
                isRefreshing = false
                cacheData()
            }

            override fun onFailure(call: Call<FeedResponse?>?, t: Throwable?) {
                isLoading = false
                view?.setFeeds(feedRecords)
                isLoading = false
                isRefreshing = false
                view ?: FeedPresenterCache.instance.removePresenter(tag)
            }
        })
    }

    fun getFeedsFromCache(): MutableList<FeedRecord> {
        return mutableListOf()
    }

    private fun cacheData() {
        view ?: FeedPresenterCache.instance.removePresenter(tag)
    }
}