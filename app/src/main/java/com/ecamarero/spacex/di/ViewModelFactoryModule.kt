package com.ecamarero.spacex.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindsViewModelFactory(factory: SpaceXViewModelProviderFactory): ViewModelProvider.Factory
}