package com.ecamarero.spacex.network.launches

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.network.launches.model.LaunchRequestMapper
import com.ecamarero.spacex.network.launches.model.LaunchResponseMapper
import io.reactivex.Single
import javax.inject.Inject

class LaunchesDataSourceImpl @Inject constructor(
    private val launchesApi: LaunchesApi
) : LaunchesDataSource {

    override fun fetchLaunches(params: LaunchParams): Single<List<Launch>> = Single.defer {
        val requests = LaunchRequestMapper.mapToRequests(params)
        val calls = requests.map {
            launchesApi.launches(
                launchYear = it.launchYear,
                launchSuccess = it.launchSuccess,
                order = it.order
            )
        }
        Single.concat(calls)
            .map(LaunchResponseMapper::toLaunches)
            .reduce(
                mutableListOf(),
                { accumulator: MutableList<Launch>, data: List<Launch> ->
                    accumulator.addAll(data)
                    accumulator
                }
            )
    }
}

