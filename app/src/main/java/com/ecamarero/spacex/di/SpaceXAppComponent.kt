package com.ecamarero.spacex.di

import com.ecamarero.spacex.SpaceXApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        SpaceXActivityModule::class,
        ViewModelFactoryModule::class,
        LaunchesDataSourceModule::class,
        CompanyInfoDataSourceModule::class,
        HttpClientModule::class
    ]
)
interface SpaceXAppComponent : AndroidInjector<SpaceXApp>