package com.ecamarero.spacex.domain.launches.usecase

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import com.ecamarero.spacex.domain.launches.usecase.LaunchParamsMapper.toLaunchParams
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetLaunches @Inject internal constructor(
    private val launchesRepository: LaunchesRepository
) {
    operator fun invoke(
        order: LaunchParams.Order,
        onlySuccessful: Boolean,
        launchYears: Collection<Int>
    ): Observable<List<Launch>> = launchesRepository
        .getLaunches(
            toLaunchParams(
                launchYears = launchYears,
                order = order,
                onlySuccessful = onlySuccessful
            )
        )
        .toObservable()
        .subscribeOn(Schedulers.io())
}

