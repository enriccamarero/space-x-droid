package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.datasource.Order
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.network.client.HttpClientModule
import com.ecamarero.spacex.network.launches.LaunchesDataSourceImpl
import com.ecamarero.spacex.network.utils.RxImmediateSchedulerRule
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesRxDataSourceTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    val clientModule = HttpClientModule()

    private lateinit var dataSource: LaunchesRxDataSource

    @Before
    fun setUp() {
        dataSource =
            LaunchesDataSourceImpl(clientModule.providesLaunchesApi())
    }

    @Test
    fun `Fetching all launches returns a list of launches`() {
        dataSource
            .fetchAllLaunchesSingle()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }
    }

    @Test
    fun `Fetching all launches of a given year only returns those launches 1`() {
        val expectedYear = 2006
        val launchParams = LaunchParams(
            launchYear = expectedYear
        )
        dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchYear == expectedYear } }
    }

    @Test
    fun `Fetching all launches of a given year only returns those launches 2`() {
        val expectedYear = 2012
        val launchParams = LaunchParams(
            launchYear = expectedYear
        )
        dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchYear == expectedYear } }
    }

    @Test
    fun `Fetching all successful launches only returns those launches`() {
        val launchSuccess = true
        val launchParams = LaunchParams(
            launchSuccess = launchSuccess
        )
        dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchSuccess == launchSuccess } }
    }


    @Test
    fun `Fetching all unsuccessful launches only returns those launches`() {
        val launchSuccess = false
        val launchParams = LaunchParams(
            launchSuccess = launchSuccess
        )
        dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchSuccess == launchSuccess } }
    }

    @Test
    fun `Launches can be sorted asc`() {
        val order: Order = Order.Ascending
        val launchParams = LaunchParams(
            order = order
        )
        val assertNoErrors = dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
        Truth.assertThat(assertNoErrors.values().first())
            .isInOrder { o1, o2 -> (o1 as Launch).launchYear.compareTo((o2 as Launch).launchYear) }
    }


    @Test
    fun `Launches can be sorted desc`() {
        val order: Order = Order.Descending
        val launchParams = LaunchParams(
            order = order
        )
        val assertNoErrors = dataSource
            .fetchAllLaunchesSingle(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        Truth.assertThat(assertNoErrors.values().first())
            .isInOrder { o1, o2 -> -((o1 as Launch).launchYear.compareTo((o2 as Launch).launchYear)) }
    }
}