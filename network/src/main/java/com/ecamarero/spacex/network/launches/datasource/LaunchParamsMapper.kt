package com.ecamarero.spacex.network.launches.datasource

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.network.launches.LaunchRequest

object LaunchParamsMapper {
    fun toRequest(from: LaunchParams?): LaunchRequest? {
        if(from == null) return null
        return LaunchRequest(
            launchYear = from.launchYear,
            launchSuccess = from.launchSuccess,
            order = from.order
        )
    }
}