package com.ecamarero.spacex.ui.launches.widget

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.LaunchesViewModel
import com.ecamarero.spacex.ui.launches.Sorting
import com.ecamarero.spacex.ui.utils.hideKeyboard
import com.ecamarero.spacex.ui.utils.observeNonNull
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_filter_dialog.*
import javax.inject.Inject

class FilterDialog : BottomSheetDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LaunchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LaunchesViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        viewModel.onYearAdded(text_input_text_years.text.toString())
        super.onDismiss(dialog)
    }

    private fun setObserver() {
        observeNonNull(viewModel.filterLiveData) {
            showYears(it.years)
            showSorting(it.sorting)
            showOnlySuccessful(it.onlySuccessfulLaunches)
        }
    }

    private fun setListeners() {
        text_input_text_years.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            v?.text.let { inputText ->
                viewModel.onYearAdded(inputText.toString())
                v?.text = ""
                v?.hideKeyboard()
            }
            true
        }

        sorting_text.setOnClickListener {
            viewModel.onSortingChanged()
        }

        successful_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.onOnlySuccessfulLaunchesChanged(isChecked)
        }
    }


    private fun showOnlySuccessful(checked: Boolean) {
        successful_switch.isChecked = checked
    }

    private fun showSorting(sorting: Sorting) {
        sorting_text.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0, 0, if (sorting == Sorting.Ascending) {
                R.drawable.ic_arrow_upward_black_24dp
            } else {
                R.drawable.ic_arrow_downward_black_24dp
            }, 0
        )
    }

    private fun showYears(years: Collection<String>) {
        years_chip_group.removeAllViews()
        years.forEach {
            years_chip_group.addView(
                Chip(requireContext()).apply {
                    text = it
                    isCloseIconVisible = true
                    setOnCloseIconClickListener {
                        viewModel.removeYear((it as Chip).text.toString())
                    }
                }
            )
        }
    }

    companion object {
        fun newInstance(): FilterDialog =
            FilterDialog()

        internal val TAG = FilterDialog::class.java.simpleName
    }

}