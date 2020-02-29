package com.ecamarero.spacex.network.client

import com.ecamarero.spacex.network.BuildConfig
import com.ecamarero.spacex.network.launches.LaunchesApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpClientModule {

    fun providesLaunchesApi(): LaunchesApi = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(LaunchesApi::class.java)
}