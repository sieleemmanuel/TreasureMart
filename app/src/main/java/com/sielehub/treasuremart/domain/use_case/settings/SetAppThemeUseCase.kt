package com.sielehub.treasuremart.domain.use_case.settings

import com.sielehub.treasuremart.data.datastore.DataStoreManager

class SetAppThemeUseCase(private val dataStoreManager: DataStoreManager) {
    suspend operator fun invoke(selectedMode: String) = dataStoreManager.setAppTheme(selectedMode)
}