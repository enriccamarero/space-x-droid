package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchesCoroutinesDataSource
import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.launches.LaunchRequest
import com.ecamarero.spacex.network.launches.LaunchResponse
import com.ecamarero.spacex.network.launches.LaunchResponseMapper
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.reactivex.Single
import kotlinx.coroutines.runBlocking

class LaunchesDataSourceImpl(
    private val httpClient: HttpClient
) : LaunchesRxDataSource, LaunchesCoroutinesDataSource {

    override fun fetchAllLaunchesSingle(params: LaunchParams?): Single<List<Launch>> {
        return Single.create {
            it.onSuccess(
                runBlocking {
                    fetchAllLaunchesSuspend(params)
                }
            )
        }
    }

    override suspend fun fetchAllLaunchesSuspend(params: LaunchParams?): List<Launch> =
        fetchAllLaunches(LaunchParamsMapper.toRequest(params))
            .map(LaunchResponseMapper::toLaunch)

    private suspend fun fetchAllLaunches(params: LaunchRequest? = null): List<LaunchResponse> {
        return httpClient.get(
            path = LAUNCHES
        ) {
            params?.let { url.parameters.removeKeysWithNoEntries() }
        }
    }

    companion object {
        const val LAUNCHES: String = "v3/launches"
    }
}