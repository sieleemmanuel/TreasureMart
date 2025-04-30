package com.sielehub.treasuremart.presentation.ui.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.common.TopBar

@Preview(showBackground = true)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopBar(
            navigationIcon = {
                FilledIconButton(
                    onClick = { onNavigateBack() },
                    modifier = modifier
                        .size(48.dp),
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Messages",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                        .padding(end = 48.dp)
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = modifier.height(0.dp))
            }
            items(10) {
                NotificationItemCard(modifier, it)
            }
            item {
                Spacer(
                    modifier = modifier
                        .padding(bottom = paddingValues.calculateBottomPadding())
                        .height(8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationItemCard(modifier: Modifier, i: Int) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Order has been delivered $i")
            Text(text = "20/04", modifier = modifier.alpha(.7f))
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = modifier.size(70.dp)
            )
            Spacer(modifier = modifier.width(10.dp))
            Column(modifier = modifier.weight(1f)) {
                Text(text = "Your order has been delivered $i")
                Spacer(modifier = modifier.height(8.dp))
                Text(text = "See details")
            }

        }
    }
}