package com.ecamarero.spacex.domain.launches.repository

import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import io.reactivex.Single

internal class LaunchesRepository(
    private val launchesRxDataSource: LaunchesRxDataSource
) {
    fun getAllLaunches(): Single<List<Launch>> = launchesRxDataSource.fetchAllLaunchesSingle()
}