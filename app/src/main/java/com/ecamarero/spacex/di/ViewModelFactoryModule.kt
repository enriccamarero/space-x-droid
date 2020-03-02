package com.ecamarero.spacex.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ecamarero.spacex.ui.launches.LaunchesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindsViewModelFactory(factory: SpaceXViewModelProviderFactory): ViewModelProvider.Factory
}