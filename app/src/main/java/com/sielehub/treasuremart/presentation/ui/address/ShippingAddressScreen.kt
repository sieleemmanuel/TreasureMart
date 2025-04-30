package com.sielehub.treasuremart.presentation.ui.address

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.presentation.common.TopBar

@Preview(showBackground = true)
@Composable
fun ShippingAddressScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val addresses = listOf(
        Address(
            city = "Pretoria",
            number = 123456789,
            street = "Address 1",
            geolocation = Geolocation("123.456", "789.012"),
            zipcode = "12345",
        ),
        Address(
            city = "Belin",
            number = 123456789,
            street = "Address 2",
            geolocation = Geolocation("345.678", "901.234"),
            zipcode = "23567",
        ),
        Address(
            city = "Maputo",
            number = 123456789,
            street = "Address 3",
            geolocation = Geolocation("234.567", "890.123"),
            zipcode = "205789",
        )
    )
    var selectedAddress by remember { mutableStateOf(addresses.first()) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            TopBar(
                navigationIcon = {
                    FilledIconButton(
                        onClick = { onNavigateBack() },
                        modifier = modifier.size(48.dp),
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "Shipping Address",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                            .padding(end = 48.dp)
                    )
                },
                actions = {}
            )
            LazyColumn(
                modifier = modifier
                    .fillMaxHeight(1f),
                contentPadding = PaddingValues(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = addresses) { address ->
                    Card(
                        onClick = {
                            selectedAddress = address
                            onNavigateBack()
                        },
                        shape = RoundedCornerShape(0),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        Spacer(modifier = modifier.height(8.dp))
                        Box {
                            Row(
                                modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = selectedAddress == address,
                                    onCheckedChange = {
                                        selectedAddress = address
                                        onNavigateBack()
                                    },
                                    modifier = modifier.align(Alignment.CenterVertically)
                                )
                                Column(modifier = modifier.weight(1f)) {
                                    Row {
                                        Text(
                                            text = "John Doe"
                                        )
                                        Spacer(modifier = modifier.width(8.dp))
                                        Text(
                                            text = address.number.toString()
                                        )
                                    }
                                    Spacer(modifier = modifier.height(4.dp))
                                    Text(
                                        text = address.street
                                    )
                                    Spacer(modifier = modifier.height(4.dp))
                                    Text(
                                        text = "${address.city}, ${address.zipcode}"
                                    )
                                    Spacer(modifier = modifier.height(4.dp))
                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = "Edit",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                            modifier = modifier
                                                .padding(vertical = 4.dp)
                                                .clickable {
                                                    Toast.makeText(
                                                        context,
                                                        "Editing address",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                },
                                        )
                                        Spacer(modifier = modifier.width(12.dp))
                                        Text(
                                            text = "Remove",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                            modifier = modifier
                                                .padding(vertical = 4.dp)
                                                .clickable {
                                                    Toast.makeText(
                                                        context,
                                                        "Removing address",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                                .clip(RoundedCornerShape(4.dp)),
                                        )
                                    }
                                }
                            }
                            if (selectedAddress == address) {
                                Text(
                                    text = "Default",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = modifier
                                        .align(Alignment.TopEnd)
                                        .padding(end = 16.dp)
                                )
                            }
                        }
                        Spacer(modifier = modifier.height(8.dp))
                    }
                }
                item {
                    Spacer(modifier = modifier.size(16.dp))
                }
            }
        }

        Button(
            onClick = { Toast.makeText(context, "Adding new address", Toast.LENGTH_SHORT).show() },
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding().plus(16.dp)
                )
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Add New Address",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }

}