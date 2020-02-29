package com.ecamarero.spacex.network.utils

import com.ecamarero.spacex.network.launches.HttpTestClientModule
import com.ecamarero.spacex.network.launches.LaunchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

internal fun HttpTestClientModule.withExpectedUrl(expected: String) {
    expectedUrl = expected
}