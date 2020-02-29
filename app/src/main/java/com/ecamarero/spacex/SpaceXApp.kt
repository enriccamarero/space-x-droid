package com.ecamarero.spacex

import com.ecamarero.spacex.di.DaggerSpaceXAppComponent
import com.ecamarero.spacex.di.SpaceXAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SpaceXApp : DaggerApplication() {

    private val spaceXAppComponent: SpaceXAppComponent by lazy {
        DaggerSpaceXAppComponent.create()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return spaceXAppComponent
    }
}
