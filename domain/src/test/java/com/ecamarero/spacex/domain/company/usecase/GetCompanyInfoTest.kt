package com.ecamarero.spacex.domain.company.usecase

import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.company.repository.CompanyRepository
import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchesRepository
import com.ecamarero.spacex.domain.launches.usecase.GetLaunches
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCompanyInfoTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val companyRepository: CompanyRepository = mockk()

    private lateinit var getCompanyInfo: GetCompanyInfo

    @Before
    fun setUp() {
        getCompanyInfo = GetCompanyInfo(companyRepository)
    }

    @Test
    fun `Get Company Info calls the repository`() {
        every { companyRepository.getCompanyInfo() } returns Single.just(DEFAULT_COMPANY_INFO)
        getCompanyInfo()
            .test()
        verify { companyRepository.getCompanyInfo() }
    }


    @Suppress("USELESS_IS_CHECK")
    @Test
    fun `Get Company Info completes and returns a list of launches`() {
        every { companyRepository.getCompanyInfo() } returns Single.just(DEFAULT_COMPANY_INFO)
        getCompanyInfo()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is CompanyInfo }
        verify { companyRepository.getCompanyInfo() }
    }

    @Test
    fun `Get Company Info can fail`() {
        val throwable = Throwable()
        every { companyRepository.getCompanyInfo() } returns Single.error(throwable)
        getCompanyInfo()
            .test()
            .assertNotComplete()
            .assertError(throwable)
        verify { companyRepository.getCompanyInfo() }
    }

    companion object {
        private val DEFAULT_COMPANY_INFO = CompanyInfo(
            name = "",
            founder = "",
            founded = "",
            employees = "",
            launchSites = "",
            valuation = 0
        )
    }
}