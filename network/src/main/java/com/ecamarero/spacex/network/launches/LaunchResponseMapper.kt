package com.ecamarero.spacex.network.launches

import com.ecamarero.spacex.domain.launches.model.Launch

object LaunchResponseMapper {
    fun map(from: List<LaunchResponse>): List<Launch> {
        return from.map(this::map)
    }

    fun map(from: LaunchResponse): Launch {
        return Launch(
            flightNumber = from.flightNumber
        )
    }
}