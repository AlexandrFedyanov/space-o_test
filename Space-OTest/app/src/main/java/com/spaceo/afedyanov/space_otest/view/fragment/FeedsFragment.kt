package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.model.entity.FeedRecord
import com.spaceo.afedyanov.space_otest.model.storage.Storage
import com.spaceo.afedyanov.space_otest.presenter.FeedsPresenterImpl
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.FeedsPresenter
import com.spaceo.afedyanov.space_otest.presenter.presenterinrerface.PresenterFactory
import com.spaceo.afedyanov.space_otest.utils.FeedPresenterCache
import com.spaceo.afedyanov.space_otest.view.adapter.FeedsAdapter
import com.spaceo.afedyanov.space_otest.view.viewinterface.FeedsView
import com.spaceo.afedyanov.space_otest.view.visualstates.setHasFeedsState
import com.spaceo.afedyanov.space_otest.view.visualstates.setLoadingState
import com.spaceo.afedyanov.space_otest.view.visualstates.setNoFeedsState
import com.spaceo.afedyanov.space_otest.view.visualstates.setRefreshingState
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * Created by Alexandr on 06.06.2016.
 */
class FeedsFragment : BaseFragment(), FeedsView {

    private lateinit var adapter: FeedsAdapter
    private lateinit var feedsPresenter: FeedsPresenter

    private val presenterFactory: PresenterFactory<FeedsPresenter> = object: PresenterFactory<FeedsPresenter> {
        override fun createPresenter(): FeedsPresenter {
            return FeedsPresenterImpl(FeedsFragment::class.java.simpleName, Storage.get(this@FeedsFragment.activity))
        }
    }

    companion object {
        fun newInstance() : FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_service, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        feedsPresenter.detachView()
    }

    override fun setupLayout() {
        feedsPresenter = FeedPresenterCache.instance.getPresenter(FeedsFragment::class.java.simpleName, presenterFactory)
        feedsPresenter.attachView(this)
        adapter = FeedsAdapter(mutableListOf())
        feedsList.adapter = adapter
        pullToRefresh.setOnRefreshListener {
            feedsPresenter.refreshFeeds()
        }
        feedsPresenter.getFeeds()
    }

    override fun scrollContentToTop() {
        if ((feedsList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 15)
            feedsList.scrollToPosition(15)
        feedsList.smoothScrollToPosition(0)
    }

    override fun setFeeds(feeds: MutableList<FeedRecord>) {
        if (feeds.size == 0) {
            setNoFeedsState()
        } else {
            setHasFeedsState()
            adapter.clear()
            feeds.forEach {
                adapter.add(it)
            }
        }
    }

    override fun setLoading() {
        setLoadingState()
    }

    override fun setRefreshing() {
        setRefreshingState()
    }
}