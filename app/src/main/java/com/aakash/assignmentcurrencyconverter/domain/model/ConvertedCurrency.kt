package com.aakash.assignmentcurrencyconverter.domain.model

import androidx.annotation.Keep

@Keep
data class ConvertedCurrency(
    val value: Double,
    val type: String
)
