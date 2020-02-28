package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchesCoroutinesDataSource
import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.client.HttpClientModule
import com.ecamarero.spacex.network.launches.LaunchResponse
import com.ecamarero.spacex.network.launches.LaunchResponseMapper
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.reactivex.Single
import kotlinx.coroutines.runBlocking

class LaunchesDataSourceImpl(
    private val httpClient: HttpClient
) : LaunchesRxDataSource, LaunchesCoroutinesDataSource {

    override suspend fun fetchAllLaunchesSuspend(): List<Launch> {
        return fetchAllLaunches().map(LaunchResponseMapper::map)
    }

    override fun fetchAllLaunchesSingle(): Single<List<Launch>> {
        return Single.create {
            it.onSuccess(
                runBlocking {
                    fetchAllLaunchesSuspend()
                }
            )
        }
    }

    private suspend fun fetchAllLaunches(): List<LaunchResponse> {
        return httpClient.get(
            path = LAUNCHES
        )
    }

    companion object {
        const val LAUNCHES: String = "v3/launches"
    }
}

