package com.sielehub.treasuremart.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.domain.use_case.settings.GetAppThemeUseCase
import com.sielehub.treasuremart.domain.use_case.settings.SetAppThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsViewModel(
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
) : ViewModel() {
    val currentAppTheme = getAppThemeUseCase().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = runBlocking {
            getAppThemeUseCase().first()
        }
    )

    private val _restartTriggered = Channel<Boolean>()
    val restartTriggered: Flow<Boolean> = _restartTriggered.receiveAsFlow()

    fun setAppTheme(selectedMode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setAppThemeUseCase(selectedMode)
        }
    }

    fun triggerAppRestart(restart: Boolean) {
        viewModelScope.launch {
            _restartTriggered.send(restart)
        }
    }
}