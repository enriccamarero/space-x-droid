package com.ecamarero.spacex.domain.launches.repository

import com.ecamarero.spacex.domain.launches.datasource.LaunchesDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LaunchesRepositoryTest {

    private val launchesDataSource: LaunchesDataSource = mockk()
    private lateinit var launchesRepository: LaunchesRepository

    @Before
    fun setUp() {
        launchesRepository = LaunchesRepository(launchesDataSource)
    }

    @Test
    fun `Launches are retrieved from the datasource`() {
        every { launchesDataSource.fetchLaunches(any()) } returns Single.just(listOf())
        launchesRepository
            .getLaunches()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }

        verify { launchesDataSource.fetchLaunches() }
    }

    @Test
    fun `Params are forwarded to the datasource`() {
        val launchParams = LaunchParams()
        every { launchesDataSource.fetchLaunches(launchParams) } returns Single.just(listOf())
        launchesRepository
            .getLaunches(launchParams)
            .test()
            .assertComplete()
            .assertNoErrors()
        verify { launchesDataSource.fetchLaunches(launchParams) }
    }


    @Test
    fun `Errors are downstreamed`() {
        val expectedError = Throwable()
        every { launchesDataSource.fetchLaunches(any()) } returns Single.error(expectedError)
        launchesRepository
            .getLaunches()
            .test()
            .assertError(expectedError)

        verify { launchesDataSource.fetchLaunches() }
    }
}