package com.ecamarero.spacex.domain.usecase

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
        getLaunches()
            .test()
            .assertComplete()
            .assertNoErrors()
        verify { launchesRepository.getLaunches(any()) }
    }

    @Test
    fun `Get Launches returns a result with data when repository returns data`() {
        every { launchesRepository.getLaunches(any()) } returns Single.just(listOf())
        getLaunches()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is List<Launch>}
    }

    @Test
    fun `Get Launches returns a result with error when repository returns error`() {
        val throwable = Throwable()
        every { launchesRepository.getLaunches(any()) } returns Single.error(throwable)
        getLaunches()
            .test()
            .assertError(throwable)
    }

    @Test
    fun `Get Launches takes sorting, launch successfulness and launch year as parameters`() {
        every { launchesRepository.getLaunches(any()) } returns Single.just(listOf())
        getLaunches(order = LaunchParams.Order.Ascending, launchYear = 2002, launchSuccessful = true)
            .test()
            .assertComplete()
            .assertNoErrors()
    }

}