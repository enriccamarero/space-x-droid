package com.ecamarero.spacex.domain.company.datasource

import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import io.reactivex.Single

interface CompanyInfoDataSource {
    fun fetchCompanyInfo(): Single<CompanyInfo>
}

