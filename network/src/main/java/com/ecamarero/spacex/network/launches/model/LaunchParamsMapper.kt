package com.ecamarero.spacex.network.launches.model

import com.ecamarero.spacex.domain.launches.datasource.LaunchParams
import com.ecamarero.spacex.domain.launches.datasource.LaunchParams.Order
import com.ecamarero.spacex.network.launches.model.LaunchRequest

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