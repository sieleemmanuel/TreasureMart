package com.sielehub.treasuremart.presentation.ui.cart.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SelectableRow(
    modifier: Modifier = Modifier,
    label: String = "MPESA",
    selected: () -> Boolean = { true },
    numberProvider: (String) -> Unit = { },
    onClick: () -> Unit = {},
) {
    var paymentNumber by remember(selected()) { mutableStateOf("+2547123456789") }
    var isSelected by remember(selected()) { mutableStateOf(selected()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isSelected = true
                onClick()
            }
    ) {
        HorizontalDivider()
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, modifier = modifier.padding(horizontal = 12.dp))
            RadioButton(
                selected = selected(),
                onClick = {
                    isSelected = true
                    onClick()
                })
        }
        if (label == "MPESA" && isSelected) {
            BasicTextField(
                value = paymentNumber,
                onValueChange = {
                    paymentNumber = it
                    numberProvider(it)
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .heightIn(40.dp)
            )
        }
    }


}