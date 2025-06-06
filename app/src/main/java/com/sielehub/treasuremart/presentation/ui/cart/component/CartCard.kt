package com.sielehub.treasuremart.presentation.ui.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Composable
fun CartCard(
    modifier: Modifier = Modifier,
    cartProduct: CartProduct,
    onSelected: (CartProduct) -> Unit = {},
    onViewProduct: (Int) -> Unit = {},
    onReduceQuantity: (CartProduct) -> Unit = {},
    onIncreaseQuantity: (CartProduct) -> Unit = {}
) {
    /*var product by remember { mutableStateOf<Product?>(null) }
    LaunchedEffect(Unit) {
        cartViewModel.getProduct(cartProduct.productId) {
            product = it
            setProduct(it)
        }
    }*/
    Card(
        onClick = { onViewProduct(cartProduct.productId) },
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Checkbox(
                checked = cartProduct.isSelected,
                onCheckedChange = {
                    onSelected(cartProduct)
                }
            )
            Box(
                modifier = modifier
                    .size(72.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = .7f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clip(RoundedCornerShape(4.dp))
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
                            onViewProduct(cartProduct.productId)
                        }
                )
                /*AsyncImage(
                    model = cartProduct.image,
                    placeholder = painterResource(id = R.drawable.ic_shopping),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .fillMaxSize()
                        .clickable {
                            onViewProduct(cartProduct.productId)
                        }
                )*/
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = cartProduct.title ?: "",
                        fontWeight = FontWeight.Bold,
                        minLines = 2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.weight(.9f)
                    )
                }
                Spacer(modifier = modifier.height(4.dp))
                Text(
                    text = cartProduct.description ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = cartProduct.price?.formatedCurrency() ?: 0.0.formatedCurrency(),
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = modifier
                            .padding(start = 12.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = null,
                            modifier = modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .size(18.dp)
                                .clickable {
                                    onReduceQuantity(cartProduct)
                                }
                                .clip(RoundedCornerShape(4.dp)),
                        )

                        Text(
                            text = cartProduct.quantity.toString(),
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = modifier.padding(horizontal = 2.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .size(18.dp)
                                .clickable {
                                    onIncreaseQuantity(cartProduct)
                                }
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartCardPreview() {
    CartCard(
        cartProduct = CartProduct(
            productId = 1,
            quantity = 1,
            title = "Product Title",
            price = 100.0,
            description = "Product Description",
            image = "https://via.placeholder.com/150",
            isSelected = false
        )
    )
}