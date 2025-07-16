package com.aakash.assignmentcurrencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aakash.assignmentcurrencyconverter.domain.model.ConvertedCurrency
import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.usecase.FetchCurrencyUseCase
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.CurrencyScreenIntent
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateCurrency
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateTextField
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.state.CurrencyScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val fetchCurrencyUseCase: FetchCurrencyUseCase
) : ViewModel() {
    private var currencyData: CurrencyData? = null

    private val _state = MutableStateFlow(CurrencyScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchCurrencyUseCase.invoke()
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    currencyData = data

                    val shortList = data.rates.keys.toList().sorted()
                    _state.value =
                        _state.value.copy(currencyShortList = shortList, baseCurrencyType = data.base)
                }

        }
    }

    fun handleIntent(intent: CurrencyScreenIntent) {
        when (intent) {
            is UpdateCurrency -> {
                setBaseCurrencyType(intent.value)
            }

            is UpdateTextField -> {
                updateTextField(intent.value)
            }
        }
    }

    private fun updateTextField(value: String) {
        _state.value =
            _state.value.copy(currencyTextFieldValue = value)
        validateAndSetBaseCurrencyValue(value)
    }

    private fun validateAndSetBaseCurrencyValue(s: String) {
        try {
            val value = s.toDouble()
            _state.value =
                _state.value.copy(baseCurrencyValue = value, currencyTextFieldErrorPresent = false)
            convertCurrency()

        } catch (_: NumberFormatException) {
            _state.value =
                _state.value.copy(baseCurrencyValue = 0.0, currencyTextFieldErrorPresent = true)
        }
    }

    private fun setBaseCurrencyType(value: String) {
        _state.value =
            _state.value.copy(baseCurrencyType = value)
        convertCurrency()
    }

    private fun convertCurrency() {
        viewModelScope.launch(Dispatchers.Default) {
            if (state.value.baseCurrencyType.isNotBlank() && state.value.baseCurrencyValue > 0) {
                currencyData?.let {
                    val currencyValue = it.rates[state.value.baseCurrencyType]
                    val convertedToUSDValue = state.value.baseCurrencyValue / currencyValue!!

                    val convertedList: MutableList<ConvertedCurrency> = mutableListOf()
                    state.value.currencyShortList.forEach { currency ->
                        convertedList.add(
                            ConvertedCurrency(
                                value = formatCurrency(convertedToUSDValue,currency,it.rates),
                                type = currency
                            )
                        )
                    }
                    _state.value =
                        _state.value.copy(convertedCurrencyList = convertedList)
                }
            }
        }
    }

    private fun formatCurrency(
        convertedToUSDValue: Double,
        currency: String,
        rates: HashMap<String, Double>
    ): Double{
        val value = rates[currency]
        var finalValue = convertedToUSDValue * value!!
        return String.format(Locale.ENGLISH, "%.2f", finalValue).toDouble()
    }
}