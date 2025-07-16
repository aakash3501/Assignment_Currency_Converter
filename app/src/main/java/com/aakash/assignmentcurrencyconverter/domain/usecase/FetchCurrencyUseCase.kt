package com.aakash.assignmentcurrencyconverter.domain.usecase

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import kotlinx.coroutines.flow.Flow

interface FetchCurrencyUseCase {
    suspend fun invoke(): Flow<CurrencyData>
}