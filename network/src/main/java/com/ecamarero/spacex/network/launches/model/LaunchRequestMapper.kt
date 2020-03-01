package com.ecamarero.spacex.network.launches.model

import com.ecamarero.spacex.domain.launches.repository.LaunchParams
import com.ecamarero.spacex.domain.launches.repository.LaunchParams.Order

object LaunchRequestMapper {
    fun mapToRequests(params: LaunchParams): List<LaunchRequest> {
        val launchYears = params.launchYears
        val order = if (params.order is Order.Ascending) ASCENDING else DESCENDING
        val launchSuccess = if (params.onlySuccessful) params.onlySuccessful else null

        if (launchYears.isEmpty() || launchYears.size == 1) {
            return listOf(
                LaunchRequest(
                    launchYear = launchYears.firstOrNull(),
                    order = order,
                    launchSuccess = launchSuccess
                )
            )
        } else {
            val yearsSorted = if (params.order is Order.Ascending) {
                launchYears.sorted()
            } else {
                launchYears.sortedDescending()
            }
            return yearsSorted.map {
                LaunchRequest(
                    launchYear = it,
                    order = order,
                    launchSuccess = launchSuccess
                )
            }
        }
    }

    private const val ASCENDING = "asc"
    private const val DESCENDING = "desc"
}