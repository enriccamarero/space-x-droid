package com.ecamarero.spacex.di

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.network.launches.LaunchesApi
import com.ecamarero.spacex.network.launches.LaunchesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class LaunchesDataSourceModule {

    @Binds
    internal abstract fun providesLaunchesDataSource(launchesDataSource: LaunchesDataSourceImpl): LaunchesDataSource
}