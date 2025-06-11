package com.sielehub.treasuremart.presentation.ui.product.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.common.StarRatingBar

@Preview(showBackground = true)
@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    rating: Float = 4f,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = R.drawable.ic_avatar,
            placeholder = painterResource(id = R.drawable.ic_avatar),
            contentDescription = null,
            modifier = modifier
                .clip(CircleShape)
                .size(30.dp)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Customer Name",
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = modifier.height(4.dp))
            StarRatingBar(
                rating = rating,
                onRatingChanged = {

                }
            )
        }

    }
}
