package com.ecamarero.spacex.network.launches.model

import com.ecamarero.spacex.domain.launches.model.Launch

object LaunchResponseMapper {
    fun toLaunches(from: List<LaunchResponse>): List<Launch> {
        return from.map(this::toLaunch)
    }

    private fun toLaunch(from: LaunchResponse): Launch {
        return Launch(
            flightNumber = from.flightNumber.toInt(),
            launchYear = from.launchYear.toInt(),
            launchSuccess = from.launchSuccess
        )
    }
}