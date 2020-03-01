package com.ecamarero.spacex.network.company.datasource

import com.ecamarero.spacex.domain.company.datasource.CompanyInfoDataSource
import com.ecamarero.spacex.domain.company.model.CompanyInfo
import com.ecamarero.spacex.domain.utils.RxImmediateSchedulerRule
import com.ecamarero.spacex.network.company.CompanyInfoApi
import com.ecamarero.spacex.network.company.CompanyInfoDataSourceImpl
import com.ecamarero.spacex.network.company.model.CompanyInfoResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CompanyInfoDataSourceImplTest {

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()

    private val companyInfoApi: CompanyInfoApi = mockk()

    private lateinit var dataSource: CompanyInfoDataSource

    @Before
    fun setUp() {
        dataSource = CompanyInfoDataSourceImpl(companyInfoApi)
    }

    @Suppress("USELESS_IS_CHECK")
    @Test
    fun `Fetching company info returns CompanyInfoResponse`() {
        every { companyInfoApi.info() } returns Single.just(
            CompanyInfoResponse(
                name = "",
                founder = "",
                founded = "",
                employees = "",
                launchSites = "",
                valuation = 0
            )
        )
        dataSource
            .fetchCompanyInfo()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it is CompanyInfo }
    }

    @Test
    fun `Fetching company can fail`() {
        val throwable = Throwable()
        every { companyInfoApi.info() } returns Single.error(
            throwable
        )
        dataSource
            .fetchCompanyInfo()
            .test()
            .assertError(throwable)
            .assertNotComplete()
    }
}