package com.ecamarero.spacex.network.company.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CompanyInfoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("founder") val founder: String,
    @SerializedName("founded") val founded: String,
    @SerializedName("employees") val employees: String,
    @SerializedName("launch_sites") val launchSites: String,
    @SerializedName("valuation") val valuation: Number
)