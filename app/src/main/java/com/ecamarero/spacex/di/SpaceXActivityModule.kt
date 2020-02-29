package com.ecamarero.spacex.di

import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.ui.launches.LaunchesActivity
import com.ecamarero.spacex.ui.launches.LaunchesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SpaceXActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSpaceXActivity(): LaunchesActivity

    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel::class)
    internal abstract fun bindsSpaceXViewModel(viewModel: LaunchesViewModel): ViewModel
}