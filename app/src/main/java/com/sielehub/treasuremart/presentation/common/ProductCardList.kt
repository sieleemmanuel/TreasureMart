package com.sielehub.treasuremart.presentation.common

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
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Preview(showBackground = true)
@Composable
fun ProductCardList(
    modifier: Modifier = Modifier,
    product: Product = Product(),
    onClick: (productId: Int) -> Unit = {},
) {
    Card(
        onClick = { onClick(product.id) },
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
                    model = product.image,
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
                            onClick(product.id)
                        }
                )
            }
            Spacer(modifier = modifier.width(8.dp))
            Column {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    text = product.title,
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
                        text = product.price.formatedCurrency(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        modifier = modifier.padding(PaddingValues(horizontal = 4.dp)),
                        text = "x1",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardListShimmer(
    modifier: Modifier = Modifier,
    product: Product = Product(),
    onClick: (productId: Int) -> Unit = {},
) {
    Card(
        onClick = { onClick(product.id) },
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
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = modifier.width(8.dp))
            Column {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .padding(top = 10.dp)
                        .shimmerEffect(RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = modifier.height(8.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = modifier
                            .width(100.dp)
                            .height(16.dp)
                            .shimmerEffect(RoundedCornerShape(4.dp)),
                    )
                    Box(
                        modifier = modifier
                            .width(30.dp)
                            .height(16.dp)
                            .shimmerEffect(RoundedCornerShape(4.dp)),
                    )
                }

            }
        }

    }
}

@Preview(showBackground = false)
@Composable
fun ProductCardListPreview() {
    ProductCardList(product = Product()) {}
}
