package com.aakash.assignmentcurrencyconverter.domain.repository.databaseRepository

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import kotlinx.coroutines.flow.Flow

interface CurrencyDatabaseRepo {
    suspend fun saveCurrencyData(currencyData: CurrencyData)

    suspend fun getCurrencyData(): Flow<CurrencyData?>

    suspend fun deleteCurrencyData()
}