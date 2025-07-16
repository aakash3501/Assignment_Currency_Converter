package com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent

sealed interface CurrencyScreenIntent

data class UpdateTextField(val value: String) : CurrencyScreenIntent
data class UpdateCurrency(val value: String) : CurrencyScreenIntent