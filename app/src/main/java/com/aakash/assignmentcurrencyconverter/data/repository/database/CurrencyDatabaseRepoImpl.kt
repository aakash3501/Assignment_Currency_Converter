package com.aakash.assignmentcurrencyconverter.data.repository.database

import com.aakash.assignmentcurrencyconverter.database.AppDataBase
import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.repository.databaseRepository.CurrencyDatabaseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyDatabaseRepoImpl @Inject constructor(
    private val appDataBase: AppDataBase
) : CurrencyDatabaseRepo {
    override suspend fun saveCurrencyData(currencyData: CurrencyData) {
        currencyData.apply {
            currencyData.savedTimeStamp = System.currentTimeMillis()
        }
        appDataBase.currencyDao().insertCurrencyData(currencyData)
    }

    override suspend fun getCurrencyData(): Flow<CurrencyData?> {
        return appDataBase.currencyDao().getCurrencyData()
    }

    override suspend fun deleteCurrencyData() {
        return appDataBase.currencyDao().deleteCurrencyData()
    }
}