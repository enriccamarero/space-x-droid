package com.ecamarero.spacex.ui.launches.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ecamarero.spacex.ui.R
import com.ecamarero.spacex.ui.launches.LaunchesViewModel
import com.ecamarero.spacex.ui.utils.observeNonNull
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.company_info_layout.*
import javax.inject.Inject

class CompanyInfoDialog : BottomSheetDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LaunchesViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(LaunchesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.company_info_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    private fun setObserver() {
        observeNonNull(viewModel.companyInfoLiveData, { state ->
            renderCompanyInfo(state.companyInfo)
            renderCompanyLoading(state.loading)
            renderCompanyError(state.error)
        })
    }

    private fun renderCompanyInfo(companyInfo: String?) {
        companyInfo?.let {
            company_info_text.text = companyInfo
        }
        company_info_text.setOnClickListener(null)
    }

    private fun renderCompanyLoading(loading: Boolean) {
        company_info_loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun renderCompanyError(error: Throwable?) {
        error?.let {
            company_info_text.text = getString(R.string.company_info_error_message)
            company_info_text.setOnClickListener { viewModel.loadCompanyInfo() }
        }
    }

    companion object {
        fun newInstance(): CompanyInfoDialog = CompanyInfoDialog()

        internal val TAG = CompanyInfoDialog::class.java.simpleName
    }

}