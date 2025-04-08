package com.sielehub.treasuremart.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R

@Preview(showBackground = true)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Pair<String, Int> = Pair("category", R.drawable.ic_shopping),
    onClick: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(90.dp)
    ) {
        Card(
            onClick = {
                onClick(category.first)
            },
            modifier = modifier.size(80.dp),
        ) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = category.second,
                    contentDescription = null,
                    placeholder = painterResource(id = category.second),
                    modifier = modifier.size(48.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }

        }
        Text(
            text = category.first.capitalize(Locale.current),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(top = 8.dp)
        )
        Spacer(modifier = modifier.size(8.dp))
    }

}