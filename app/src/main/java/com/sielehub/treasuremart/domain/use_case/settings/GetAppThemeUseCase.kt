package com.sielehub.treasuremart.domain.use_case.settings

import com.sielehub.treasuremart.data.datastore.DataStoreManager

class GetAppThemeUseCase(private val dataStoreManager: DataStoreManager) {
    operator fun invoke() = dataStoreManager.themeMode
}