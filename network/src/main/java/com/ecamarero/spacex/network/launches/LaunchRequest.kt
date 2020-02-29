package com.ecamarero.spacex.network.launches

import com.google.gson.annotations.SerializedName

data class LaunchRequest(
    @SerializedName("launch_year") val launchYear: Int? = null,
    @SerializedName("launch_success") val launchSuccess: Boolean? = null,
    @SerializedName("order") val order: String? = null
)