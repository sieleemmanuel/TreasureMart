package com.sielehub.treasuremart.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OnBoardingViewModel(
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    val onBoardingDone = dataStoreManager.onBoardingDone.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        initialValue = runBlocking { dataStoreManager.onBoardingDone.first() }
    )

    fun setOnBoardingDone(onBoardingDone: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setOnBoardingDone(onBoardingDone)
        }
    }

}