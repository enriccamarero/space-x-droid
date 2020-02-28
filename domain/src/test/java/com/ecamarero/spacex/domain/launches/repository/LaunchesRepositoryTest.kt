package com.ecamarero.spacex.domain.launches.repository

import com.ecamarero.spacex.domain.launches.datasource.LaunchesRxDataSource
import com.ecamarero.spacex.domain.launches.model.Launch
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LaunchesRepositoryTest {

    private val launchesRxDataSource: LaunchesRxDataSource = mockk()
    private lateinit var launchesRepository: LaunchesRepository

    @Before
    fun setUp() {
        launchesRepository = LaunchesRepository(launchesRxDataSource)
    }

    @Test
    fun `Fetching all launches returns a list of launches`() {
        every { launchesRxDataSource.fetchAllLaunchesSingle() } returns Single.just(listOf())
        launchesRepository
            .getAllLaunches()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }
            .awaitTerminalEvent()

        verify { launchesRxDataSource.fetchAllLaunchesSingle() }
    }

}