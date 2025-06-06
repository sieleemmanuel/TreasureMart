package com.sielehub.treasuremart.presentation.ui.product.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.WishProduct
import com.sielehub.treasuremart.presentation.base.MainViewModel
import com.sielehub.treasuremart.presentation.common.BadgedIcon
import com.sielehub.treasuremart.presentation.common.TopBar
import com.sielehub.treasuremart.presentation.ui.product.component.ReviewCard
import com.sielehub.treasuremart.presentation.ui.product.component.StarRatingBar
import com.sielehub.treasuremart.presentation.util.formatedCurrency
import com.sielehub.treasuremart.presentation.util.shimmerEffect
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    productDetailViewModel: ProductDetailViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel(),
    productId: Int = 1,
    onNavigateBack: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToCheckout: (productId: Int?) -> Unit = {}
) {
    val pagerState = rememberPagerState {
        2
    }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val density = LocalDensity.current
    var isLoading by remember {
        mutableStateOf(true)
    }
    val productDetailState by productDetailViewModel.productDetailState.collectAsStateWithLifecycle()
    var collapsedTopBarHeight by remember { mutableFloatStateOf(56f) }
    val expandedTopBarHeight = 360.dp
    val isTopBarCollapsed by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 20 || listState.firstVisibleItemIndex > 0
        }
    }
    var isOpenImages by remember { mutableStateOf(false) }
    val isWishProduct by productDetailViewModel.isWishProduct.collectAsStateWithLifecycle()
    val productInCartState by productDetailViewModel.productInCartState.collectAsStateWithLifecycle()
    val cartState by productDetailViewModel.cartState.collectAsStateWithLifecycle()
    val addToCartState by productDetailViewModel.addToCartState.collectAsStateWithLifecycle()
    val firstItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    LaunchedEffect(isWishProduct) {
        productDetailViewModel.checkIsWish(productId)
    }

    LaunchedEffect(productId) {
        productDetailViewModel.getProductDetail(productId)
    }
    LaunchedEffect(addToCartState) {
        productDetailViewModel.isProductInCart(productId)
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        isLoading = false
    }
    HandleOnBackPressed(isOpenImages.not()) {
        if (isOpenImages) {
            isOpenImages = false
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
        /*.padding(bottom = paddingValues.calculateBottomPadding())*/
    ) {
        TopBar(
            modifier = modifier
                .zIndex(1f)
                .onGloballyPositioned { coordinates ->
                    collapsedTopBarHeight = coordinates.size.height.toFloat()
                },
            navigationIcon = {
                FilledIconButton(
                    onClick = onNavigateBack,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        contentDescription = null
                    )
                }
            },
            title = {},
            actions = {
                FilledIconButton(
                    onClick = {
                        Toast.makeText(context, "Sharing the product", Toast.LENGTH_SHORT).show()
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {

                    Icon(imageVector = Icons.Outlined.Share, contentDescription = null)

                }
            },
            containerColor = if (isTopBarCollapsed)
                MaterialTheme.colorScheme.surfaceContainer.copy(
                    alpha = firstItemScrollOffset.div(200f)
                )
            else Color.Transparent
        )

        when {
            productDetailState.error.isNotEmpty() -> {
                Column {
                    Text(text = productDetailState.error)
                    Spacer(modifier = modifier.height(16.dp))
                    Button(onClick = { productDetailViewModel.getProductDetail(productId) }) {
                        Text(text = "Retry")
                    }
                }
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = listState,
        ) {
            item {
                if (isLoading) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(expandedTopBarHeight)
                            .shimmerEffect()
                    )
                } else {
                    Box(modifier = modifier.fillMaxWidth()) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = modifier
                                .clickable {
                                    isOpenImages = !isOpenImages
                                }) {
                            AsyncImage(
                                model = productDetailState.product?.image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(360.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .wrapContentHeight()
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 8.dp, end = 16.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(16.dp)
                                ),
                        ) {
                            Text(
                                text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                modifier = modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Text(
                        text = productDetailState.product?.title ?: "",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)
                    )
                    Spacer(modifier = modifier.height(12.dp))
                    Text(
                        text = productDetailState.product?.price?.formatedCurrency()
                            ?: 0.0.formatedCurrency(),
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
                            rating = productDetailState.product?.rating?.rate?.toFloat() ?: 0f,
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
                        .padding(horizontal = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
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
                                    text = "(${productDetailState.product?.rating?.count} ratings)",
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
                        repeat(3) {
                            ReviewCard(
                                rating = productDetailState.product?.rating?.rate?.toFloat() ?: 0f
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                Card(
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(12.dp),
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = productDetailState.product?.description
                            ?: LoremIpsum(20).values.first(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = modifier.height(16.dp))
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
                Spacer(
                    modifier = modifier
                        .navigationBarsPadding()
                        .height(120.dp)
                )
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .navigationBarsPadding()
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row {
                FilledIconButton(
                    onClick = {
                        productDetailState.product?.let {
                            if (!isWishProduct) {
                                productDetailViewModel.addToWishList(
                                    WishProduct(
                                        productId = it.id,
                                        product = it
                                    )
                                )
                            } else {
                                productDetailViewModel.removeFromWishList(it.id)
                            }
                        }
                    },
                    shape = RoundedCornerShape(1.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(
                        imageVector = if (isWishProduct) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isWishProduct) Color.Red
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                FilledIconButton(
                    onClick = onNavigateToCart,
                    shape = RoundedCornerShape(1.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    cartState.cart?.products?.size?.let {
                        BadgedIcon(Icons.Outlined.ShoppingCart, it)
                    }
                }
            }

            Row(
                modifier = modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                OutlinedButton(
                    onClick = {
                        productDetailState.product?.let {
                            productDetailViewModel.addToCart(
                                CartProduct(
                                    productId = it.id,
                                    quantity = 1,
                                    price = it.price,
                                    title = it.title,
                                    image = it.image,
                                    category = it.category,
                                    description = it.description
                                )
                            )
                            productDetailViewModel.isProductInCart(it.id)
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = modifier.weight(1f)
                ) {
                    val text = if (productInCartState.inCart)
                        "Added(${productInCartState.cartProduct?.quantity})" else "Add to cart"
                    Text(text = text)
                }
                Button(
                    onClick = {
                        onNavigateToCheckout(productId)
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = modifier
                        .weight(1f)
                ) {
                    Text(text = "Buy now")
                }
            }
        }
    }
    if (isOpenImages) {
        productDetailState.product?.let {
            ImageViewer(
                images = listOf(it.image, it.image, it.image),
                onDismiss = { isOpenImages = false }
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showBackground = true)
@Composable
fun ImageViewer(
    modifier: Modifier = Modifier,
    images: List<String> = mutableListOf("", "", ""),
    onDismiss: () -> Unit = {}
) {
    val configurations = LocalConfiguration.current
    val screenHeight = configurations.screenHeightDp
    val pagerState = rememberPagerState(
        pageCount = {
            images.size
        }
    )
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = (scale * zoom).coerceIn(1f, 3f)
                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * screenHeight
                        val maxX = extraWidth / 2
                        offset = Offset(
                            (offset.x + scale * pan.x).coerceIn(-maxX, maxX),
                            (offset.y + scale * pan.y).coerceIn(-extraHeight, extraHeight)
                        )
                    }
                }
        ) {
            AsyncImage(
                model = images[it],
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y,
                    )
            )
        }
        IconButton(
            onClick = onDismiss,
            modifier = modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(top = 16.dp, start = 16.dp)
                .background(color = Color.Black.copy(alpha = 0.4f))
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun HandleOnBackPressed(
    enabled: Boolean = true,
    delayTime: Long = 0L,
    onBackAction: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var isEnabled by remember(enabled) { mutableStateOf(enabled) }

    LaunchedEffect(isEnabled) {
        if (isEnabled) {
            delay(delayTime)
            isEnabled = false
        }
    }
    BackHandler(enabled = enabled.not()) {
        if (isEnabled) {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            scope.launch {
                awaitFrame()
                onBackPressedDispatcher?.onBackPressed()
            }
        } else {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            onBackAction()
            isEnabled = true
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductScreenPreview() {
    ProductDetailScreen()
}

