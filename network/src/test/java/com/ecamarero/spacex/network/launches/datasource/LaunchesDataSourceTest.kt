package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import com.ecamarero.spacex.network.client.HttpClientModule
import com.ecamarero.spacex.network.launches.LaunchesDataSourceImpl
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesDataSourceTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    val clientModule = HttpClientModule

    private lateinit var dataSource: LaunchesDataSource

    @Before
    fun setUp() {
        dataSource =
            LaunchesDataSourceImpl(clientModule.providesLaunchesApi())
    }

    @Test
    fun `Fetching all launches returns a list of launches`() {
        dataSource
            .fetchLaunches()
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
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchYear == expectedYear.toString() } }
    }

    @Test
    fun `Fetching all launches of a given year only returns those launches 2`() {
        val expectedYear = 2012
        val launchParams = LaunchParams(
            launchYear = expectedYear
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchYear == expectedYear.toString() } }
    }

    @Test
    fun `Fetching all successful launches only returns those launches`() {
        val launchSuccess = true
        val launchParams = LaunchParams(
            onlySuccessful = launchSuccess
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchSuccess == launchSuccess } }
    }


    @Test
    fun `Fetching all unsuccessful launches only returns those launches`() {
        val launchSuccess = false
        val launchParams = LaunchParams(
            onlySuccessful = launchSuccess
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.all { it.launchSuccess == launchSuccess } }
    }

    @Test
    fun `Launches can be sorted asc`() {
        val order: LaunchParams.Order = LaunchParams.Order.Ascending
        val launchParams = LaunchParams(
            order = order
        )
        val assertNoErrors = dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
        Truth.assertThat(assertNoErrors.values().first())
            .isInOrder { o1, o2 -> (o1 as Launch).launchYear.compareTo((o2 as Launch).launchYear) }
    }


    @Test
    fun `Launches can be sorted desc`() {
        val order: LaunchParams.Order = LaunchParams.Order.Descending
        val launchParams = LaunchParams(
            order = order
        )
        val assertNoErrors = dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        Truth.assertThat(assertNoErrors.values().first())
            .isInOrder { o1, o2 -> -((o1 as Launch).launchYear.compareTo((o2 as Launch).launchYear)) }
    }
}