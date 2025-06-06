package com.sielehub.treasuremart.presentation.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.ui.theme.TreasureMartTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onBoardingViewModel: OnBoardingViewModel = koinViewModel(),
    paddingValues: () -> PaddingValues,
    onFinish: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues())
    ) {
        val (skip, pager, indicators, button, textHaveAccount) = createRefs()

        val pages = remember {
            listOf(
                OnboardingPage(
                    image = R.drawable.img_purchase_online,
                    title = context.getString(R.string.onboarding_one_title),
                    description = context.getString(R.string.onboarding_one_desc)
                ),
                OnboardingPage(
                    image = R.drawable.img_track_order,
                    title = context.getString(R.string.onboarding_two_title),
                    description = context.getString(R.string.onboarding_two_desc)
                ),
                OnboardingPage(
                    image = R.drawable.img_get_your_order,
                    title = context.getString(R.string.onboarding_three_title),
                    description = context.getString(R.string.onboarding_three_desc)
                ),
            )
        }
        val pagerState = rememberPagerState(
            pageCount = { pages.count() }
        )
        val scope = rememberCoroutineScope()
        if (pagerState.currentPage != pages.count() - 1)
            TextButton(
                onClick = {
                    scope.launch {
                        // if (pagerState.currentPage != pages.count() - 1)
                        pagerState.animateScrollToPage(pages.count() - 1)
                        /*else {
                            onFinish()
                            viewModel.setOnBoardingDone(true)
                        }*/
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = Color.Transparent
                ),
                modifier = modifier
                    .zIndex(1f)
                    .constrainAs(skip) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

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
                    bottom.linkTo(button.top, margin = 24.dp)
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
                        onFinish(false)
                        onBoardingViewModel.setOnBoardingDone(true)
                    }
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp)
                .constrainAs(button) {
                    bottom.linkTo(
                        if (pagerState.currentPage == pages.count() - 1)
                            textHaveAccount.top else parent.bottom,
                        if (pagerState.currentPage == pages.count() - 1) 16.dp else 36.dp
                    )
                }
        ) {
            val buttonText = if (pagerState.currentPage == pages.count() - 1)
                stringResource(R.string.get_started) else stringResource(R.string.next)
            Text(text = buttonText, style = MaterialTheme.typography.titleMedium)
        }
        Text(
            modifier = modifier
                .clickable {
                    onFinish(true)
                    onBoardingViewModel.setOnBoardingDone(true)
                }
                .constrainAs(textHaveAccount) {
                    bottom.linkTo(parent.bottom, 36.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    visibility = if (pagerState.currentPage == pages.count() - 1)
                        Visibility.Visible else Visibility.Gone
                },
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.login)
            /*buildAnnotatedString {
                append(stringResource(R.string.already_have_an_account))
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = stringResource(R.string.login),
                        styles = TextLinkStyles(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            ),
                        ),
                        linkInteractionListener = {
                            onFinish(true)
                            onBoardingViewModel.setOnBoardingDone(true)
                        }
                    )
                ) {
                    append(stringResource(R.string.login))
                }
            }*/
        )
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
            contentDescription = null,
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
    val title: String = "",
    val description: String = ""
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TreasureMartTheme {
        OnBoardingScreen(paddingValues = { PaddingValues() })
    }
}