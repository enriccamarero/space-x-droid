package com.ecamarero.spacex.di

import com.ecamarero.spacex.network.BuildConfig
import com.ecamarero.spacex.network.company.CompanyInfoApi
import com.ecamarero.spacex.network.launches.LaunchesApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class HttpClientModule {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
    }

    @Provides
    internal fun providesRetrofit(): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat(DATE_FORMAT).create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    fun providesLaunchesApi(retrofit: Retrofit): LaunchesApi = retrofit.create(LaunchesApi::class.java)

    @Provides
    fun providesCompanyInfoApi(retrofit: Retrofit): CompanyInfoApi = retrofit.create(CompanyInfoApi::class.java)
}