package com.spaceo.afedyanov.space_otest.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.model.api.ApiFacade
import com.spaceo.afedyanov.space_otest.model.entity.FeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexandr on 06.06.2016.
 */
class FeedsFragment : BaseFragment() {


    companion object {
        fun newInstance() : FeedsFragment {
            return FeedsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_service, container, false)
    }

    override fun setupLayout() {
        val apiFacade = ApiFacade()
        apiFacade.apiFeedService.getFeeds().enqueue(object: Callback<FeedResponse?> {
            override fun onResponse(call: Call<FeedResponse?>?, response: Response<FeedResponse?>?) {
                if (response != null && response.isSuccessful) {
                    response.body()?.feedRecords?.forEach {
                        Log.d("feed date", it.date)
                        Log.d("feed text", it.text)
                    }
                }
            }

            override fun onFailure(call: Call<FeedResponse?>?, t: Throwable?) {
                Log.d("feed", t.toString())
            }
        })
    }

}