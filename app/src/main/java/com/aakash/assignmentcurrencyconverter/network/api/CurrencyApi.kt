package com.aakash.assignmentcurrencyconverter.network.api

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("api/latest.json?app_id=844d8e204d7744b1b3ce3fa9babca0be")
    suspend fun getCurrencyData(): Response<CurrencyData>
}