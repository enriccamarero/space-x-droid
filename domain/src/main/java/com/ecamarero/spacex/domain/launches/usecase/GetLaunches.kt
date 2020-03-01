package com.ecamarero.spacex.domain.launches.usecase

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetLaunches @Inject internal constructor(
    private val launchesRepository: LaunchesRepository
) {
    operator fun invoke(
        order: LaunchParams.Order = LaunchParams.Order.Ascending,
        onlySuccessful: Boolean = false,
        launchYear: Collection<Int> = emptyList()
    ): Observable<List<Launch>> {
        return launchesRepository
            .getLaunches(
                LaunchParams(
                    launchYears = launchYear,
                    order = order,
                    onlySuccessful = onlySuccessful
                )
            )
            .toObservable()
            .subscribeOn(Schedulers.io())
    }
}
