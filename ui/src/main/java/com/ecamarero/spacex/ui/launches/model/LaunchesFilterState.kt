package com.ecamarero.spacex.ui.launches.model

import com.ecamarero.spacex.ui.launches.LaunchesViewModel.Sorting

data class LaunchesFilterState(
    val years: Set<String> = emptySet(),
    val sorting: Sorting = Sorting.Ascending,
    val onlySuccessfulLaunches: Boolean = false
)