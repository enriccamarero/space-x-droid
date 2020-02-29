package com.ecamarero.spacex.domain.usecase

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import io.reactivex.Observable

class GetLaunches internal constructor(private val launchesRepository: LaunchesRepository) {
    
    operator fun invoke(
        order: LaunchParams.Order = LaunchParams.Order.Ascending,
        launchSuccessful: Boolean? = null,
        launchYear: Int? = null
    ): Observable<Result<List<Launch>>> {
        return launchesRepository
            .getLaunches(
                LaunchParams(
                    launchYear = launchYear,
                    order = order,
                    launchSuccess = launchSuccessful
                )
            )
            .toObservable()
            .map { Result(it) }
            .onErrorReturn { Result(error = it) }
    }

    companion object {
        data class Result<T>(
            val data: T? = null,
            val error: Throwable? = null
        )
    }
}
