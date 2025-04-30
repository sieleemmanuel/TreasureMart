package com.sielehub.treasuremart.presentation.ui.cart.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SelectableRow(
    modifier: Modifier = Modifier,
    label: String = "VISA",
    selected: () -> Boolean = { true },
    onClick: () -> Unit = {},
    showDivider: Boolean = true,
) {
    var isSelected by remember(selected()) { mutableStateOf(selected()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isSelected = true
                onClick()
            }
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
            selected = selected(),
            onClick = {
                isSelected = true
                onClick()
            }
        )
            Text(text = label, modifier = modifier.padding(horizontal = 12.dp))

        }
        if (showDivider) HorizontalDivider()
    }
}