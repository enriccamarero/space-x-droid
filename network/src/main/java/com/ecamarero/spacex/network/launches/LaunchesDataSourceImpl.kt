package com.ecamarero.spacex.network.launches

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.launches.model.LaunchParamsMapper
import com.ecamarero.spacex.network.launches.model.LaunchResponseMapper
import io.reactivex.Single

class LaunchesDataSourceImpl(
    private val launchesApi: LaunchesApi
) : LaunchesRxDataSource {

    override fun fetchAllLaunchesSingle(params: LaunchParams?): Single<List<Launch>> {
        val requestParams = LaunchParamsMapper.toRequest(params)
        return launchesApi.launches(
            launchYear = requestParams.launchYear,
            launchSuccess = requestParams.launchSuccess,
            order = requestParams.order
        )
            .map(LaunchResponseMapper::toLaunches)
    }
}

