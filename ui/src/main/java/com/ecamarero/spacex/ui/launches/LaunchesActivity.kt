package com.ecamarero.spacex.ui.launches

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.widget.LaunchAdapter
import com.ecamarero.spacex.ui.utils.observeNonNull
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_space_x.*
import javax.inject.Inject

class LaunchesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LaunchesViewModel

    private val launchAdapter: LaunchAdapter by lazy { LaunchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)
        super.onCreate(savedInstanceState)
        viewModel.loadLaunches()
        setContentView(R.layout.activity_space_x)
        launch_list.adapter = launchAdapter
        launch_list.layoutManager = LinearLayoutManager(this)
        observeNonNull(viewModel.launchesLiveData, {
            it.launches?.let {
                launchAdapter.submitList(it)
            }
        })
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_filter -> {
                    FilterDialog.newInstance().show(supportFragmentManager, "TAG")
                    true
                }
                else -> false
            }
        }
    }

}

class FilterDialog : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance(): FilterDialog = FilterDialog()
    }

}