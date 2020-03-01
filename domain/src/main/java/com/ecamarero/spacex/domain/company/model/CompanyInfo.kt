package com.ecamarero.spacex.domain.company.model

data class CompanyInfo(
    val name: String,
    val founder: String,
    val founded: String,
    val employees: String,
    val launchSites: String,
    val valuation: Number
)