package com.ecamarero.spacex.network.client

import com.ecamarero.spacex.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.client.request.host
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol

class HttpClientModule {
    fun providesHttpClient(): HttpClient {
        return HttpClient(Android) {
            setUpDefaultRequest()
            setUpJsonSerializer()
        }
    }

}

internal fun <T : HttpClientEngineConfig> HttpClientConfig<T>.setUpDefaultRequest() {
    defaultRequest {
        host = BuildConfig.BASE_URL
        url {
            protocol = URLProtocol.HTTPS
        }
        accept(ContentType.Application.Json)
    }
}

internal fun <T : HttpClientEngineConfig> HttpClientConfig<T>.setUpJsonSerializer() {
    install(JsonFeature) {
        serializer = GsonSerializer()
    }
}
