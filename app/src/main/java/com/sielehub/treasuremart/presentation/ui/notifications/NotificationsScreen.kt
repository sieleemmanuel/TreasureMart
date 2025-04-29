package com.sielehub.treasuremart.presentation.ui.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.R

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null
                )
            }
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.weight(.8f)
            )
            /*IconButton(onClick = { *//*TODO*//* }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }*/
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = modifier.height(8.dp))
            }
            items(10) {
                NotificationItemCard(modifier, it)
            }
        }
    }
}

@Composable
fun NotificationItemCard(modifier: Modifier, i: Int) {
    Card {
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