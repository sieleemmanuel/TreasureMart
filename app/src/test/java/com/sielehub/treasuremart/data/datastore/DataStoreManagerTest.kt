package com.sielehub.treasuremart.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val TEST_DATASTORE_NAME = "settings_test.preferences_pb"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DataStoreManagerTest {
    private lateinit var context: Context
    private lateinit var dataStoreManager: DataStoreManager

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val tesDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(tesDispatcher + Job())


    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile(TEST_DATASTORE_NAME) }
        )

    @Before
    fun setUp() {
        context = mockk()
        dataStoreManager = DataStoreManager(testDataStore)
    }

    @Test
    fun `when setOnBoardingDone is called, onBoardingDone should be updated`() =
        testScope.runTest {
            val initialValue = dataStoreManager.onBoardingDone.first()
            assert(!initialValue)
            val onBoardingDone = true
            dataStoreManager.setOnBoardingDone(onBoardingDone)
            assert(dataStoreManager.onBoardingDone.first() == onBoardingDone)
        }
}