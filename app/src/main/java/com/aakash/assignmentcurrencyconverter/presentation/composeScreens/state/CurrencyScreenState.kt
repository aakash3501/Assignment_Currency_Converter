package com.aakash.assignmentcurrencyconverter.presentation.composeScreens.state

import com.aakash.assignmentcurrencyconverter.domain.model.ConvertedCurrency

data class CurrencyScreenState(
    val currencyTextFieldValue: String = "",
    val currencyTextFieldErrorPresent: Boolean = false,
    var baseCurrencyValue: Double = 0.0,
    var baseCurrencyType: String = "",
    var currencyShortList: List<String> = emptyList(),
    var convertedCurrencyList: List<ConvertedCurrency> = emptyList()
)
