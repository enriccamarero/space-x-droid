package com.ecamarero.spacex.domain.launches.repository

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import io.reactivex.Single
import javax.inject.Inject

class LaunchesRepository @Inject internal constructor(
    private val launchesDataSource: LaunchesDataSource
) {
    fun getLaunches(params: LaunchParams): Single<List<Launch>> =
        launchesDataSource.fetchLaunches(params)
}