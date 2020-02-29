package com.ecamarero.spacex.network.launches

import com.ecamarero.spacex.domain.launches.model.Launch

object LaunchResponseMapper {
    fun toLaunches(from: List<LaunchResponse>): List<Launch> {
        return from.map(this::toLaunch)
    }

    fun toLaunch(from: LaunchResponse): Launch {
        return Launch(
            flightNumber = from.flightNumber
        )
    }
}