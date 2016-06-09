package com.spaceo.afedyanov.space_otest.model.api

import com.spaceo.afedyanov.space_otest.model.entity.FeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiFeedService {

    @GET(ApiConstants.GET_FEED)
    fun getFeeds(): Call<FeedResponse>

}