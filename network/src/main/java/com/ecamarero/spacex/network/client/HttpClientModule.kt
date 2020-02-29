package com.ecamarero.spacex.network.client

import com.ecamarero.spacex.network.BuildConfig
import com.ecamarero.spacex.network.launches.LaunchesApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat.FULL
import java.time.format.DateTimeFormatter

object HttpClientModule {

    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"

    fun providesLaunchesApi(): LaunchesApi = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat(DATE_FORMAT).create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()
        .create(LaunchesApi::class.java)
}