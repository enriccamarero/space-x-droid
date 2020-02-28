package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.launches.HttpTestClientModule
import com.ecamarero.spacex.network.launches.utils.RxImmediateSchedulerRule
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
                it is Throwable
            }
            .awaitTerminalEvent()
    }
}