package com.ecamarero.spacex.network.launches.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class LaunchResponse(
    @SerializedName("flight_number") val flightNumber: Int,
    @SerializedName("mission_name") val missionName: String,
    @SerializedName("launch_year") val launchYear: String,
    @SerializedName("launch_date_utc") val launchDateUtc: Date,
    @SerializedName("rocket") val rocket: RocketResponse,
    @SerializedName("launch_success") val launchSuccess: Boolean?,
    @SerializedName("links") val links: LinksReponse
)

data class RocketResponse(
    @SerializedName("rocket_name") val rocketName: String,
    @SerializedName("rocket_id") val rocketType: String
)

data class LinksReponse(
    @SerializedName("mission_patch") val missionPatch: String?,
    @SerializedName("mission_patch_small") val missionPatchSmall: String?,
    @SerializedName("article_link") val articleLink: String?,
    @SerializedName("wikipedia") val wikipedia: String?,
    @SerializedName("video_link") val videoLink: String?,
    @SerializedName("youtube_id") val youtubeId: String?
)