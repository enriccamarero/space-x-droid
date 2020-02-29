package com.ecamarero.spacex.network.launches.model

import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchParams.Order

object LaunchParamsMapper {
    fun toRequest(from: LaunchParams?): LaunchRequest {
        return LaunchRequest(
            launchYear = from?.launchYear,
            launchSuccess = from?.launchSuccess,
            order = when (from?.order) {
                Order.Ascending -> "asc"
                Order.Descending -> "desc"
                else -> "asc"
            }
        )
    }
}