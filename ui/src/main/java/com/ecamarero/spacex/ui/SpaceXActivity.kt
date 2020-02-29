package com.ecamarero.spacex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

class SpaceXActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var spaceXViewModel: SpaceXViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        spaceXViewModel = ViewModelProvider(this, viewModelFactory).get(SpaceXViewModel::class.java)
        spaceXViewModel.loadLaunches()
        setContentView(R.layout.activity_space_x)
    }
}
