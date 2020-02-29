package com.ecamarero.spacex.ui.launches

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.widget.LaunchAdapter
import com.ecamarero.spacex.ui.utils.observeNonNull
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_space_x.*
import javax.inject.Inject

class LaunchesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LaunchesViewModel
    private val launchAdapter: LaunchAdapter =
        LaunchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)
        viewModel.loadLaunches()
        setContentView(R.layout.activity_space_x)
        launch_list.adapter = launchAdapter
        launch_list.layoutManager = LinearLayoutManager(this)
        observeNonNull(viewModel.launchesLiveData, {
            it.launches?.let {
                launchAdapter.submitList(it)
            }
        })
    }
}