package com.ecamarero.spacex.domain.company.repository

import com.ecamarero.spacex.domain.company.datasource.CompanyInfoDataSource
import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CompanyRepositoryTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val companyInfoDataSource: CompanyInfoDataSource = mockk()

    private lateinit var companyRepository: CompanyRepository

    @Before
    fun setUp() {
        companyRepository = CompanyRepository(companyInfoDataSource)
    }

    @Suppress("USELESS_IS_CHECK")
    @Test
    fun `Company info is retrieved from the datasource`() {
        every { companyInfoDataSource.fetchCompanyInfo() } returns Single.just(DEFAULT_COMPANY_INFO)
        companyRepository
            .getCompanyInfo()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is CompanyInfo }

        verify { companyInfoDataSource.fetchCompanyInfo() }
    }

    @Test
    fun `Errors are downstreamed`() {
        val expectedError = Throwable()
        every { companyInfoDataSource.fetchCompanyInfo() } returns Single.error(expectedError)
        companyRepository
            .getCompanyInfo()
            .test()
            .assertError(expectedError)

        verify { companyInfoDataSource.fetchCompanyInfo() }
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