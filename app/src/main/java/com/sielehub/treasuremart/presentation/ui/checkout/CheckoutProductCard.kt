package com.sielehub.treasuremart.presentation.ui.checkout

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Preview(showBackground = true)
@Composable
fun CheckoutProductCard(
    modifier: Modifier = Modifier,
    cartProduct: CartProduct = Constants.cartProducts().first(),
    onClick: (productId: Int) -> Unit = {},
) {
    Card(
        onClick = { onClick(cartProduct.productId) },
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = modifier
                    .size(90.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
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
                        .clickable {
                            onClick(cartProduct.productId)
                        }
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
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                        text = (cartProduct.price ?: 0.0).formatedCurrency(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                        text = "x${cartProduct.quantity}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }

            }
        }
    }
}
