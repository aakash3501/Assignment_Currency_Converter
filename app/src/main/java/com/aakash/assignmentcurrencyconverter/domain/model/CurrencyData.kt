package com.aakash.assignmentcurrencyconverter.domain.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aakash.assignmentcurrencyconverter.database.typeconverter.MapTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
@Keep
data class CurrencyData(
    @ColumnInfo(name = "disclaimer")
    @SerializedName("disclaimer")
    val disclaimer: String,

    @ColumnInfo(name = "license")
    @SerializedName("license")
    val license: String,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "base")
    @SerializedName("base")
    val base: String,

    @ColumnInfo(name = "rates")
    @SerializedName("rates")
    @TypeConverters(MapTypeConverter::class)
    val rates: HashMap<String, Double>,

    @ColumnInfo(name = "savedTimeStamp")
    @SerializedName("savedTimeStamp")
    var savedTimeStamp: Long,

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}
