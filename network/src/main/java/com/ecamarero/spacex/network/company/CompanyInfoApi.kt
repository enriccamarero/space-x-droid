package com.ecamarero.spacex.network.company

import com.ecamarero.spacex.network.company.model.CompanyInfoResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CompanyInfoApi {

    @GET("v3/info")
    fun info(): Single<CompanyInfoResponse>
}
