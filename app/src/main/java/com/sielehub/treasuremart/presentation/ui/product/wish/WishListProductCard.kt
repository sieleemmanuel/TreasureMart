package com.sielehub.treasuremart.presentation.ui.product.wish

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect

@Preview(showBackground = true)
@Composable
fun WishListProductCard(
    modifier: Modifier = Modifier,
    product: Product = Product(),
    onClick: (productId: Int) -> Unit = {},
    onDelete: (productId: Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .clickable {
                onClick(product.id)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .padding(start = 10.dp)
                .size(120.dp)
                /*.background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )*/
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
        Column(
            modifier = modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = product.title,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                minLines = 2,
                maxLines = 2
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.price.formatedCurrency(),
                    style = MaterialTheme.typography.titleSmall,
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = modifier
                        .alpha(0.8f)
                        .clickable {
                            onDelete(product.id)
                        }
                        .padding(bottom = 4.dp)
                )

            }

        }
    }

}

