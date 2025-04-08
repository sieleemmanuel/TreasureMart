package com.sielehub.treasuremart.presentation.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.sielehub.treasuremart.data.datastore.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class OnBoardingViewModelTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: OnBoardingViewModel
    private val testScope = TestScope(testDispatcher + Job())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("settings_test.preferences_pb") }
        )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataStoreManager = DataStoreManager(testDataStore)
        viewModel = OnBoardingViewModel(dataStoreManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set and get onBoardingDone value`() =
        testScope.runTest {
            val initialValue = viewModel.onBoardingDone.value
            assert(!initialValue)
            viewModel.setOnBoardingDone(true)
            assert(viewModel.onBoardingDone.value)
        }
}