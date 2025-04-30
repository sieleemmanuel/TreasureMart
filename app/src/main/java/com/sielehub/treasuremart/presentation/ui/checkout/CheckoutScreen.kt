package com.sielehub.treasuremart.presentation.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sielehub.treasuremart.core.Constants.Companion.products
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.cart.component.SelectableRow
import com.sielehub.treasuremart.presentation.ui.product.component.ProductCardList

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
    onNavigateToProductDetail: (id: Int) -> Unit = {},
    onEditAddress: () -> Unit = {}
) {
    val density = LocalDensity.current
    val paymentMethods = listOf("VISA", "Google Pay")
    var selectedPaymentMethod by rememberSaveable { mutableStateOf("") }
    var contentPaddingBottom by remember { mutableStateOf(56.dp) }
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            TopBar(
                navigationIcon = {
                    FilledIconButton(
                        onClick = onNavigateBack,
                        colors = IconButtonDefaults.iconButtonColors(),
                        shape = MaterialTheme.shapes.extraSmall,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "Place Order",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 12.dp, top = 10.dp, end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    AddressCard(
                        onEditAddress = onEditAddress
                    )
                }
                item {
                    PaymentMethodsCard(
                        selectedPaymentMethod = selectedPaymentMethod,
                        paymentMethods = paymentMethods,
                        onPaymentMethodSelected = {
                            selectedPaymentMethod = it
                        }
                    )
                }
                items(items = products().subList(0, 2)) {
                    ProductCardList(product = it) { productId ->
                        onNavigateToProductDetail(productId)
                    }
                }
                item {
                    SubTotalCard()
                    Spacer(modifier = modifier.height(contentPaddingBottom * 3))
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    end = 16.dp
                )
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    val height = it.size.height
                    contentPaddingBottom = with(density) {
                        height.toDp()
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "KSh 156, 000"
            )

            Button(
                onClick = {

                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = modifier
                    .weight(1f)
            ) {
                Text(text = "Place Order")
            }
        }
    }
}

@Composable
private fun SubTotalCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(top = 10.dp, start = 10.dp),
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier,
                text = "Subtotal",
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                text = "KSh 18, 999",
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Spacer(modifier = modifier.height(4.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier,
                text = "Shipping fee",
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                text = "KSh 258",
                style = MaterialTheme.typography.titleSmall,
            )
        }
        /*Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                text = "Payment Amount",
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                text = "KSh 19, 257",
                style = MaterialTheme.typography.titleSmall,
            )
        }*/
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Composable
private fun PaymentMethodsCard(
    modifier: Modifier = Modifier,
    paymentMethods: List<String> = listOf("VISA", "Google Pay"),
    selectedPaymentMethod: String,
    onPaymentMethodSelected: (String) -> Unit = {}
) {
    var selectedMethod by remember(selectedPaymentMethod) { mutableStateOf(selectedPaymentMethod) }
    Card(
        modifier = modifier
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Text(
            text = "Payment Method",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(start = 10.dp, top = 10.dp),
        )
        paymentMethods.forEachIndexed { index, method ->
            SelectableRow(
                label = method,
                selected = { selectedMethod == method },
                onClick = {
                    selectedMethod = method
                    onPaymentMethodSelected(method)
                },
                showDivider = index != paymentMethods.lastIndex,
            )
        }
        Spacer(modifier = modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun AddressCard(
    modifier: Modifier = Modifier,
    onEditAddress: () -> Unit = {},
) {
    val defaultAddress by remember {
        mutableStateOf(
            Address(
            city = "Pretoria",
            number = 123456789,
            street = "Address 1",
            geolocation = Geolocation("123.456", "789.012"),
            zipcode = "12345",
        )
        )
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        onClick = onEditAddress
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = modifier.padding(start = 12.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = defaultAddress.number.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onEditAddress) {
                Icon(
                    imageVector = Icons.Outlined.EditLocationAlt,
                    contentDescription = null,
                    modifier = modifier.padding(start = 4.dp)
                )
            }
        }
        Text(
            text = defaultAddress.street,
            modifier = modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(
            text = "${defaultAddress.city}, ${defaultAddress.zipcode}",
            modifier = modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen()
}

