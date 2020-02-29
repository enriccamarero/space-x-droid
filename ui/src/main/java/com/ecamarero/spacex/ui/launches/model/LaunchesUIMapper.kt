package com.ecamarero.spacex.ui.launches.model

import com.ecamarero.spacex.domain.launches.model.Launch
import org.joda.time.DateTime
import org.joda.time.Days
import java.text.DateFormat
import java.util.*
import kotlin.math.absoluteValue

internal object LaunchesUIMapper {
    fun toUI(from: List<Launch>): List<LaunchUI> {
        return from.map {
            val daysForLaunch =
                getDaysForLaunch(it.launchDateUtc)
            LaunchUI(
                flightNumber = it.flightNumber,
                missionName = it.missionName,
                missionDate = DateFormat.getDateInstance().format(it.launchDateUtc),
                missionTime = DateFormat.getTimeInstance().format(it.launchDateUtc),
                rocketName = it.rocket.rocketName,
                rocketType = it.rocket.rocketType,
                daysForLaunch = daysForLaunch.absoluteValue.toString(),
                pastLaunch = daysForLaunch > 0,
                successfulLaunch = it.launchSuccess,
                missionPatchUrlString = it.links.missionPatchSmall,
                articleUrlString = it.links.articleLink,
                wikipediaUrlString = it.links.wikipedia,
                videoPagesUrlString = it.links.videoLink
            )
        }
    }

    private fun getDaysForLaunch(launchDate: Date) =
        Days.daysBetween(
            DateTime.now(),
            DateTime(launchDate.toInstant().toEpochMilli())
        ).days
}