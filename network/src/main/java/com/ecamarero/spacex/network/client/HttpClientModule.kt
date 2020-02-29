package com.ecamarero.spacex.network.client

import com.ecamarero.spacex.network.BuildConfig
import com.ecamarero.spacex.network.launches.LaunchesApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class HttpClientModule {

    fun providesLaunchesApi(): LaunchesApi = Retrofit
        .Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(LaunchesApi::class.java)
}