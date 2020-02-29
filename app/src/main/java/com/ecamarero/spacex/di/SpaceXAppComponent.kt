package com.ecamarero.spacex.di

import com.ecamarero.spacex.SpaceXApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        SpaceXActivityModule::class,
        LaunchesDataSourceModule::class
    ]
)
interface SpaceXAppComponent : AndroidInjector<SpaceXApp>