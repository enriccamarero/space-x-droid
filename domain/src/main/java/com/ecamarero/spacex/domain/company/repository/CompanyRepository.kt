package com.ecamarero.spacex.domain.company.repository

import com.ecamarero.spacex.domain.company.datasource.CompanyInfoDataSource
import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import io.reactivex.Single
import javax.inject.Inject

class CompanyRepository @Inject internal constructor(
    private val companyInfoDataSource: CompanyInfoDataSource
) {
    fun getCompanyInfo(): Single<CompanyInfo> = companyInfoDataSource.fetchCompanyInfo()
}