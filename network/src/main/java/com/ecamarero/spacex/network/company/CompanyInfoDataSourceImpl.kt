package com.ecamarero.spacex.network.company

import com.ecamarero.spacex.domain.company.datasource.CompanyInfoDataSource
import com.ecamarero.spacex.domain.company.model.CompanyInfo
import io.reactivex.Single
import javax.inject.Inject

class CompanyInfoDataSourceImpl @Inject constructor(
    private val companyInfoApi: CompanyInfoApi
) : CompanyInfoDataSource {
    override fun fetchCompanyInfo(): Single<CompanyInfo> {
        return companyInfoApi.info().map {
            CompanyInfo(
                name = it.name,
                founder = it.founder,
                founded = it.founded,
                employees = it.employees,
                launchSites = it.launchSites,
                valuation = it.valuation
            )
        }
    }

}