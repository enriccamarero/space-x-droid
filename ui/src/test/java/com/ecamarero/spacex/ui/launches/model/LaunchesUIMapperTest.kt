package com.ecamarero.spacex.ui.launches.model

import com.ecamarero.spacex.domain.launches.model.Launch
import com.google.common.truth.Truth.assertThat
import org.joda.time.DateTime
import org.joda.time.Days
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.text.DateFormat
import java.time.Instant
import java.util.*
import kotlin.math.absoluteValue

class LaunchesUIMapperTest {

    private lateinit var actual: LaunchUI

    @ParameterizedTest
    @MethodSource("params")
    fun `Maps flight number correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(flightNumber).isEqualTo(input.flightNumber)
        }
    }

    @ParameterizedTest
    @MethodSource("params")
    fun `Maps mission name correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(missionName).isEqualTo(input.missionName)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps mission date correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(missionDate).isEqualTo(DateFormat.getDateInstance().format(input.launchDateUtc))
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps mission time correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(missionTime).isEqualTo(DateFormat.getTimeInstance().format(input.launchDateUtc))
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps rocket name correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(rocketName).isEqualTo(input.rocket.rocketName)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps rocket type correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(rocketType).isEqualTo(input.rocket.rocketType)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps days difference correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(daysForLaunch).isEqualTo(
                Days.daysBetween(
                    DateTime.now(),
                    DateTime(input.launchDateUtc.toInstant().toEpochMilli())
                ).days.absoluteValue.toString()
            )
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps past or upcoming launch correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(pastLaunch).isEqualTo(
                Days.daysBetween(
                    DateTime.now(),
                    DateTime(input.launchDateUtc.toInstant().toEpochMilli())
                ).days > 0
            )
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps successful launch correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(successfulLaunch).isEqualTo(input.launchSuccess)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps patch url correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(missionPatchUrlString).isEqualTo(input.links.missionPatchSmall)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps article url correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(articleUrlString).isEqualTo(input.links.articleLink)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps wikipedia url correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(wikipediaUrlString).isEqualTo(input.links.wikipedia)
        }
    }


    @ParameterizedTest
    @MethodSource("params")
    fun `Maps video url correctly`(input: Launch) {
        actual = LaunchesUIMapper.toUI(listOf(input)).first()
        with(actual) {
            assertThat(videoPagesUrlString).isEqualTo(input.links.videoLink)
        }
    }

    companion object {

        @JvmStatic
        private fun params() = listOf(DUMMY_LAUNCH, MIN_DATE_LAUNCH)

        private val DUMMY_LAUNCH = Launch(
            flightNumber = 1,
            launchYear = "2006",
            launchSuccess = true,
            missionName = "MISSION_NAME",
            launchDateUtc = Calendar.getInstance().time,
            rocket = Launch.Rocket(
                rocketName = "rocketName",
                rocketType = "rocketType"
            ),
            links = Launch.Links(
                missionPatch = "missionPatch",
                missionPatchSmall = "missionPatchSmall",
                articleLink = "articleLink",
                wikipedia = "wikipedia",
                videoLink = "videoLink",
                youtubeId = "youtubeId"
            )
        )

        private val MIN_DATE_LAUNCH = Launch(
            flightNumber = 13123,
            launchYear = "2008",
            launchSuccess = null,
            missionName = "MISSION_NAME",
            launchDateUtc = Date.from(Instant.EPOCH),
            rocket = Launch.Rocket(
                rocketName = "rocketName",
                rocketType = "rocketType"
            ),
            links = Launch.Links(
                missionPatch = "missionPatch",
                missionPatchSmall = "missionPatchSmall",
                articleLink = "articleLink",
                wikipedia = "wikipedia",
                videoLink = "videoLink",
                youtubeId = "youtubeId"
            )
        )
    }
}