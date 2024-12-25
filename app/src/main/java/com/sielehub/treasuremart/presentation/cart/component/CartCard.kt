package com.sielehub.treasuremart.presentation.cart.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.core.Constants.Companion.products
import com.sielehub.treasuremart.domain.model.CartProduct

@Composable
fun CartCard(
    modifier: Modifier = Modifier,
    cartProduct: CartProduct,
    onClick: (Int) -> Unit = {},
    onRemove: (Int) -> Unit = {},
) {
    val product = products().find { it.id == cartProduct.productId }
    ElevatedCard(onClick = { /*TODO*/ }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(modifier = modifier
                .size(90.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = product?.image,
                    placeholder = painterResource(id = R.drawable.ic_shopping),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            product?.id?.let { onClick(it) }
                        }
                )
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
                        text = product?.title?:"",
                        fontWeight = FontWeight.Bold,
                        minLines = 2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.weight(.9f)
                    )
                    IconButton(
                        onClick = { product?.id?.let { onRemove(it) } },
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            contentColor = Color.Gray
                        ),
                        modifier = modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = null,
                        )
                    }
                }
                Text(
                    text = product?.description?:"",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        style = TextStyle(color = MaterialTheme.colorScheme.primary),
                        text = "KSh ${(product?.price?.times(cartProduct.quantity)?.times(130) ?: 1)}",
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = modifier
                            .padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedIconButton(
                            modifier = modifier.size(24.dp),
                            colors = IconButtonDefaults.outlinedIconButtonColors(
                                contentColor = Color.Gray
                            ),
                            border = BorderStroke(color = Color.Gray, width = 1.dp),
                            shape = RoundedCornerShape(4.dp),
                            onClick = {

                            }) {
                            Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                        }
                        Text(text = cartProduct.quantity.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                        OutlinedIconButton(
                            modifier = modifier.size(24.dp),
                            colors = IconButtonDefaults.outlinedIconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(
                                color = MaterialTheme.colorScheme.primary,
                                width = 1.dp
                            ),
                            shape = RoundedCornerShape(4.dp),
                            onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartCardPreview() {
    CartCard(cartProduct = CartProduct(1, 1))
}