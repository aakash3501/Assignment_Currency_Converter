package com.aakash.assignmentcurrencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert
    fun insertCurrencyData(currencyData: CurrencyData)

    @Query("SELECT * FROM currency LIMIT 1")
    fun getCurrencyData(): Flow<CurrencyData?>

    @Query("DELETE FROM currency")
    fun deleteCurrencyData()
}