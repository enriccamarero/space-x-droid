package com.ecamarero.spacex.network.launches.utils

import com.ecamarero.spacex.network.launches.HttpTestClientModule
import com.ecamarero.spacex.network.launches.LaunchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList


fun successLaunches(): Collection<LaunchResponse> = Gson().fromJson<Collection<LaunchResponse>>(
    HttpTestClientModule.LAUNCHES, object : TypeToken<ArrayList<LaunchResponse>>() {}.type)
fun errorLaunches(): Collection<LaunchResponse> = Gson().fromJson<Collection<LaunchResponse>>("[]", object : TypeToken<ArrayList<LaunchResponse>>() {}.type)