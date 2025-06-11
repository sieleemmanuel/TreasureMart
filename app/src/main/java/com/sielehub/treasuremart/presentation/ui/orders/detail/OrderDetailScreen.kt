package com.sielehub.treasuremart.presentation.ui.orders.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.checkout.AddressCard
import com.sielehub.treasuremart.presentation.ui.checkout.CheckoutProductCard
import com.sielehub.treasuremart.presentation.ui.orders.OrdersViewModel
import com.sielehub.treasuremart.presentation.common.ProductCardListShimmer
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    ordersViewModel: OrdersViewModel = koinViewModel(),
    orderId: Long,
    onNavigateBack: () -> Unit = {},
    onTrackOrder: (Long) -> Unit = {}
) {
    val density = LocalDensity.current
    var contentPaddingBottom by remember { mutableStateOf(56.dp) }
    val orderState by ordersViewModel.orderState.collectAsState()

    LaunchedEffect(Unit) {
        ordersViewModel.getOrder(orderId)
    }
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
                        text = orderState.order?.orderStatus ?: "",
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
                    orderState.order?.address?.let {
                        AddressCard(
                            onEditAddress = {},
                            defaultAddress = it
                        )
                    }
                }
                when {
                    orderState.isLoading -> {
                        items(3) {
                            ProductCardListShimmer()
                        }
                    }

                    orderState.error.isNotBlank() -> {
                        item {
                            Text(text = orderState.error)
                        }
                    }

                    else -> {
                        items(items = orderState.order?.orderItems ?: emptyList()) {
                            CheckoutProductCard(
                                cartProduct = it,
                                onClick = {

                                }
                            )
                        }
                    }
                }

                item {
                    orderState.order?.let { order ->
                        OrderInfoCard(order = order)
                    }
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
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = modifier
                    .clickable {
                        //ordersViewModel.deleteOrder(orderState.order!!.orderId)
                    }
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(24.dp)
                    )) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = modifier.width(8.dp))
            Box(
                modifier = modifier
                    .clickable {
                        onTrackOrder(orderState.order!!.orderId)
                    }
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Text(
                    text = "Track Order",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderInfoCard(
    modifier: Modifier = Modifier,
    order: Order = Constants.order
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Spacer(modifier = modifier.height(8.dp))
        OrderInfoRow(info = Pair("Order No:", order.orderId.toString()))
        OrderInfoRow(info = Pair("Order Date:", order.orderDate))
        OrderInfoRow(info = Pair("Payment Method:", order.paymentMethod))
        OrderInfoRow(
            info = Pair(
                "Shipping Fee:",
                (order.address.shippingFee ?: 0.00).formatedCurrency()
            )
        )
        OrderInfoRow(info = Pair("Payment Amount", order.orderTotal.formatedCurrency()))
        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview
@Composable
fun OrderInfoRow(
    modifier: Modifier = Modifier,
    info: Pair<String, String> = Pair("Label:", "Value")
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier,
            text = info.first,
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
            text = info.second,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}