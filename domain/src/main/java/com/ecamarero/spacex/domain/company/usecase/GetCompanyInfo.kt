package com.ecamarero.spacex.domain.company.usecase

import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.company.repository.CompanyRepository
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCompanyInfo @Inject internal constructor(
    private val companyRepository: CompanyRepository
) {
    operator fun invoke(): Observable<CompanyInfo> {
        return companyRepository
            .getCompanyInfo()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }
}
