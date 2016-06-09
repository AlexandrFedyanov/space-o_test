package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.model.api.ApiFacade
import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import com.spaceo.afedyanov.space_otest.model.entity.FeedResponse
import com.spaceo.afedyanov.space_otest.view.adapter.FeedsAdapter
import com.spaceo.afedyanov.space_otest.view.visualstates.setHasFeedsState
import com.spaceo.afedyanov.space_otest.view.visualstates.setLoadingState
import com.spaceo.afedyanov.space_otest.view.visualstates.setNoFeedsState
import kotlinx.android.synthetic.main.fragment_service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexandr on 06.06.2016.
 */
class FeedsFragment : BaseFragment() {

    private lateinit var adapter: FeedsAdapter

    companion object {
        fun newInstance() : FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_service, container, false)
    }

    override fun setupLayout() {
        adapter = FeedsAdapter(mutableListOf())
        feedsList.adapter = adapter
        pullToRefresh.setOnRefreshListener {
            pullToRefresh.isRefreshing = true
            getFeeds()
        }
        showLoading()
        getFeeds()
    }

    fun getFeeds() {
        val apiFacade = ApiFacade()
        apiFacade.apiFeedService.getFeeds().enqueue(object: Callback<FeedResponse?> {
            override fun onResponse(call: Call<FeedResponse?>?, response: Response<FeedResponse?>?) {
                if (response != null && response.isSuccessful) {
                    setFeeds(response.body()?.feedRecords)
                }
            }

            override fun onFailure(call: Call<FeedResponse?>?, t: Throwable?) {
                setFeeds(null)
            }
        })
    }

    fun setFeeds(feeds: MutableList<FeedRecord>?) {
        pullToRefresh.isRefreshing = false
        if (feeds == null || feeds.size == 0) {
            setNoFeedsState()
        } else {
            setHasFeedsState()
            adapter.clear()
            feeds.forEach {
                adapter.add(it)
            }
        }
    }

    fun showLoading() {
        setLoadingState()
    }

}