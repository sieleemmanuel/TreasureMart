package com.sielehub.treasuremart.presentation.ui.product.component

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
import androidx.compose.material3.ElevatedCard
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.domain.model.Product

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
                AsyncImage(
                    model = product.image,
                    placeholder = rememberAsyncImagePainter(R.drawable.loading_progress),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
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
                        text = "KSh ${product.price.times(130)}",
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


        /* if (addButton) {
             Button(
                 modifier = modifier
                     .padding(PaddingValues(horizontal = 4.dp))
                     .fillMaxWidth()
                     .heightIn(24.dp),
                 shape = RoundedCornerShape(4.dp),
                 onClick = { *//*TODO*//* }
            ) {
                Text(text = "Add to cart")
            }
            //Spacer(modifier = modifier.height(4.dp))
        }*/
    }
}

@Preview(showBackground = false)
@Composable
fun ProductCardListPreview() {
    ProductCardList(product = Product()) {}
}
