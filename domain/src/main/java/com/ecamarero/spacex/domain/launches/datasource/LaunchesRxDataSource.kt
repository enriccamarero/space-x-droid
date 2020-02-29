package com.ecamarero.spacex.domain.launches.datasource

import com.ecamarero.spacex.domain.launches.model.Launch
import io.reactivex.Single

interface LaunchesRxDataSource {
    fun fetchAllLaunchesSingle(params: LaunchParams? = null): Single<List<Launch>>
}

