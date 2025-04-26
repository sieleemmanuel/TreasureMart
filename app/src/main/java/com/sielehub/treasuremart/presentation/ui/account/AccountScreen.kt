package com.sielehub.treasuremart.presentation.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.FireTruck
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ResetTv
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.presentation.common.BadgedIcon
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    accountViewModel: AccountViewModel = koinViewModel(),
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToOrders: () -> Unit = {}
) {
    val logoutState by accountViewModel.logoutState.collectAsState()
    val orderCategories = listOf(
        Pair(Icons.Default.Payments, "To pay"),
        Pair(Icons.Default.Backpack, "To ship"),
        Pair(Icons.Default.FireTruck, "Shipped"),
        Pair(Icons.Outlined.Reviews, "To review"),
        Pair(Icons.Default.ResetTv, "Returns")
    )
    val services = listOf(
        Pair(Icons.Filled.LocationOn, "Addresses"),
        Pair(Icons.Filled.HeadsetMic, "Help center"),
        Pair(Icons.AutoMirrored.Outlined.Help, "FAQ"),
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_avatar),
                        contentDescription = null,
                        modifier = modifier.size(36.dp)
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = "Username here",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            actions = {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                }
                FilledIconButton(
                    onClick = onNavigateToNotifications,
                    colors = IconButtonDefaults.iconButtonColors(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    BadgedIcon(
                        icon = Icons.Outlined.Notifications,
                        count = Constants.myCart().products.size
                    )
                }
            }
        )
        Spacer(modifier = modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(0.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Orders",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
                TextButton(
                    onClick = onNavigateToOrders,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "View all",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        modifier = modifier.size(16.dp)
                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                orderCategories.forEach { category ->
                    OrderCategoryItem(
                        modifier = modifier
                            .weight(1f)
                            .height(72.dp),
                        category = category
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(0.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "My Services",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
            FlowRow(
                modifier = modifier
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                services.forEach { service ->
                    Column(
                        modifier = modifier
                            .width(100.dp)
                            .clickable {

                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = modifier.height(4.dp))
                        Icon(imageVector = service.first,
                            contentDescription = null)
                        Spacer(modifier = modifier.height(4.dp))
                        Text(text = service.second,
                            textAlign = TextAlign.Center,
                            modifier = modifier.fillMaxWidth(),
                            )
                        Spacer(modifier = modifier.height(4.dp))
                    }
                }
            }
            Spacer(modifier = modifier.height(10.dp))
        }
        Spacer(modifier = modifier.weight(1f))
        Button(
            onClick = {
                accountViewModel.logout()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            if (logoutState.isLoading) {
                CircularProgressIndicator(
                    modifier = modifier.size(30.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = modifier.width(8.dp))
            }
            Text(stringResource(R.string.logout), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderCategoryItem(
    modifier: Modifier = Modifier,
    category: Pair<ImageVector, String> = Pair(Icons.Default.Payments, "To pay"),
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = modifier.height(8.dp))
        Icon(
            imageVector = category.first,
            contentDescription = null
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = category.second)
        Spacer(modifier = modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview(modifier: Modifier = Modifier) {
    AccountScreen()
}