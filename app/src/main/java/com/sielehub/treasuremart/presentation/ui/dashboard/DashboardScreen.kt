package com.sielehub.treasuremart.presentation.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.core.Constants.Companion.categories
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.presentation.common.BadgedIcon
import com.sielehub.treasuremart.presentation.common.DealsProductCard
import com.sielehub.treasuremart.presentation.ui.product.component.ProductCardGrid
import com.sielehub.treasuremart.presentation.ui.product.list.ProductsByCategoryState
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onOpenNotification: () -> Unit = {},
    onSearchBarClick: () -> Unit = {},
    onOpenProductDeals: () -> Unit = {},
    onOpenBestPicksProduct: (Product) -> Unit = {},
) {
    var searchedProduct by remember { mutableStateOf("Find products") }
    val searchHistory = remember {
        mutableListOf<String>("Power station", "jacket", "water filter", "loafer shoes")
    }
    var searchHistoryIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        while (true) {
            searchedProduct = ""
            delay(500)
            searchedProduct = searchHistory[searchHistoryIndex]
            searchHistoryIndex = (searchHistoryIndex + 1) % searchHistory.size
            delay(5000)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues),
    ) {
        Spacer(modifier = modifier.height(8.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
            FilledIconButton(
                onClick = {
                    onOpenNotification()
                },
                colors = IconButtonDefaults.iconButtonColors(),
                shape = RoundedCornerShape(4.dp)
            ) {
                BadgedIcon(
                    icon = Icons.Outlined.Notifications,
                    count = Constants.myCart().products.size
                )
            }
        }
        Spacer(modifier = modifier.height(4.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(24.dp)
                )
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    onSearchBarClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search),
                modifier = modifier.padding(10.dp)
            )
            AnimatedVisibility(
                visible = searchedProduct.isNotEmpty(),
                enter = slideInVertically(),
                modifier = modifier.weight(1f)
            ) {
                Text(
                    text = searchedProduct,
                    modifier = modifier
                        .padding(end = 8.dp)
                )
            }
        }
        Spacer(modifier = modifier.height(4.dp))
        ProductsPages(
            onOpenProductDeals = onOpenProductDeals,
            onOpenBestPicksProduct = onOpenBestPicksProduct,
        )
    }
}

@Composable
fun ProductsPages(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    onOpenProductDeals: () -> Unit = {},
    onOpenBestPicksProduct: (Product) -> Unit = {}
) {
    var tabItems = mutableListOf(stringResource(R.string.explore))
        .plus(categories().map { it.replaceFirstChar { it.uppercase() } })
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = selectedTabIndex,
        pageCount = {
            tabItems.size
        }
    )
    val categoryProductsState = dashboardViewModel.productSByCategoryState.value

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex != 0) {
            dashboardViewModel.getProductsByCategory(tabItems[selectedTabIndex].lowercase())
        }
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
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
        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxWidth()
        ) {
            if (it == 0) {
                ExplorePage(
                    onOpenProductDeals = onOpenProductDeals,
                    onOpenBestPicksProduct = onOpenBestPicksProduct
                )
            } else {
                CategoryPage(
                    category = tabItems[selectedTabIndex],
                    categoryProductsState = categoryProductsState,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExplorePage(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel = koinViewModel(),
    onOpenProductDeals: () -> Unit,
    onOpenBestPicksProduct: (Product) -> Unit
) {
    val superDealsProductsState: SuperDealsProductsState =
        dashboardViewModel.superDealsProductsState.value
    val bestPickProductsState: BestPickProductsState =
        dashboardViewModel.bestPickProductsState.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(horizontal = 12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = R.drawable.promotions,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.promotions),
                    modifier = modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.super_deals),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier
                    )
                    TextButton(
                        onClick = {
                            onOpenProductDeals()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        contentPadding = PaddingValues(end = 0.dp)
                    ) {
                        Text("Ends:")
                        CountDownTimer(hours = 24)
                        Spacer(modifier = modifier.width(1.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                            contentDescription = null,
                            modifier = modifier.size(10.dp)
                        )
                    }
                }
                when {
                    superDealsProductsState.isLoading -> {
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = modifier.padding(vertical = 24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }

                    superDealsProductsState.error.isNotBlank() -> {
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = superDealsProductsState.error,
                                modifier = modifier.padding(vertical = 24.dp)
                            )
                        }
                    }

                    superDealsProductsState.products.isNotEmpty() -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = modifier
                        ) {
                            items(superDealsProductsState.products) { product ->
                                DealsProductCard(
                                    product = product,
                                    onClick = {
                                        onOpenProductDeals()
                                    })
                            }
                        }
                    }
                }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(R.string.best_picks_for_you),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )
        }
        when {
            bestPickProductsState.isLoading -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.padding(vertical = 56.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

            bestPickProductsState.error.isNotBlank() -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = bestPickProductsState.error)
                    }
                }
            }

            bestPickProductsState.products.isNotEmpty() -> {
                items(items = bestPickProductsState.products.distinct()) { product ->
                    ProductCardGrid(
                        product = product,
                        onClick = {
                            onOpenBestPicksProduct(product)
                        })
                }
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(
                modifier = modifier
                    .height(16.dp)
                    .navigationBarsPadding()
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    category: String,
    categoryProductsState: ProductsByCategoryState
) {
    key(category) {
        val categoryResImage = remember {
            when (category.lowercase()) {
                "electronics" -> R.drawable.electronics
                "jewelery" -> R.drawable.jewelry
                "men's clothing" -> R.drawable.men_clothings
                "women's clothing" -> R.drawable.women_clothings
                else -> R.drawable.loading_progress
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .padding(horizontal = 12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = categoryResImage,
                        contentDescription = null,
                        placeholder = rememberAsyncImagePainter(R.drawable.loading_progress),
                        modifier = modifier
                            .height(180.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            when {
                categoryProductsState.isLoading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = modifier.padding(vertical = 56.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }

                categoryProductsState.error.isNotBlank() -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = categoryProductsState.error)
                        }
                    }
                }

                categoryProductsState.products.isNotEmpty() -> {
                    items(
                        items = categoryProductsState.products,
                        span = { GridItemSpan(1) }) { product ->
                        ProductCardGrid(
                            product = product,
                            onClick = {
                                // navController.navigate(Route.ProductDetail(product.id))
                            })
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(
                    modifier = modifier
                        .height(16.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    hours: Int = 12
) {
    var remainingHours by remember { mutableIntStateOf(hours) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(true) {
        while (remainingHours > 0 || minutes > 0 || seconds > 0) {
            delay(1000)
            if (seconds > 0) {
                seconds--
            } else {
                seconds = 59
                if (minutes > 0) {
                    minutes--
                } else {
                    minutes = 59
                    if (remainingHours > 0) {
                        remainingHours--
                    }
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TimeValue(modifier, remainingHours)
        TimeSeparator()
        TimeValue(modifier, minutes)
        TimeSeparator()
        TimeValue(modifier, seconds)
    }
}

@Composable
private fun TimeSeparator() {
    Box {
        Text(
            text = " : ",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun TimeValue(modifier: Modifier, timeValue: Int) {
    Box(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Text(
            text = if (timeValue < 10) "0$timeValue" else "$timeValue",
            color = MaterialTheme.colorScheme.background,
            modifier = modifier.padding(horizontal = 3.dp, vertical = 1.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview(
    modifier: Modifier = Modifier,
) {
    TreasureMartTheme(false) {
        Surface {
            DashboardScreen(modifier, PaddingValues())
        }

    }
}