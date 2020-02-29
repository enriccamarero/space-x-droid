package com.ecamarero.spacex.di

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.network.client.HttpClientModule
import com.ecamarero.spacex.network.launches.LaunchesDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class LaunchesDataSourceModule {

    @Provides
    internal fun providesLaunchesDataSource(): LaunchesDataSource {
        return LaunchesDataSourceImpl(HttpClientModule.providesLaunchesApi())
    }
}