package com.aakash.assignmentcurrencyconverter.presentation.composeComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.aakash.assignmentcurrencyconverter.R

@Composable
fun AmountTextField(amountString: String, errorPresent: Boolean, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = amountString,
        singleLine = true,
        label = {
            Text(text = stringResource(R.string.amount_field_placeholder))
        },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        isError = errorPresent,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done,
        ),
        supportingText = {
            if (errorPresent) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.amount_field_error_message),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        onValueChange = onValueChange
    )
}