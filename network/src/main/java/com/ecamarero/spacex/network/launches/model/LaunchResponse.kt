package com.ecamarero.spacex.network.launches.model

import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    @SerializedName("flight_number") val flightNumber: Number,
    @SerializedName("launch_year") val launchYear: Number,
    @SerializedName("launch_success") val launchSuccess: Boolean
)