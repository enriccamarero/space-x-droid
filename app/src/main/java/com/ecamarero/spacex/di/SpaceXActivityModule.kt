package com.ecamarero.spacex.di

import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.ui.launches.FilterDialog
import com.ecamarero.spacex.ui.launches.LaunchesActivity
import com.ecamarero.spacex.ui.launches.LaunchesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class SpaceXActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSpaceXActivity(): LaunchesActivity


    @ContributesAndroidInjector
    internal abstract fun contributesFilterDialogFragment(): FilterDialog

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel::class)
    internal abstract fun bindsSpaceXViewModel(viewModel: LaunchesViewModel): ViewModel
}