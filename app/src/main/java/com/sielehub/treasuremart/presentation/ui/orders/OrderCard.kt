package com.sielehub.treasuremart.presentation.ui.orders

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    order: Order = Order(
        orderId = 179090630039539L,
        address = Address(
            city = "Pretoria",
            number = 123456789,
            street = "Address 1",
            geolocation = Geolocation("123.456", "789.012"),
            zipcode = "12345",
            shippingFee = 258.00,
            isDefault = true
        ),
        orderItems = Constants.cartProducts().take(2),
        orderTotal = 1235.00,
        orderDate = LocalDate.now().toString(),
        orderStatus = Constants.OrderStatus.SHIPPED
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Order ID: ${order.orderId} ${order.orderDate}")
            Text(text = order.orderStatus)
        }
        order.orderItems.forEach { cartProduct ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = modifier
                        .size(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        )
                ) {
                    SubcomposeAsyncImage(
                        model = cartProduct.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = {
                            Box(
                                modifier = modifier
                                    .fillMaxSize()
                                    .shimmerEffect()
                            )
                        },
                        modifier = modifier
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = modifier.width(8.dp))
                Column {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        text = cartProduct.title ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                        text = "x${cartProduct.quantity}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier
            .padding(horizontal = 8.dp))
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Total amount:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = order.orderTotal.formatedCurrency(),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(modifier = modifier
                .clickable { /*TODO*/ }
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
            Box(modifier = modifier
                .clickable { /*TODO*/ }
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
                Text(text = "Track Order",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        Spacer(modifier = modifier.height(8.dp))

    }
}