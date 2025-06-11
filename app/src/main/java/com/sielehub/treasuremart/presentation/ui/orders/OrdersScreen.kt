package com.sielehub.treasuremart.presentation.ui.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.core.composables.EmptyListUIState
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    ordersViewModel: OrdersViewModel = koinViewModel(),
    paddingValues: PaddingValues = PaddingValues(),
    statusIndex: Int = 0,
    onNavigateBack: () -> Unit = {},
    onNavigateToOrderDetail: (orderId: Long) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(statusIndex) }
    val tabItems = listOf(
        Constants.OrderStatus.TO_PAY,
        Constants.OrderStatus.TO_SHIP,
        Constants.OrderStatus.SHIPPED,
        Constants.OrderStatus.COMPLETED,
        Constants.OrderStatus.RETURNED,
    )
    val ordersState by ordersViewModel.ordersState.collectAsStateWithLifecycle()
    val orders by remember {
        derivedStateOf {
            ordersState.orders.filter { order ->
                order.orderStatus == tabItems[selectedTabIndex]
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = modifier
                    .height(48.dp)
                    .aspectRatio(1f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null
                )
            }
            Text(
                text = "My orders",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.weight(.8f)
            )
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) MaterialTheme.typography.titleMedium.fontWeight
                            else MaterialTheme.typography.titleSmall.fontWeight
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = modifier
                        .wrapContentWidth()
                )
            }
        }
        Spacer(modifier = modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (orders.isEmpty()) {
                item {
                    EmptyListUIState(
                        icon = Icons.AutoMirrored.Rounded.ReceiptLong,
                        description = "You have no orders in this category yet."
                    )
                }
            }
            items(items = orders) { order ->
                OrderCard(
                    order = order,
                    onNavigateToDetail = onNavigateToOrderDetail
                )
            }
        }
    }
}
