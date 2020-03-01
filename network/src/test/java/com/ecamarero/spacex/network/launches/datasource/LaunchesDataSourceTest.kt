package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import com.ecamarero.spacex.network.launches.LaunchesApi
import com.ecamarero.spacex.network.launches.LaunchesDataSourceImpl
import io.mockk.Ordering
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaunchesDataSourceTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val launchesApi: LaunchesApi = mockk()

    private lateinit var dataSource: LaunchesDataSource

    @Before
    fun setUp() {
        dataSource = LaunchesDataSourceImpl(launchesApi)
    }

    @Suppress("USELESS_IS_CHECK")
    @Test
    fun `Fetching all launches returns a list of launches`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        dataSource
            .fetchLaunches(LaunchParams())
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }

        verify {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }
    }

    @Test
    fun `Fetching launches can fail`() {
        val throwable = Throwable()
        every { launchesApi.launches(any(), any(), any()) } returns Single.error(throwable)
        dataSource
            .fetchLaunches(LaunchParams())
            .test()
            .assertError(throwable)
            .assertNotComplete()

        verify {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }
    }

    @Test
    fun `Fetching all launches calls the api`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        dataSource
            .fetchLaunches(LaunchParams())
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }
    }

    @Test
    fun `Fetching launches ascending results in "asc" parameter`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val launchParams = LaunchParams(
            order = LaunchParams.Order.Ascending
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            launchesApi.launches(
                launchYear = any(),
                order = "asc",
                launchSuccess = any()
            )
        }
    }

    @Test
    fun `Fetching launches descending results in "desc" parameter`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val launchParams = LaunchParams(
            order = LaunchParams.Order.Descending
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            launchesApi.launches(
                launchYear = any(),
                order = "desc",
                launchSuccess = any()
            )
        }
    }

    @Test
    fun `Fetching only successful launches results in launch_success=true parameter`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val launchParams = LaunchParams(
            onlySuccessful = true
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            launchesApi.launches(
                launchYear = any(),
                order = any(),
                launchSuccess = true
            )
        }
    }

    @Test
    fun `Fetching all launches results in launch_success=null parameter`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val launchParams = LaunchParams(
            onlySuccessful = false
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify {
            launchesApi.launches(
                launchYear = any(),
                order = any(),
                launchSuccess = null
            )
        }
    }

    @Test
    fun `Fetching launches with N years results in N calls concatenated`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val yearsSelected = listOf(1, 2, 3)
        val launchParams = LaunchParams(
            launchYears = yearsSelected
        )

        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(exactly = yearsSelected.size) {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }

        verify(ordering = Ordering.ORDERED) {
            yearsSelected.map {
                launchesApi.launches(
                    launchYear = it,
                    launchSuccess = any(),
                    order = any()
                )
            }
        }
    }

    @Test
    fun `Fetching launches with N years can fail - If one or more fail downstreams error`() {
        val throwable = Throwable()
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        every { launchesApi.launches(1, any(), any()) } returns Single.error(throwable)
        val yearsSelected = listOf(1, 2, 3)
        val launchParams = LaunchParams(
            launchYears = yearsSelected
        )

        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertError(throwable)
            .assertNotComplete()

        verify(exactly = yearsSelected.size) {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }

        verify(ordering = Ordering.ORDERED) {
            yearsSelected.map {
                launchesApi.launches(
                    launchYear = it,
                    launchSuccess = any(),
                    order = any()
                )
            }
        }
    }

    @Test
    fun `Fetching launches with N years and ASCENDING results in N calls concatenated in order ASC`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val yearsSelected = listOf(1, 2, 3)
        val launchParams = LaunchParams(
            launchYears = yearsSelected,
            order = LaunchParams.Order.Ascending
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(exactly = yearsSelected.size) {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }

        verify(ordering = Ordering.ORDERED) {
            yearsSelected.map {
                launchesApi.launches(
                    launchYear = it,
                    launchSuccess = any(),
                    order = any()
                )
            }
        }
    }

    @Test
    fun `Fetching launches with N years and DESCENDING results in N calls concatenated in order DESC`() {
        every { launchesApi.launches(any(), any(), any()) } returns Single.just(listOf())
        val yearsSelected = listOf(1, 2, 3)
        val launchParams = LaunchParams(
            launchYears = yearsSelected,
            order = LaunchParams.Order.Descending
        )
        dataSource
            .fetchLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()

        verify(exactly = yearsSelected.size) {
            launchesApi.launches(
                launchYear = any(),
                launchSuccess = any(),
                order = any()
            )
        }

        verify(ordering = Ordering.ORDERED) {
            yearsSelected.sortedDescending().map {
                launchesApi.launches(
                    launchYear = it,
                    launchSuccess = any(),
                    order = any()
                )
            }
        }
    }
}