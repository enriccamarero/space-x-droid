package com.ecamarero.spacex.ui.launches

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.model.LaunchUI
import com.ecamarero.spacex.ui.launches.widget.FilterDialog
import com.ecamarero.spacex.ui.launches.widget.LaunchAdapter
import com.ecamarero.spacex.ui.launches.widget.LinksDialog
import com.ecamarero.spacex.ui.utils.observeNonNull
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_space_x.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.empty_state_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import javax.inject.Inject


class LaunchesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LaunchesViewModel

    private val launchAdapter: LaunchAdapter by lazy {
        LaunchAdapter {
            LinksDialog.newInstance(it).show(supportFragmentManager, LinksDialog.TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LaunchesViewModel::class.java)
        super.onCreate(savedInstanceState)
        viewModel.loadLaunches()
        setContentView(R.layout.activity_space_x)

        launch_list.adapter = launchAdapter
        launch_list.layoutManager = LinearLayoutManager(this)

        observeNonNull(viewModel.launchesLiveData, { state ->
            renderList(state.launches)
            renderLoading(state.loading)
            renderError(state.error)
        })

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_filter -> {
                    FilterDialog.newInstance().show(supportFragmentManager, FilterDialog.TAG)
                    true
                }
                else -> false
            }
        }

        empty_button.setOnClickListener {
            FilterDialog.newInstance().show(supportFragmentManager, FilterDialog.TAG)
        }
    }

    private fun renderList(launches: List<LaunchUI>?) {
        launches?.let {
            launchAdapter.submitList(it)
            empty.visibility = if (launches.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun renderError(error: Throwable?) {
        error?.let {
            Snackbar
                .make(root, "Something is not working properly", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { viewModel.loadLaunches() }
                .show()
        }
    }
}