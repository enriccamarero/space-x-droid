package com.ecamarero.spacex.ui.company.model

import com.ecamarero.spacex.domain.company.model.CompanyInfo
import org.junit.Assert.*
import org.junit.Test

class CompanyUIMapperTest {

    @Test
    fun `Generates the correct string - Empty values`() {
        assertEquals(CompanyUIMapper.toCompanyString(DEFAULT_COMPANY_INFO), " was founded by  in . It has now  employees,  launch sites, and is valued at USD 0.0 billion")
    }

    @Test
    fun `Generates the correct string - Actual values`() {
        assertEquals(CompanyUIMapper.toCompanyString(ACTUAL_COMPANY_INFO), "SpaceX was founded by Elon musk in 2002. It has now 700 employees, 3 launch sites, and is valued at USD 27.5 billion")
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

        private val ACTUAL_COMPANY_INFO = CompanyInfo(
            name = "SpaceX",
            founder = "Elon musk",
            founded = "2002",
            employees = "700",
            launchSites = "3",
            valuation = 27500000000
        )
    }
}