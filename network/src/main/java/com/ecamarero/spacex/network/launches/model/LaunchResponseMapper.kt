package com.ecamarero.spacex.network.launches.model

import com.ecamarero.spacex.domain.launches.model.Launch
import com.ecamarero.spacex.domain.launches.model.Links
import com.ecamarero.spacex.domain.launches.model.Rocket

object LaunchResponseMapper {
    fun toLaunches(from: List<LaunchResponse>): List<Launch> {
        return from.map(this::toLaunch)
    }

    private fun toLaunch(from: LaunchResponse): Launch {
        return Launch(
            flightNumber = from.flightNumber,
            missionName = from.missionName,
            launchYear = from.launchYear,
            launchDateUtc = from.launchDateUtc,
            rocket = RocketResponseMapper.toRocket(from.rocket),
            launchSuccess = from.launchSuccess,
            links = LinksResponseMapper.toLinks(from.links)
        )
    }
}

object RocketResponseMapper {
    fun toRocket(from: RocketResponse): Rocket {
        return Rocket(
            rocketName = from.rocketName,
            rocketType = from.rocketType
        )
    }
}

object LinksResponseMapper {
    fun toLinks(from: LinksReponse): Links {
        return Links(
            missionPatch = from.missionPatch,
            missionPatchSmall = from.missionPatchSmall,
            articleLink = from.articleLink,
            wikipedia = from.wikipedia,
            videoLink = from.videoLink,
            youtubeId = from.youtubeId
        )
    }
}