package com.aakash.assignmentcurrencyconverter.domain.repository.networkRepository

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import retrofit2.Response

interface CurrencyNetworkRepo {
    suspend fun getCurrencyDataFromApi() : Response<CurrencyData>
}