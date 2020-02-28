package com.ecamarero.spacex.network.launches

import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    @SerializedName("flight_number") val flightNumber: Number
)