package com.aakash.assignmentcurrencyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aakash.assignmentcurrencyconverter.database.typeconverter.MapTypeConverter
import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData

@Database(entities = [CurrencyData::class], version = 1, exportSchema = false)
@TypeConverters(MapTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}