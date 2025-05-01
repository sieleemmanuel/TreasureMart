package com.sielehub.treasuremart.presentation.ui.product.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.R
import com.sielehub.treasuremart.presentation.common.TopBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    searchViewModel: SearchViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var searchQuery by rememberSaveable { mutableStateOf(searchViewModel.currentQuery) }
    val recentQueries by searchViewModel.searchHistory.collectAsStateWithLifecycle()
    val searchSuggestions by searchViewModel.searchSuggestions
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(searchQuery) {
        searchViewModel.getSearchSuggestions(searchQuery)
        searchViewModel.currentQuery = searchQuery
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TopBar(
            navigationIcon = {
                FilledIconButton(
                    onClick = { onNavigateBack() },
                    modifier = modifier.size(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            title = {
                BasicTextField(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxWidth(1f)
                        .height(48.dp)
                        .focusRequester(focusRequester),
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            performSearch(
                                searchViewModel = searchViewModel,
                                searchQuery = searchQuery,
                                onSearch = onSearch
                            )
                        }
                    ),
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    decorationBox = {
                        OutlinedTextFieldDefaults.DecorationBox(
                            value = searchQuery,
                            innerTextField = it,
                            enabled = false,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            contentPadding = OutlinedTextFieldDefaults.contentPadding(
                                top = 0.dp,
                                bottom = 0.dp
                            ),
                            placeholder = {
                                Text(text = stringResource(R.string.search))
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        modifier = modifier
                                            .clickable {
                                                searchQuery = ""
                                            }
                                    )
                                }
                            },
                            container = {
                                Container(
                                    shape = RoundedCornerShape(24.dp),
                                    interactionSource = interactionSource,
                                    enabled = true,
                                    isError = false,
                                    colors = OutlinedTextFieldDefaults.colors().copy(
                                        focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                        /*focusedPlaceholderColor = Color.Transparent,
                                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.7f
                                        )*/
                                    ),
                                )
                            }
                        )
                    }
                )
            },
            actions = {
                FilledIconButton(
                    onClick = {
                        performSearch(searchViewModel, searchQuery, onSearch)
                    },
                    modifier = modifier.size(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = IconButtonDefaults.iconButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            containerColor = Color.Transparent,
        )
        /*Spacer(modifier = modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onNavigateBack() },
                modifier = modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            BasicTextField(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(1f)
                    .height(48.dp),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        performSearch(
                            searchViewModel = searchViewModel,
                            searchQuery = searchQuery,
                            onSearch = onSearch
                        )
                    }
                ),
                singleLine = true,
                textStyle = TextStyle.Default.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                decorationBox = {
                    OutlinedTextFieldDefaults.DecorationBox(
                        value = searchQuery,
                        innerTextField = it,
                        enabled = false,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = OutlinedTextFieldDefaults.contentPadding(
                            top = 0.dp,
                            bottom = 0.dp
                        ),
                        placeholder = {
                            Text(text = stringResource(R.string.search))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    modifier = modifier
                                        .clickable {
                                            searchQuery = ""
                                        }
                                )
                            }
                        },
                        container = {
                            Container(
                                shape = RoundedCornerShape(24.dp),
                                interactionSource = interactionSource,
                                enabled = true,
                                isError = false,
                                colors = OutlinedTextFieldDefaults.colors().copy(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                    *//*focusedPlaceholderColor = Color.Transparent,
                                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(
                                        alpha = 0.7f
                                    )*//*
                                ),
                            )
                        }
                    )
                }
            )
            IconButton(
                onClick = {
                    performSearch(searchViewModel, searchQuery, onSearch)
                },
                modifier = modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }*/
        Spacer(modifier = modifier.height(8.dp))
        if (searchQuery.isEmpty() && recentQueries.isNotEmpty()) {
            SearchHistory(
                recentQueries = recentQueries,
                onItemClick = {
                    onSearch(it)
                },
                onClearHistory = {
                    searchViewModel.clearSearchHistory()
                }
            )
        }
        if (searchQuery.isNotEmpty()) {
            SearchSuggestionSpec(
                suggestions = searchSuggestions,
                onSuggestionClick = {
                    searchQuery = it
                    performSearch(searchViewModel, searchQuery, onSearch)
                }
            )
        }
    }
}

private fun performSearch(
    searchViewModel: SearchViewModel,
    searchQuery: String,
    onSearch: (String) -> Unit
) {
    if (searchQuery.isNotEmpty()) {
        searchViewModel.addSearchToHistory(searchQuery)
        onSearch(searchQuery)
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SearchSuggestionSpec(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    onSuggestionClick: (String) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(items = suggestions) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onSuggestionClick(it)
                    }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = modifier
                        .padding(4.dp)

                )
                Text(text = it)
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )

        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchHistory(
    modifier: Modifier = Modifier,
    recentQueries: List<String> = emptyList(),
    onItemClick: (String) -> Unit = {},
    onClearHistory: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Search history",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            IconButton(
                onClick = onClearHistory,
                modifier = modifier.alpha(.5f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.DeleteOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(modifier = modifier.height(4.dp))
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            recentQueries.forEach {
                Box(
                    modifier = modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .selectable(
                            selected = true,
                            onClick = {
                                onItemClick(it)
                            }
                        )
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Light
                        ),
                        modifier = modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    SearchScreen(paddingValues = PaddingValues(), modifier = modifier)
}