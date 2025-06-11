package com.sielehub.treasuremart.presentation.ui.checkout

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.cart.component.SelectableRow
import com.sielehub.treasuremart.core.composables.ProductCardListShimmer
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    checkoutViewModel: CheckoutViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToProducts: () -> Unit = {},
    onEditAddress: () -> Unit = {},
    onNavigateToProductDetail: (id: Int) -> Unit = {},
    onNavToOrderDetails: (orderId: Long) -> Unit = {}
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val paymentMethods = listOf(
        Constants.PaymentMethod.VISA,
        Constants.PaymentMethod.GOOGLE_PAY,
        Constants.PaymentMethod.CASH_ON_DELIVERY
    )
    var selectedPaymentMethod by rememberSaveable { mutableStateOf("") }
    var contentPaddingBottom by remember { mutableStateOf(56.dp) }
    val checkoutProductsState by checkoutViewModel.checkoutProductsState.collectAsState()
    val subTotal by checkoutViewModel.subtotal.collectAsState()
    val addresses by checkoutViewModel.addresses.collectAsState()
    val selectedAddress by remember {
        derivedStateOf {
            addresses.firstOrNull { it.isDefault == true }
        }
    }
    val placeOrderState by checkoutViewModel.placeOrderState.collectAsState()
    var showProgressDialog by remember { mutableStateOf(false) }
    val orderStatus = listOf(
        Constants.OrderStatus.TO_PAY,
        Constants.OrderStatus.TO_SHIP,
        Constants.OrderStatus.SHIPPED,
        Constants.OrderStatus.COMPLETED,
        Constants.OrderStatus.RETURNED,
    ).random()
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
                    selectedAddress?.let {
                        AddressCard(
                            onEditAddress = onEditAddress,
                            defaultAddress = it
                        )
                    }
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
                when {
                    checkoutProductsState.isLoading -> {
                        items(3) {
                            ProductCardListShimmer()
                        }
                    }

                    checkoutProductsState.error.isNotBlank() -> {
                        item {
                            Text(text = checkoutProductsState.error)
                        }
                    }

                    else -> {
                        items(items = checkoutProductsState.products) {
                            CheckoutProductCard(
                                cartProduct = it,
                                onClick = {

                                }
                            )
                        }
                    }
                }

                item {
                    SubTotalCard(subTotal = subTotal)
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
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = (subTotal + (selectedAddress?.shippingFee ?: 0.00)).formatedCurrency()
            )

            Button(
                onClick = {
                    if (selectedAddress != null && selectedPaymentMethod.isNotEmpty()) {
                        val order = Order(
                            address = selectedAddress!!,
                            orderItems = checkoutProductsState.products,
                            orderTotal = subTotal + (selectedAddress!!.shippingFee ?: 0.00),
                            orderDate = "2023-04-01",
                            orderStatus = orderStatus,
                            paymentMethod = selectedPaymentMethod
                        )
                        checkoutViewModel.placeOrder(order)
                        showProgressDialog = true
                    } else {
                        if (selectedAddress == null) {
                            Toast.makeText(context, "Please select an address", Toast.LENGTH_SHORT)
                                .show()
                        } else if (selectedPaymentMethod.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please select a payment method",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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
        AnimatedVisibility(
            visible = showProgressDialog,
            enter = slideInVertically(),
            exit = slideOutVertically(),
        ) {
            PlacingOrderDialog(
                placeOrderState = placeOrderState,
                onDismiss = {
                    showProgressDialog = false
                    onNavigateBack()
                },
                onNavigateToProducts = onNavigateToProducts,
                onNavigateToDetails = {
                    showProgressDialog = false
                    onNavToOrderDetails(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubTotalCard(
    modifier: Modifier = Modifier,
    subTotal: Double = 1200.00,
    shippingFee: Double = 258.00
) {
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
                text = subTotal.formatedCurrency(),
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
                text = shippingFee.formatedCurrency(),
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
    paymentMethods: List<String> = listOf(
        Constants.PaymentMethod.VISA,
        Constants.PaymentMethod.GOOGLE_PAY,
        Constants.PaymentMethod.CASH_ON_DELIVERY
    ),
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
fun AddressCard(
    modifier: Modifier = Modifier,
    onEditAddress: () -> Unit = {},
    defaultAddress: Address = Address(
        city = "Pretoria",
        number = 123456789,
        street = "Address 1",
        geolocation = Geolocation("123.456", "789.012"),
        zipcode = "12345",
        shippingFee = 258.00
    )
) {
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
fun PlacingOrderDialog(
    modifier: Modifier = Modifier,
    placeOrderState: PlaceOrderState = PlaceOrderState(
        isLoading = false,
        error = "",
        orderID = System.currentTimeMillis()
    ),
    onDismiss: () -> Unit = {},
    onNavigateToProducts: () -> Unit = {},
    onNavigateToDetails: (orderId: Long) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Box(modifier = modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onDismiss,
                    modifier = modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when {
                        placeOrderState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = modifier
                                    .align(Alignment.CenterHorizontally)
                                    .size(64.dp),
                            )
                            Spacer(modifier = modifier.height(16.dp))
                            Text(text = "Processing the order")
                        }

                        placeOrderState.error.isNotEmpty() -> {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = null,
                                modifier = modifier
                                    .align(Alignment.CenterHorizontally)
                                    .size(64.dp)
                            )
                            Spacer(modifier = modifier.height(16.dp))
                            Text(text = placeOrderState.error)
                        }

                        else -> {
                            Icon(
                                imageVector = Icons.Default.CheckCircleOutline,
                                contentDescription = null,
                                modifier = modifier
                                    .align(Alignment.CenterHorizontally)
                                    .size(64.dp)
                            )
                            Spacer(modifier = modifier.height(16.dp))
                            Text(
                                text = "Order confirmed!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = modifier.height(16.dp))
                            Text(
                                text = "Your order has been placed successfully",
                                textAlign = TextAlign.Center,
                            )
                            placeOrderState.orderID?.let { placeOrderOrderId ->
                                TextButton(
                                    onClick = { onNavigateToDetails(placeOrderOrderId) }
                                ) {
                                    Text(text = "View Order")
                                }
                            }
                            Spacer(modifier = modifier.height(16.dp))
                            Button(
                                onClick = {
                                onNavigateToProducts()
                                onDismiss()
                            }) {
                                Text(text = "Continue Shopping")
                            }
                        }
                    }
                }
            }
        }
    }
}


