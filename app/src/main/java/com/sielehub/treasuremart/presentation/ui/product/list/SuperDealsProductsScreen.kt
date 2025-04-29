package com.sielehub.treasuremart.presentation.ui.product.list

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.dashboard.CountDownTimer
import com.sielehub.treasuremart.presentation.ui.dashboard.DashboardViewModel
import com.sielehub.treasuremart.presentation.ui.product.component.ProductCardGrid
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperDealProductsScreen(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    paddingValues: PaddingValues = PaddingValues(),
    onNavigateBack: () -> Unit = {},
    onNavigateToProductDetail: (id: Int, discount: Int) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current

    val productDealsState by dashboardViewModel.superDealsProductsState.collectAsState()

    val categories = remember {
        listOf(
            "All",
            "Electronics",
            "Jewelery",
            "Men's clothing",
            "Women's clothing"
        )
    }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    var selectedCategory by rememberSaveable {
        mutableStateOf(categories[selectedTabIndex])
    }

    val products by remember(selectedCategory) {
        derivedStateOf {
            if (selectedTabIndex == 0) {
                productDealsState.products
            } else {
                productDealsState.products.filter {
                    it.category.lowercase() == selectedCategory.lowercase()
                }
            }
        }
    }

    LaunchedEffect(selectedTabIndex) {
        selectedCategory = categories[selectedTabIndex]
    }

    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        TopBar(
            navigationIcon = {
                FilledIconButton(
                    onClick = { onNavigateBack() },
                    modifier = modifier
                        .size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Super Deals",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = {
                FilledIconButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "To share real super deals!!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    },
                    colors = IconButtonDefaults.iconButtonColors(),
                    shape = RoundedCornerShape(4.dp),
                    modifier = modifier
                        .padding(start = 4.dp)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = null
                    )
                }
            }
        )
        
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(vertical = 8.dp),
            indicator = {},
            divider = {},
        ) {
            categories.forEachIndexed { index, title ->
                CustomTab(
                    title = title,
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(top = 4.dp, bottom = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            )
            {
                Text("Ends: ")
                CountDownTimer(hours = 24)
            }
        }

        when {
            productDealsState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                }
            }

            productDealsState.error.isNotEmpty() -> {
                Column(
                    modifier = modifier.fillMaxSize(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = productDealsState.error)
                    Spacer(modifier = modifier.height(8.dp))
                    Button(onClick = {
                        dashboardViewModel.getSuperDealProducts()
                    }) {
                        Text(text = "Retry")
                    }
                }
            }

            else -> {
                if (!products.isEmpty()) {
                    LazyVerticalGrid(
                        modifier = modifier,
                        columns = GridCells.Adaptive(160.dp),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(key = { it.id }, items = products) {
                            ProductCardGrid(
                                product = it,
                                onClick = { onNavigateToProductDetail(it.id, 50) }
                            )
                        }
                        item {
                            Spacer(modifier = modifier.height(paddingValues.calculateBottomPadding()))
                        }
                    }
                } else {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "$selectedCategory products deals not found now!")
                    }
                }
            }
        }


    }
}

@Composable
fun CustomTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .padding(end = 8.dp)
            .background(
                color = if (selected) MaterialTheme.colorScheme.primary
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .selectable(
                selected = selected,
                onClick = onClick
            )

    ) {
        Text(
            text = title,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
