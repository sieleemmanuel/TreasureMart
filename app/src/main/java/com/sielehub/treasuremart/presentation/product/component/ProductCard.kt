package com.sielehub.treasuremart.presentation.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        onClick = {

        },
        modifier = modifier
            .wrapContentHeight()
    ) {
        Box(modifier = modifier){
            AsyncImage(
                model = R.drawable.ic_launcher_background,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_shopping),
                modifier = modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
            IconButton(onClick = { /*TODO*/ },
                modifier = modifier
                    .padding(PaddingValues(4.dp)).align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = modifier.height(8.dp))
            Text(
                modifier = modifier
                    .padding(PaddingValues(horizontal = 10.dp)),
                text = "Product name",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        Spacer(modifier = modifier.height(8.dp))
            Text(
                modifier = modifier.padding(PaddingValues(horizontal = 10.dp)),
                text = "KSh 2, 000",
                style = TextStyle(fontSize = 20.sp)
            )
        Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(horizontal = 12.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                StarRatingBar(
                    onRatingChanged = {

                    }
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(text = "(0)")
            }
        Spacer(modifier = modifier.height(16.dp))
        Button(
            modifier = modifier
                .padding(PaddingValues(horizontal = 10.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Add to cart")
        }
        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview(showBackground = false)
@Composable
fun ProductCardPreview() {
    ProductCard()
}
