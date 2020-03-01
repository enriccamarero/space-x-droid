package com.ecamarero.spacex.domain.launches.datasource

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import io.reactivex.Single

interface LaunchesDataSource {
    fun fetchLaunches(params: LaunchParams): Single<List<Launch>>
}

