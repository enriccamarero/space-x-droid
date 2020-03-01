package com.ecamarero.spacex.domain.launches.usecase

import com.ecamarero.spacex.domain.launches.repository.LaunchParams

object LaunchParamsMapper {

    fun toLaunchParams(
        launchYears: Collection<Int>,
        order: LaunchParams.Order,
        onlySuccessful: Boolean
    ): LaunchParams = LaunchParams(
        launchYears = launchYears,
        order = order,
        onlySuccessful = onlySuccessful
    )
}