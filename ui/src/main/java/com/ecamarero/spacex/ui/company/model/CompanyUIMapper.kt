package com.ecamarero.spacex.ui.company.model

import com.ecamarero.spacex.domain.company.model.CompanyInfo
import kotlin.math.pow

object CompanyUIMapper {
    fun toCompanyString(it: CompanyInfo): String {
        return COMPANY_TEMPLATE
            .replace(COMPANY_NAME_TAG, it.name)
            .replace(FOUNDER_NAME_TAG, it.founder)
            .replace(YEAR_TAG, it.founded)
            .replace(EMPLOYEES_TAG, it.employees)
            .replace(LAUNCH_SITES_TAG, it.launchSites)
            .replace(
                VALUATION_TAG, "${valuationToString(
                    it.valuation
                )} billion")
    }

    private fun valuationToString(it: Number) = it.toLong() / 10F.pow(9)

    private const val COMPANY_TEMPLATE =
        "{companyName} was founded by {founderName} in {year}. " +
                "It has now {employees} employees, " +
                "{launch_sites} launch sites, " +
                "and is valued at USD {valuation}"

    private const val COMPANY_NAME_TAG = "{companyName}"
    private const val FOUNDER_NAME_TAG = "{founderName}"
    private const val YEAR_TAG = "{year}"
    private const val EMPLOYEES_TAG = "{employees}"
    private const val LAUNCH_SITES_TAG = "{launch_sites}"
    private const val VALUATION_TAG = "{valuation}"
}