package com.ecamarero.spacex.ui.company.model

internal data class CompanyInfoState(
    val companyInfo: String? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)