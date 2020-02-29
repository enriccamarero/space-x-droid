package com.ecamarero.spacex.network.launches

import com.ecamarero.spacex.network.launches.model.LaunchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LaunchesApi {

    @GET("v3/launches")
    fun launches(
        @Query("launch_year") launchYear: Int? = null,
        @Query("launch_success") launchSuccess: Boolean? = null,
        @Query("order") order: String? = null
    ): Single<List<LaunchResponse>>
}