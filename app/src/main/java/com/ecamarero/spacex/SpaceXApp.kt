package com.ecamarero.spacex

import com.ecamarero.spacex.di.DaggerSpaceXAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SpaceXApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerSpaceXAppComponent.create()
    }
}
