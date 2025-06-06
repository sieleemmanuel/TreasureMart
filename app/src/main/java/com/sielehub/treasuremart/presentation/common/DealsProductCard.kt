package com.sielehub.treasuremart.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Composable
fun DealsProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
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
        Spacer(modifier = modifier.height(4.dp))
        Text(
            modifier = modifier.padding(PaddingValues(horizontal = 8.dp)),
            text = product.price.formatedCurrency(),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DealsProductCardShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()

        )
        Spacer(modifier = modifier.height(4.dp))
        Box(
            modifier = modifier
                .padding(PaddingValues(horizontal = 8.dp))
                .width(50.dp)
                .height(20.dp)
                .shimmerEffect(RoundedCornerShape(4.dp)),

            )
    }
}

@Preview(showBackground = false)
@Composable
fun ProductCardPreview() {
    DealsProductCard(product = Product()) {}
}
