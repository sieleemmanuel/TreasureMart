package com.sielehub.treasuremart.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    paddingValues: () -> PaddingValues,
    onFinish: () -> Unit = {}
) {
    val viewModel: OnBoardingViewModel = koinViewModel()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues())
    ) {
        val (skip, pager, indicators, button) = createRefs()

        val pages = remember {
            listOf(
                OnboardingPage(
                    image = R.drawable.img_purchase_online,
                    title = "Choose your product",
                    description = "Find your favorite products that you want to buy easily"
                ),
                OnboardingPage(
                    image = R.drawable.img_track_order,
                    title = "Add to Cart",
                    description = "Start building your cart now! Add the items you love and get ready to complete your order. It's easy to get started."
                ),
                OnboardingPage(
                    image = R.drawable.img_get_your_order,
                    title = "Easy & Fast Delivery",
                    description = "Get your items delivered quickly and effortlessly! We offer easy and fast delivery, right to your doorstep"
                ),
            )
        }
        val pagerState = rememberPagerState(
            pageCount = { pages.count() }
        )
        val scope = rememberCoroutineScope()
        Text(text = "Skip",
            modifier = modifier
                .clickable {
                    scope.launch {
                        if (pagerState.currentPage != pages.count() - 1)
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        else {
                            onFinish()
                            viewModel.setOnBoardingDone(true)
                        }
                    }
                }
                .zIndex(10f)
                .constrainAs(skip) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )

        HorizontalPager(
            modifier = modifier
                .fillMaxSize()
                .constrainAs(pager) {
                    top.linkTo(parent.top)
                    bottom.linkTo(indicators.top)
                    height = Dimension.fillToConstraints
                },
            state = pagerState
        ) { page ->
            Page(onboardingPage = { pages[page] })
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(indicators) {
                    bottom.linkTo(button.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) {
                val color = if (it == pagerState.currentPage) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                val width =
                    if (it == pagerState.currentPage) 10.dp else 4.dp
                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(width, 4.dp)
                )
            }
        }

        Button(
            onClick = {
                scope.launch {
                    if (pagerState.currentPage != pages.count() - 1)
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    else {
                        onFinish()
                        viewModel.setOnBoardingDone(true)
                    }
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                }
        ) {
            val buttonText =
                if (pagerState.currentPage == pages.count() - 1) "Get Started" else "Next"
            Text(text = buttonText)
        }
    }
}

@Composable
fun Page(
    modifier: Modifier = Modifier,
    onboardingPage: () -> OnboardingPage = { OnboardingPage() },
) {
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
    ) {
        val (image, title, description) = createRefs()
        Image(
            painter = painterResource(id = onboardingPage().image),
            contentDescription = "",
            modifier = modifier
                .size(240.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(title.top)
                }
        )
        Text(
            text = onboardingPage().title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(description.top, margin = 16.dp)
                }
        )
        Text(
            text = onboardingPage().description,
            textAlign = TextAlign.Center,
            modifier = modifier
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
        )
    }
}

data class OnboardingPage(
    val image: Int = R.drawable.img_purchase_online,
    val title: String = "Purchase online?",
    val description: String = "OnBoarding page description goes here"
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TreasureMartTheme {
        OnBoardingScreen(paddingValues = { PaddingValues() })
    }
}