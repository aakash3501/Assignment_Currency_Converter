package com.aakash.assignmentcurrencyconverter.presentation.composeComponents.gridItems

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.aakash.assignmentcurrencyconverter.presentation.ui.Dimension
import com.aakash.assignmentcurrencyconverter.presentation.ui.Spacing

@Composable
fun CurrencyGridItem(text: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.xSmall, vertical = Spacing.xxSmall)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimension.currencyCardHeight)
                .padding(Spacing.xSmall),
            textAlign = TextAlign.Center,
            text = text
        )
    }
}