package com.sielehub.treasuremart.presentation.product

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.product.component.ReviewCard
import com.sielehub.treasuremart.presentation.product.component.StarRatingBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState {
        4
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            delay(2000)
            with(pagerState) {
                val nextPage = if (currentPage < pageCount) currentPage + 1 else 0
                animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                        )
                )
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            FilledIconButton(
                onClick = {

                },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {

                Icon(imageVector = Icons.Outlined.Share, contentDescription = null)

            }
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
            // .verticalScroll(scrollState)
        ) {
            item {
                Box(modifier = modifier.fillMaxWidth()) {
                    HorizontalPager(state = pagerState) {
                        AsyncImage(
                            model = R.drawable.ic_launcher_background,
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.ic_shopping),
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                Card(
                    modifier = modifier
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = LoremIpsum(12).values.first(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    Text(
                        text = "KSh 1,000",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(horizontal = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Row(
                        modifier = modifier
                            .wrapContentWidth()
                            .padding(PaddingValues(horizontal = 12.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        StarRatingBar(
                            onRatingChanged = {

                            }
                        )
                        Spacer(modifier = modifier.width(8.dp))
                        Text(
                            text = "(150 ratings)",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = modifier.height(10.dp))
                }
            }
            item {
                Spacer(modifier = modifier.height(16.dp))
                Card(
                    modifier = modifier
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Product Rating and Reviews",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = modifier.padding(horizontal = 12.dp),
                            )
                            Row(
                                modifier = modifier
                                    .wrapContentWidth()
                                    .padding(PaddingValues(horizontal = 12.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "(150 ratings)",
                                    style = MaterialTheme.typography.bodySmall
                                )

                            }
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                                contentDescription = null,
                                modifier = modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                    HorizontalDivider(modifier = modifier)
                    Column(
                        modifier = modifier.padding(horizontal = 10.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        repeat( 3) {
                            ReviewCard()
                        }
                    }

                }
                Spacer(modifier = modifier.height(16.dp))
                Card(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(12.dp),
                    )
                    Spacer(modifier = modifier.height(8.dp))

                    Text(
                        text = LoremIpsum(60).values.first(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = modifier.padding(horizontal = 12.dp),
                    )
                }
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = "What's in the box",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 12.dp),
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = "- Item 1",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.padding(horizontal = 12.dp),
                )
                Text(
                    text = "- Item 2",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.padding(horizontal = 12.dp),
                )
                Text(
                    text = "- Item 3",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.padding(horizontal = 12.dp),
                )
                Spacer(modifier = modifier.height(100.dp))
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                FilledIconButton(
                    onClick = {

                    },
                    shape = RoundedCornerShape(1.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
                }
                FilledIconButton(
                    onClick = {

                    },
                    shape = RoundedCornerShape(1.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    BadgedBox(badge = {
                        Badge {
                            Text(text = "9")
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)

                    }
                }
            }

            ElevatedButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = modifier
                    .weight(1f)
                    .heightIn(48.dp)
            ) {
                Text(text = "Add to cart")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview(modifier: Modifier = Modifier) {
    ProductScreen()
}

