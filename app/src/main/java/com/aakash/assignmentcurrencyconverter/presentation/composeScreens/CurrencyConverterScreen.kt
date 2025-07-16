package com.aakash.assignmentcurrencyconverter.presentation.composeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aakash.assignmentcurrencyconverter.presentation.composeComponents.AmountTextField
import com.aakash.assignmentcurrencyconverter.presentation.composeComponents.Spinner
import com.aakash.assignmentcurrencyconverter.presentation.composeComponents.gridItems.CurrencyGridItem
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateCurrency
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateTextField
import com.aakash.assignmentcurrencyconverter.presentation.ui.Spacing
import com.aakash.assignmentcurrencyconverter.presentation.viewmodel.CurrencyViewModel

@Composable
fun CurrencyConverterScreen(modifier: Modifier = Modifier) {
    val viewModel: CurrencyViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = Spacing.xLarge,
                start = Spacing.medium,
                end = Spacing.medium,
                bottom = Spacing.medium
            ),
        horizontalAlignment = Alignment.End
    ) {

        AmountTextField(
            amountString = state.value.currencyTextFieldValue,
            errorPresent = state.value.currencyTextFieldErrorPresent
        )
        { value ->
            viewModel.handleIntent(UpdateTextField(value))
        }

        Spacer(modifier = Modifier.height(Spacing.xxSmall))

        Spinner(
            label = "USD",
            options = state.value.currencyShortList,
        ) { item ->
            viewModel.handleIntent(UpdateCurrency(item))
        }

        Spacer(modifier = Modifier.height(Spacing.xxSmall))

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(state.value.convertedCurrencyList.size) { index ->
                val item = state.value.convertedCurrencyList[index]
                CurrencyGridItem(text = "${item.value}\n${item.type}")
            }
        }
    }

}
