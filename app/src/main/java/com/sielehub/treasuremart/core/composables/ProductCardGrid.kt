package com.sielehub.treasuremart.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Composable
fun ProductCardGrid(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp)
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
            )
        }

        Spacer(modifier = modifier.height(8.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = 8.dp)),
            text = product.title,
            style = MaterialTheme.typography.labelMedium,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            modifier = modifier.padding(PaddingValues(horizontal = 8.dp)),
            text = product.price.formatedCurrency(),
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = 8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            StarRatingBar(
                rating = product.rating.rate.toFloat(),
                onRatingChanged = {
                }
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = "(${product.rating.count})",
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Preview
@Composable
fun ProductCardGridShimmer(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp)
                .shimmerEffect()
        )

        Spacer(modifier = modifier.height(8.dp))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(PaddingValues(horizontal = 8.dp))
                .shimmerEffect(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = modifier.height(8.dp))
        Box(
            modifier = modifier
                .width(100.dp)
                .padding(PaddingValues(horizontal = 8.dp))
                .height(16.dp)
                .shimmerEffect(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(PaddingValues(horizontal = 8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = modifier
                    .width(100.dp)
                    .height(16.dp)
                    .shimmerEffect(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = modifier.width(4.dp))
            Box(
                modifier = modifier
                    .width(40.dp)
                    .height(16.dp)
                    .shimmerEffect(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = modifier.height(16.dp))
    }
}

@Preview(showBackground = false)
@Composable
fun ProductCardPreview() {
    ProductCardGrid(product = Product()) {}
}
