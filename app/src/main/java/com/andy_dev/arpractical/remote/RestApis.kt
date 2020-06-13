package com.andy_dev.arpractical.remote

import com.andy_dev.arpractical.model.FactsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestApis {
    @GET(NetworkHelper.FACTS_JSON)
    fun callFactsListAPI(): Observable<FactsResponse>
}