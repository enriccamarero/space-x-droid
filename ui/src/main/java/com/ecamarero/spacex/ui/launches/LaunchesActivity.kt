package com.ecamarero.spacex.ui.launches

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.model.LaunchUI
import com.ecamarero.spacex.ui.launches.widget.CompanyInfoDialog
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
        setContentView(R.layout.activity_space_x)
        setUpRecyclerView()
        observeLaunches()
        setListeners()
    }

    private fun setUpRecyclerView() {
        launch_list.adapter = launchAdapter
        launch_list.layoutManager = LinearLayoutManager(this)
    }

    private fun setListeners() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_filter -> {
                    openFilter()
                    true
                }
                R.id.action_info -> {
                    openCompanyInfo()
                    true
                }
                else -> false
            }
        }

        empty_button.setOnClickListener {
            openFilter()
        }
    }

    private fun openFilter() {
        FilterDialog.newInstance().show(supportFragmentManager, FilterDialog.TAG)
    }

    private fun openCompanyInfo() {
        CompanyInfoDialog.newInstance().show(supportFragmentManager, CompanyInfoDialog.TAG)
    }

    private fun observeLaunches() {
        observeNonNull(viewModel.launchesLiveData, { state ->
            renderLaunchesList(state.launches)
            renderLaunchesLoading(state.loading)
            renderLaunchesError(state.error)
        })
    }

    private fun renderLaunchesList(launches: List<LaunchUI>?) {
        launches?.let {
            launchAdapter.submitList(it) {
                launch_list.scrollToPosition(0)
            }
            empty.visibility = if (launches.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun renderLaunchesLoading(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun renderLaunchesError(error: Throwable?) {
        error?.let {
            Snackbar
                .make(root, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry_prompt)) { viewModel.loadLaunches() }
                .show()
        }
    }
}