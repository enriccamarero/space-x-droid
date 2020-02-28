package com.ecamarero.spacex.domain.launches.datasource

import com.ecamarero.spacex.domain.launches.model.Launch

interface LaunchesCoroutinesDataSource {
    suspend fun fetchAllLaunchesSuspend(): List<Launch>
}