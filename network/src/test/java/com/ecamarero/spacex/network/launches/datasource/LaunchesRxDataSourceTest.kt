package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.launches.HttpTestClientModule
import com.ecamarero.spacex.network.launches.LAUNCHES_URL
import com.ecamarero.spacex.network.utils.RxImmediateSchedulerRule
import com.ecamarero.spacex.network.utils.withExpectedUrl
import io.ktor.client.features.ResponseException
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesRxDataSourceTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    val clientModule = HttpTestClientModule()

    private lateinit var dataSource: LaunchesRxDataSource

    @Before
    fun setUp() {
        dataSource = LaunchesDataSourceImpl(clientModule.providesHttpClient())
    }

    @Test
    fun `Fetching all launches returns a list of launches`() {
        clientModule.success = true
        clientModule.withExpectedUrl(LAUNCHES_URL)
        dataSource
            .fetchAllLaunchesSingle()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }
            .awaitTerminalEvent()
    }

    @Test
    fun `On fetching error, error is passed downstream`() {
        clientModule.success = false
        dataSource
            .fetchAllLaunchesSingle()
            .test()
            .assertNotComplete()
            .assertError {
                it is ResponseException
            }
            .awaitTerminalEvent()
    }

    @Test
    fun `Passing params modifies the request`() {
        clientModule.withExpectedUrl("https://api.spacexdata.com/v3/launches?launch_year=20128")
        dataSource
            .fetchAllLaunchesSingle(LaunchParams(
                launchYear = 2018
            ))
            .test()
    }
}