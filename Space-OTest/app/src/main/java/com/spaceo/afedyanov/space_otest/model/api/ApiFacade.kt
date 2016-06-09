package com.spaceo.afedyanov.space_otest.model.api

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class ApiFacade {
    var apiFeedService: ApiFeedService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        apiFeedService = retrofit.create(ApiFeedService::class.java)
    }
}