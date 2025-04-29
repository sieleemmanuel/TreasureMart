package com.sielehub.treasuremart.presentation.ui.settings

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sielehub.treasuremart.core.Constants
import com.sielehub.treasuremart.presentation.common.TopBar
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    settingsViewModel: SettingsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val currentTheme by settingsViewModel.currentAppTheme.collectAsStateWithLifecycle()
    var showThemeModeDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        TopBar(
            navigationIcon = {
                FilledIconButton(
                    onClick = onNavigateBack,
                    shape = MaterialTheme.shapes.extraSmall,
                    colors = IconButtonDefaults.iconButtonColors(),
                    modifier = modifier
                        .height(48.dp)
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.weight(.8f)
                )
            }
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(top = 12.dp),
        ) {
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            showThemeModeDialog = true
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = modifier
                            .padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = "Theme",
                            modifier = modifier
                                .padding()
                        )
                        Text(
                            text = currentTheme,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            ),
                            modifier = modifier
                        )

                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = modifier
                            .padding(end = 16.dp)
                            .size(20.dp)
                    )
                }
                HorizontalDivider()
            }
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            Toast.makeText(context, "Show about the app", Toast.LENGTH_SHORT).show()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "About",
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 18.dp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = modifier
                            .padding(end = 16.dp)
                            .size(20.dp)
                    )
                }
                HorizontalDivider()
            }
        }
    }
    if (showThemeModeDialog) {
        ThemeModeDialog(
            currentTheme = currentTheme,
            onThemeSelected = {
                settingsViewModel.setAppTheme(it)
                settingsViewModel.triggerAppRestart(currentTheme != it)
            },
            onDismissRequest = {
                showThemeModeDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeModeDialog(
    modifier: Modifier = Modifier,
    currentTheme: String = "Light mode",
    onDismissRequest: () -> Unit = {},
    onThemeSelected: (String) -> Unit = {}
) {
    val themeOptions = listOf(
        Constants.AppThemes.LIGHT,
        Constants.AppThemes.DARK,
        Constants.AppThemes.SYSTEM,
    )
    val context = LocalContext.current
    var selectedTheme by remember(currentTheme) {
        mutableStateOf(currentTheme)
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = modifier.padding(PaddingValues(16.dp))) {
            Text(
                text = "App theme",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(16.dp)
            )
            themeOptions.forEach {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = it == selectedTheme,
                            onClick = {
                                selectedTheme = it
                                onThemeSelected(it)
                                Toast.makeText(context, selectedTheme, Toast.LENGTH_SHORT).show()
                                onDismissRequest()
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = modifier.padding(16.dp)
                    )
                    Spacer(modifier = modifier.weight(1f))
                    RadioButton(
                        selected = it == selectedTheme,
                        onClick = {
                            selectedTheme = it
                            onThemeSelected(it)
                            Toast.makeText(context, selectedTheme, Toast.LENGTH_SHORT).show()
                            onDismissRequest()
                        })
                }
            }
        }
    }
}