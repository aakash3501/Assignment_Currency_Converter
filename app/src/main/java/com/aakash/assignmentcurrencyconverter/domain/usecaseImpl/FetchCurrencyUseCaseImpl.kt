package com.aakash.assignmentcurrencyconverter.domain.usecaseImpl

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.repository.databaseRepository.CurrencyDatabaseRepo
import com.aakash.assignmentcurrencyconverter.domain.repository.networkRepository.CurrencyNetworkRepo
import com.aakash.assignmentcurrencyconverter.domain.usecase.FetchCurrencyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCurrencyUseCaseImpl @Inject constructor(
    private val currencyNetworkRepo: CurrencyNetworkRepo,
    private val currencyDatabaseRepo: CurrencyDatabaseRepo
) : FetchCurrencyUseCase {
    private val refreshTimeInMilliSecond = 1800000 //30 minutes

    override suspend fun invoke(): Flow<CurrencyData> = flow {
        currencyDatabaseRepo.getCurrencyData().collect { dbData ->
            if (dbData != null) {
                if (shouldRefreshData(dbData.savedTimeStamp)) {
                    clearCurrencyDataFromDb()
                } else {
                    emit(dbData)
                }
            } else {
                val result = currencyNetworkRepo.getCurrencyDataFromApi()
                if (result.isSuccessful) {
                    result.body()?.let {
                        insertCurrencyDataInDb(it)
                    }
                }
            }
        }


    }

    private fun shouldRefreshData(timeStamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - timeStamp) > refreshTimeInMilliSecond
    }

    private suspend fun clearCurrencyDataFromDb() {
        currencyDatabaseRepo.deleteCurrencyData()
    }

    private suspend fun insertCurrencyDataInDb(data: CurrencyData) {
        currencyDatabaseRepo.saveCurrencyData(data)
    }
}