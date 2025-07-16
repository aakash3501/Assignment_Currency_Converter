package com.aakash.assignmentcurrencyconverter.data.repository.network

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.repository.networkRepository.CurrencyNetworkRepo
import com.aakash.assignmentcurrencyconverter.network.api.CurrencyApi
import retrofit2.Response
import javax.inject.Inject

class CurrencyNetworkRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) : CurrencyNetworkRepo {
    override suspend fun getCurrencyDataFromApi(): Response<CurrencyData> {
        return currencyApi.getCurrencyData()
    }
}