package com.ecamarero.spacex.domain.launches.model

import java.util.*

data class Launch(
    val flightNumber: Int,
    val launchYear: String,
    val launchSuccess: Boolean?,
    val missionName: String,
    val launchDateUtc: Date,
    val rocket: Rocket,
    val links: Links
)

data class Rocket(
    val rocketName: String,
    val rocketType: String
)

data class Links(
    val missionPatch: String?,
    val missionPatchSmall: String?,
    val articleLink: String?,
    val wikipedia: String?,
    val videoLink: String?,
    val youtubeId: String?
)