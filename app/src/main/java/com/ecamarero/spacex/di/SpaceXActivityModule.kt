package com.ecamarero.spacex.di

import androidx.lifecycle.ViewModel
import com.ecamarero.spacex.ui.SpaceXActivity
import com.ecamarero.spacex.ui.SpaceXViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SpaceXActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributesSpaceXActivity(): SpaceXActivity

    @Binds
    @IntoMap
    @ViewModelKey(SpaceXViewModel::class)
    internal abstract fun bindsSpaceXViewModel(viewModel: SpaceXViewModel): ViewModel
}