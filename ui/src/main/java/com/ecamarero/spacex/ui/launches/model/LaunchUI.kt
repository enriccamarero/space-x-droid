package com.ecamarero.spacex.ui.launches.model

internal data class LaunchUI(
    val flightNumber: Int,
    val missionName: String,
    val missionDate: String,
    val missionTime: String,
    val rocketName: String,
    val rocketType: String,
    val daysForLaunch: String,
    val pastLaunch: Boolean,
    val successfulLaunch: Boolean?,
    val missionPatchUrlString: String?,
    val articleUrlString: String?,
    val wikipediaUrlString: String?,
    val videoPagesUrlString: String?
)