package com.ecamarero.spacex.domain.launches.usecase

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetLaunchesTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val launchesRepository: LaunchesRepository = mockk()

    private lateinit var getLaunches: GetLaunches

    @Before
    fun setUp() {
        getLaunches = GetLaunches(launchesRepository)
    }

    @Test
    fun `Get Launches calls the repository`() {
        every { launchesRepository.getLaunches(any()) } returns Single.just(listOf())
        getLaunches(
            order = LaunchParams.Order.Ascending,
            onlySuccessful = true,
            launchYears = emptyList()
        )
            .test()
        verify { launchesRepository.getLaunches(any()) }
    }


    @Suppress("USELESS_IS_CHECK")
    @Test
    fun `Get Launches completes and returns a list of launches`() {
        every { launchesRepository.getLaunches(any()) } returns Single.just(listOf())
        getLaunches(
            order = LaunchParams.Order.Ascending,
            onlySuccessful = true,
            launchYears = emptyList()
        )
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch> }
        verify { launchesRepository.getLaunches(any()) }
    }

    @Test
    fun `Get Launches can fail`() {
        val throwable = Throwable()
        every { launchesRepository.getLaunches(any()) } returns Single.error(throwable)
        getLaunches(
            order = LaunchParams.Order.Ascending,
            onlySuccessful = true,
            launchYears = emptyList()
        )
            .test()
            .assertNotComplete()
            .assertError(throwable)
        verify { launchesRepository.getLaunches(any()) }
    }
}